package LP2.Trabalho_final.controller;

import LP2.Trabalho_final.TrabalhoFinalApplication;
import LP2.Trabalho_final.model.Cep;
import LP2.Trabalho_final.model.Cidade;
import LP2.Trabalho_final.model.Uf;
import LP2.Trabalho_final.repository.CidadeRepository;
import LP2.Trabalho_final.service.CidadeService;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest(classes = TrabalhoFinalApplication.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class CidadeControllerTeste {
    private static final ObjectMapper MAPPER = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CidadeService cidadeService;
    @MockBean
    private CidadeRepository cidadeRepository;


    public Cidade buildCidade(String nome, Uf uf) {
        return new Cidade(nome, uf);
    }

    public Uf buildUf(String sigla, String nome) {
        return new Uf(sigla, nome);
    }

    @Test
    void testaAdicao() throws Exception {
        Cidade cidade = buildCidade("Porto Alegre", buildUf("RS", "Rio Grande do Sul"));

        doAnswer(invocation -> invocation.getArgument(0)).when(cidadeService).salvar(any(Cidade.class));

        MvcResult mvcResult = mockMvc.perform(post("/cidades")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(cidade)))
                .andExpect(status().isCreated())
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        System.out.println("Conteúdo da resposta: " + content);

        Cidade retorno = parseResponse(mvcResult, Cidade.class);

        verify(cidadeService, times(1)).salvar(any(Cidade.class));

        assertThat("Não retornou o Cidade correto", retorno.getNome(), equalTo(cidade.getNome()));

    }
    @Test
    void testaLista() throws Exception {
        List<Cidade> resultados = new ArrayList<>();
        Uf uf = new Uf("RS", "Rio Grande do Sul");
        Cidade cidade = new Cidade("Porto Alegre", uf);
        resultados.add(cidade);
        doAnswer(invocation -> {
            return resultados;
        }).when(cidadeService).buscaTodos();

        MvcResult mvcResult =
                mockMvc.perform(get("/cidades")).andExpect(status().is(HttpStatus.OK.value())).andReturn();

        List<Cidade> cidades = parseResponseList(mvcResult, Cidade.class);
        assertThat("Não retornou quantidade correta", cidades.size(), is(1));

        Cidade validar = new Cidade("Porto Alegre", uf);


        verify(cidadeService, times(1)).buscaTodos();
        assertThat("Não retornou Cidade correto", cidades.get(0).getNome(), equalTo(validar.getNome()));
    }
    @Test
    void testaBuscaPorUF() throws Exception {
        List<Cidade> resultados = new ArrayList<>();
        Uf uf = new Uf("RS", "Rio Grande do Sul");
        Cidade cidade = new Cidade("Porto Alegre", uf);
        resultados.add(cidade);
        doAnswer(invocation -> {
            return resultados;
        }).when(cidadeService).buscaPorUf(uf);

        MvcResult mvcResult =
                mockMvc.perform(get("/ceps/por-uf?uf=1")).andExpect(status().is(HttpStatus.OK.value())).andReturn();

        List<Cidade>cidades  = parseResponseList(mvcResult, Cidade.class);
        assertThat("Não retornou quantidade correta", cidades.size(), is(1));

        Cidade validar = new Cidade("Porto Alegre", uf);


        verify(cidadeService, times(1)).buscaPorUf(uf);
        assertThat("Não retornou Cidade correto", cidades.get(0).getNome(), equalTo(validar.getNome()));
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
