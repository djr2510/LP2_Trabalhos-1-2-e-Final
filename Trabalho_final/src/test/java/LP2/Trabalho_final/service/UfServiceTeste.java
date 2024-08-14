package LP2.Trabalho_final.service;

import LP2.Trabalho_final.model.Cidade;
import LP2.Trabalho_final.model.Uf;
import LP2.Trabalho_final.repository.CidadeRepository;
import LP2.Trabalho_final.repository.UfRepository;
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

public class UfServiceTeste {
    @Mock
    private UfRepository ufRepository;

    @InjectMocks
    private UfService service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testaCadastrar() {
        Uf uf = build("Rs","Rio Grande do Sul");

        doAnswer(invocation -> {
            return uf;
        }).when(ufRepository).save(eq(uf));

        Uf retorno = service.salvar(uf);

        verify(ufRepository, times(1)).save(eq(uf));
        assertThat("Não retornou a Cidade Correta", retorno.getId(), equalTo(uf.getId()));
    }
    @Test
    void testaListagem() {
        List<Uf> listagem = new ArrayList<>();
        Uf uf1 = build("Rs","Rio Grande do Sul");
        Uf uf2 = build("SP","São Paulo");
        listagem.add(uf1);
        listagem.add(uf2);
        doAnswer(invocation -> {
            return listagem;
        }).when(ufRepository).findAll();

        List<Uf> retorno = service.buscaTodos();

        verify(ufRepository, times(1)).findAll();
        assertThat("Não retornou a Cidade Correta", retorno.get(0).getId(), equalTo(uf1.getId()));
    }
    public Uf build(String sigla, String nome) {
        return new Uf(sigla, nome);
    }
}
