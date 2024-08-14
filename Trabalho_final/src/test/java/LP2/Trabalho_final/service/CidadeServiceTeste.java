package LP2.Trabalho_final.service;

import LP2.Trabalho_final.model.Cep;
import LP2.Trabalho_final.model.Cidade;
import LP2.Trabalho_final.model.Uf;
import LP2.Trabalho_final.repository.CepRepository;
import LP2.Trabalho_final.repository.CidadeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class CidadeServiceTeste {
    @Mock
    private CidadeRepository cidadeRepository;

    @InjectMocks
    private CidadeService service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testaCadastrar() {
        Uf uf = build("Rs","Rio Grande do Sul");
        Cidade cidade = build("Porto Alegre", uf);

        doAnswer(invocation -> {
            return cidade;
        }).when(cidadeRepository).save(eq(cidade));

        Cidade retorno = service.salvar(cidade);

        verify(cidadeRepository, times(1)).save(eq(cidade));
        assertThat("Não retornou a Cidade Correta", retorno.getId(), equalTo(cidade.getId()));
    }
    @Test
    void testaListagem() {
        List<Cidade> listagem = new ArrayList<>();
        Uf uf = build("Rs","Rio Grande do Sul");
        Cidade cidade1 = build("Tramandai", uf);
        Cidade cidade2 = build("Porto Alegre", uf);

        listagem.add(cidade1);
        listagem.add(cidade2);
        doAnswer(invocation -> {
            return listagem;
        }).when(cidadeRepository).findAll();

        List<Cidade> retorno = service.buscaTodos();

        verify(cidadeRepository, times(1)).findAll();
        assertThat("Não retornou a Cidade Correta", retorno.get(0).getId(), equalTo(cidade1.getId()));
    }
    void testaListagemPorUf() {
        List<Cidade> listagem = new ArrayList<>();
        Uf uf1 = build("SP","São Paulo");
        Uf uf2 = build("Rs","Rio Grande do Sul");
        Cidade cidade1 = build("São Paulo", uf1);
        Cidade cidade2 = build("Porto Alegre", uf2);

        listagem.add(cidade1);
        listagem.add(cidade2);
        doAnswer(invocation -> {
            return listagem;
        }).when(cidadeRepository).findAll();

        List<Cidade> retorno = service.buscaTodos();

        verify(cidadeRepository, times(1)).findAll();
        assertThat("Não retornou a Cidade Correta", retorno.get(0).getId(), equalTo(cidade1.getId()));
    }
    public Cidade build(String nome, Uf uf){
        return new Cidade(nome, uf);
    }
    public Uf build(String sigla, String nome) {
        return new Uf(sigla, nome);
    }


}
