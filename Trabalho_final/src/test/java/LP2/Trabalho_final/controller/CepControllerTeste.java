package LP2.Trabalho_final.controller;

import LP2.Trabalho_final.TrabalhoFinalApplication;
import LP2.Trabalho_final.model.Cep;
import LP2.Trabalho_final.model.Cidade;
import LP2.Trabalho_final.model.Uf;
import LP2.Trabalho_final.repository.CepRepository;
import LP2.Trabalho_final.service.CepService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.event.annotation.AfterTestMethod;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.mockito.ArgumentMatchers.eq;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;

@SpringBootTest(classes = TrabalhoFinalApplication.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class CepControllerTeste {
    private static final ObjectMapper MAPPER = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CepService cepService;
    @MockBean
    private CepRepository cepRepository;

    private static final Integer CEP_INSERIR = 95680807;

    public Cep build(Integer Cep, Cidade cidade, String logradouro, Integer num_inicio, Integer num_fim){
        return new Cep(Cep,cidade, logradouro, num_inicio, num_fim);
    }
    public Cidade build (String nome, Uf uf){
        return new Cidade(nome, uf);
    }
    public Uf build(String sigla, String nome){
        return new Uf(sigla, nome);
    }

    @Test
    void testaAdicao() throws Exception {
        Cep cep = new Cep();
        cep.setNum_cep(CEP_INSERIR);
        cep.setCidade(new Cidade("Porto Alegre", new Uf("RS", "Rio Grande do Sul")));
        cep.setLogradouro("Rua");
        cep.setNum_inicio(1000);
        cep.setNum_fim(1100);

        doAnswer(invocation -> invocation.getArgument(0)).when(cepService).salvar(any(Cep.class));

        MvcResult mvcResult = mockMvc.perform(post("/ceps")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(cep)))
                .andExpect(status().isCreated())
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        System.out.println("Conteúdo da resposta: " + content);

        Cep retorno = parseResponse(mvcResult, Cep.class);

        verify(cepService, times(1)).salvar(any(Cep.class));

        assertThat("Não retornou o CEP correto", retorno.getNum_cep(), equalTo(cep.getNum_cep()));


    }
    @Test
    public void testaRemover() throws Exception {
        Uf uf = new Uf("RS", "Rio Grande do Sul");
        Cidade cidade = new Cidade("Porto Alegre", uf);

        doNothing().when(cepService).remover(eq(CEP_INSERIR));

        mockMvc.perform(post("/ceps")
                        .contentType("application/json")
                        .content(MAPPER.writeValueAsString(build(CEP_INSERIR, cidade, "logradouro", 1234, 5678))))
                .andExpect(status().is(HttpStatus.CREATED.value()))
                .andReturn();

        MvcResult mvcResult = mockMvc.perform(delete("/ceps/" + CEP_INSERIR)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn();

        verify(cepService, times(1)).remover(eq(CEP_INSERIR));
    }
    @Test
    void testaLista() throws Exception {
        List<Cep> resultados = new ArrayList<>();
        Uf uf = new Uf("RS", "Rio Grande do Sul");
        Cidade cidade = new Cidade("Porto Alegre", uf);
        Cep cep = build(CEP_INSERIR, cidade, "RS",  1234, 5678);
        resultados.add(cep);
        doAnswer(invocation -> {
            return resultados;
        }).when(cepService).buscaTodos();

        MvcResult mvcResult =
                mockMvc.perform(get("/ceps")).andExpect(status().is(HttpStatus.OK.value())).andReturn();

        List<Cep> ceps = parseResponseList(mvcResult, Cep.class);
        assertThat("Não retornou quantidade correta", ceps.size(), is(1));

        Cep validar = build(CEP_INSERIR, cidade, "RS",  1234, 5678);


        verify(cepService, times(1)).buscaTodos();
        assertThat("Não retornou cep correto", ceps.get(0).getNum_cep(), equalTo(validar.getNum_cep()));
    }
    @Test
    void testaBuscaPorCidade() throws Exception {
        String nomeCidade="Porto Alegre";
        List<Cep> resultados = new ArrayList<>();
        Uf uf = new Uf("RS", "Rio Grande do Sul");
        Cidade cidade = new Cidade(nomeCidade, uf);
        Cep cep = build(CEP_INSERIR, cidade, "RS",  1234, 5678);
        resultados.add(cep);
        doAnswer(invocation -> {
            return resultados;
        }).when(cepService).buscaPorNomeCidade(nomeCidade);

        MvcResult mvcResult =
                mockMvc.perform(get("/ceps/por-cidade?cidade=Porto Alegre")).andExpect(status().is(HttpStatus.OK.value())).andReturn();

        List<Cep> ceps = parseResponseList(mvcResult, Cep.class);
        assertThat("Não retornou quantidade correta", ceps.size(), is(1));

        Cep validar = build(CEP_INSERIR, cidade, "RS",  1234, 5678);


        verify(cepService, times(1)).buscaPorNomeCidade(nomeCidade);
        assertThat("Não retornou cep correto", ceps.get(0).getNum_cep(), equalTo(validar.getNum_cep()));
    }
    @Test
    void testaBuscaPorUF() throws Exception {
        List<Cep> resultados = new ArrayList<>();
        Uf uf = new Uf("RS", "Rio Grande do Sul");
        Cidade cidade = new Cidade("Porto Alegre", uf);
        Cep cep = build(CEP_INSERIR, cidade, "RS",  1234, 5678);
        resultados.add(cep);
        doAnswer(invocation -> {
            return resultados;
        }).when(cepService).buscaPorUf("RS");

        MvcResult mvcResult =
                mockMvc.perform(get("/ceps/por-uf?uf=RS")).andExpect(status().is(HttpStatus.OK.value())).andReturn();

        List<Cep> ceps = parseResponseList(mvcResult, Cep.class);
        assertThat("Não retornou quantidade correta", ceps.size(), is(1));

        Cep validar = build(CEP_INSERIR, cidade, "RS",  1234, 5678);


        verify(cepService, times(1)).buscaPorUf("RS");
        assertThat("Não retornou cep correto", ceps.get(0).getNum_cep(), equalTo(validar.getNum_cep()));
    }




    @AfterEach
    public void tearDown()  throws Exception {
        MvcResult mvcResult =
                mockMvc.perform(get("/ceps/"+ CEP_INSERIR)).andReturn();

        if(mvcResult.getResponse().getStatus() != HttpStatus.NOT_FOUND.value()) {
            mockMvc.perform(delete("/ceps/"+CEP_INSERIR)).andReturn();
        }
    }


    private static <T> List<T> parseResponseList(MvcResult mockHttpServletResponse, Class<T> clazz) {
        try {
            String contentAsString = mockHttpServletResponse.getResponse().getContentAsString();
            return MAPPER.readValue(contentAsString, MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static <T> T parseResponse(MvcResult mockHttpServletResponse, Class<T> clazz) {
        try {
            String contentAsString = mockHttpServletResponse.getResponse().getContentAsString();
            return MAPPER.readValue(contentAsString, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
