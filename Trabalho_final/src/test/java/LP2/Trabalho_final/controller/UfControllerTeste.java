package LP2.Trabalho_final.controller;

import LP2.Trabalho_final.TrabalhoFinalApplication;
import LP2.Trabalho_final.model.Cidade;
import LP2.Trabalho_final.model.Uf;
import LP2.Trabalho_final.repository.UfRepository;
import LP2.Trabalho_final.service.UfService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = TrabalhoFinalApplication.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class UfControllerTeste {
    private static final ObjectMapper MAPPER = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    public Uf buildUf(String sigla, String nome) {
        return new Uf(sigla, nome);
    }

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UfService ufService;
    @MockBean
    private UfRepository ufRepository;

    @Test
    void testaAdicao() throws Exception {
        Uf uf =buildUf("RS", "Rio Grande do Sul");

        doAnswer(invocation -> invocation.getArgument(0)).when(ufService).salvar(any(Uf.class));

        MvcResult mvcResult = mockMvc.perform(post("/ufs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(uf)))
                .andExpect(status().isCreated())
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        System.out.println("Conteúdo da resposta: " + content);

        Cidade retorno = parseResponse(mvcResult, Cidade.class);

        verify(ufService, times(1)).salvar(any(Uf.class));

        assertThat("Não retornou o Uf correto", retorno.getNome(), equalTo(uf.getNome()));

    }
    @Test
    void testaLista() throws Exception {
        List<Uf> resultados = new ArrayList<>();
        Uf uf = new Uf("RS", "Rio Grande do Sul");
        resultados.add(uf);
        doAnswer(invocation -> {
            return resultados;
        }).when(ufService).buscaTodos();

        MvcResult mvcResult =
                mockMvc.perform(get("/ufs")).andExpect(status().is(HttpStatus.OK.value())).andReturn();

        List<Uf> ufs = parseResponseList(mvcResult, Uf.class);
        assertThat("Não retornou quantidade correta", ufs.size(), is(1));

        Uf validar = new Uf("RS", "Rio Grande do Sul");


        verify(ufService, times(1)).buscaTodos();
        assertThat("Não retornou Cidade correto", ufs.get(0).getNome(), equalTo(validar.getNome()));
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
