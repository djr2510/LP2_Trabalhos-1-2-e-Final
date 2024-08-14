package com.LP2.Trabalho;

import com.LP2.Trabalho.controller.ContatoController;
import com.LP2.Trabalho.exception.ParametroInvalidoException;
import com.LP2.Trabalho.exception.handler.ErroResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.LP2.Trabalho.model.ContatoModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = Application.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class ApplicationTests {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    private static final String NOME_CONTATO_INSERIR = "Roberto";
    private static final Integer TELEFONE_CONTATO_INSERIR = 97312274;
    private static final String EMAIL_CONTATO_INSERIR = "Roberto@gmail.com";
    private static final Calendar ANIVERSARIO_CONTATO_INSERIR = new GregorianCalendar(2006,Calendar.NOVEMBER,25);
    private ContatoModel build(String nome, Integer telefone, String email, Calendar nascimento) {
        return new ContatoModel(nome, telefone, email, nascimento);
    }
    private ContatoModel build(Integer id,String nome, Integer telefone, String email, Calendar nascimento) {
        return new ContatoModel(id,nome, telefone, email, nascimento);
    }

    @Test
    void testaAdicaoContato()  throws Exception{
        MvcResult mvcResult =
                mockMvc.perform(post("/contatos").contentType("application/json")
                                .content(MAPPER.writeValueAsString(build(NOME_CONTATO_INSERIR,TELEFONE_CONTATO_INSERIR,EMAIL_CONTATO_INSERIR,ANIVERSARIO_CONTATO_INSERIR))))
                        .andExpect(status().is(HttpStatus.CREATED.value())).andReturn();

        ContatoModel retorno = parseResponse(mvcResult, ContatoModel.class);
        assertThat("Não retornou o Contato Correto", retorno.getNome(), equalTo(NOME_CONTATO_INSERIR));
        System.out.println("O id Criado Foi "+retorno.getId());
    }
    @Test
    void testaAdicaoNomeInvalido() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/contatos")
                        .contentType("application/json")
                        .content(MAPPER.writeValueAsString(build("",TELEFONE_CONTATO_INSERIR,EMAIL_CONTATO_INSERIR,ANIVERSARIO_CONTATO_INSERIR))))
                        .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andReturn();
    }

    @Test
    void testaLista() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/contatos"))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andReturn();

        List<ContatoModel> contatos = parseResponseList(mvcResult, ContatoModel.class);
        for (ContatoModel contato : contatos) {
            System.out.println("ID: " + contato.getId() + ", Nome: " + contato.getNome() + ", Telefone: " + contato.getTelefone() + ", Email: " + contato.getEmail());
        }
        assertThat("Não retornou quantidade correta", contatos.size(), anyOf(is(3), is(4)));
    }

    @Test
    void testaDeleteContato() throws Exception {
        ContatoModel contato = build(NOME_CONTATO_INSERIR, TELEFONE_CONTATO_INSERIR, EMAIL_CONTATO_INSERIR, ANIVERSARIO_CONTATO_INSERIR);


        mockMvc.perform(post("/contatos")
                        .contentType("application/json")
                        .content(MAPPER.writeValueAsString(contato)))
                .andExpect(status().is(HttpStatus.CREATED.value()))
                .andReturn();

        Integer idContatoCriado = contato.getId();

        mockMvc.perform(delete("/contatos/{id}", idContatoCriado))
                .andExpect(status().isOk());
        System.out.println("Contato "+contato.getId()+" Deletado");
    }
    @Test
    void testaEditarContato() throws Exception {
        Integer idContatoEditar = 2;
        ContatoModel contatoEditado = build(idContatoEditar, "Robson", TELEFONE_CONTATO_INSERIR, "robson@gmail.com", ANIVERSARIO_CONTATO_INSERIR);
        mockMvc.perform(put("/contatos/{id}", idContatoEditar)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(contatoEditado)))
                .andExpect(status().isOk());
        System.out.println("Contato "+idContatoEditar+" Editado");
        System.out.println("ID: " + contatoEditado.getId() + ", Nome: " + contatoEditado.getNome() + ", Telefone: " + contatoEditado.getTelefone() + ", Email: " + contatoEditado.getEmail());
    }


    @Test
    void testaBuscaPorLetra() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/contatos/consulta").param("Nome", "Da"))
                .andExpect(status().isOk())
                .andReturn();
        List<ContatoModel> contatos = parseResponseList(mvcResult, ContatoModel.class);
        System.out.println("Contatos que contem (Da)");
        System.out.println("Contatos encontrados:");
        for (ContatoModel contato : contatos) {
            System.out.println("ID: " + contato.getId() + ", Nome: " + contato.getNome() + ", Telefone: " + contato.getTelefone() + ", Email: " + contato.getEmail());
        }
        assertThat("Não encontrou contatos iniciando com 'Da'", contatos.size(), greaterThan(0));
    }

    @Test
    void testaAniversarioMes() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/contatos/consulta").param("Mes", "10"))
                .andExpect(status().isOk())
                .andReturn();
        List<ContatoModel> contatos = parseResponseList(mvcResult, ContatoModel.class);
        System.out.println("Contatos com o mes 10");
        System.out.println("Contatos encontrados:");
        for (ContatoModel contato : contatos) {
            System.out.println("ID: " + contato.getId() + ", Nome: " + contato.getNome() + ", Telefone: " + contato.getTelefone() + ", Email: " + contato.getEmail());
        }
        assertThat("Não encontrou contatos com aniversário em Outubro", contatos.size(), greaterThan(0));
    }



    public void tearDown() throws Exception {
        mockMvc.perform(get("/contatos/" + NOME_CONTATO_INSERIR))
                .andExpect(status().isOk())
                .andReturn();

        mockMvc.perform(delete("/contatos/" + NOME_CONTATO_INSERIR))
                .andExpect(status().isNoContent())
                .andReturn();
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
