package LP2.Trabalho_final.service;
import LP2.Trabalho_final.model.Cep;
import LP2.Trabalho_final.model.Cidade;
import LP2.Trabalho_final.model.Uf;
import LP2.Trabalho_final.repository.CepRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class CepServiceTeste {

    @Mock
    private CepRepository cepRepository;

    @InjectMocks
    private CepService service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    void testaCadastrar() {
        Uf uf = build("Rs","Rio Grande do Sul");
        Cidade cidade = build("Porto Alegre", uf);
        Cep inserir = build(12345678,cidade, "Algo", 1234, 5678);

        doAnswer(invocation -> {
            return inserir;
        }).when(cepRepository).save(eq(inserir));

        Cep retorno = service.salvar(inserir);

        verify(cepRepository, times(1)).save(eq(inserir));
        assertThat("Não retornou o CEP Correto", retorno.getNum_cep(), equalTo(inserir.getNum_cep()));
    }
    @Test
    void testaRemover() {
        Uf uf = build("Rs","Rio Grande do Sul");
        Cidade cidade = build("Porto Alegre", uf);
        Optional<Cep> cep = Optional.of(build(12345678,cidade, "Algo", 1234, 5678));

        doAnswer(invocation -> {
            return cep;
        }).when(cepRepository).findById(12345678);

        doNothing().when(cepRepository).delete(eq(cep.get()));

        service.remover(12345678);

        verify(cepRepository, times(1)).findById(12345678);
        verify(cepRepository, times(1)).delete(eq(cep.get()));
    }

    @Test
    void testaListagem() {
        List<Cep> listagem = new ArrayList<>();
        Uf uf = build("Rs","Rio Grande do Sul");
        Cidade cidade = build("Porto Alegre", uf);
        Cep cep2 = build(12345678,cidade, "Algo", 1234, 5678);
        Cep cep1 = build(87654321,cidade, "Outro", 8765, 4321);
        listagem.add(cep1);
        listagem.add(cep2);
        doAnswer(invocation -> {
            return listagem;
        }).when(cepRepository).findAll();

        List<Cep> retorno = service.buscaTodos();

        verify(cepRepository, times(1)).findAll();
        assertThat("Não retornou o CEP Correto", retorno.get(0).getNum_cep(), equalTo(cep1.getNum_cep()));
    }

    @Test
    void testaListagemPorCep() {
        Uf uf = build("Rs","Rio Grande do Sul");
        Cidade cidade = build("Porto Alegre", uf);
        Cep cep = build(12345678,cidade, "Algo", 1234, 5678);
        doReturn(Optional.of(cep)).when(cepRepository).findById(12345678);


        Cep retorno = service.buscaPorId(12345678);

        verify(cepRepository, times(1)).findById(12345678);
        assertThat("Não retornou o CEP Correto", retorno.getNum_cep(), equalTo(cep.getNum_cep()));
    }
    @Test
    void testaListagemPorCidade() {
        Uf uf = build("Rs", "Rio Grande do Sul");
        Cidade cidade = build("Porto Alegre", uf);
        Cep cep = build(12345678, cidade, "Algo", 1234, 5678);

        List<Cep> listaCeps = Arrays.asList(cep);

        doReturn(listaCeps).when(cepRepository).findByCidadeNome("Porto Alegre");

        List<Cep> retorno = service.buscaPorNomeCidade("Porto Alegre");

        verify(cepRepository, times(1)).findByCidadeNome("Porto Alegre");

        assertThat("Não retornou o CEP Correto", retorno.get(0).getNum_cep(), equalTo(cep.getNum_cep()));
    }
    @Test
    void testaListagemPorUf() {
        Uf uf = build("Rs", "Rio Grande do Sul");
        Cidade cidade = build("Porto Alegre", uf);
        Cep cep = build(12345678, cidade, "Algo", 1234, 5678);

        List<Cep> listaCeps = Arrays.asList(cep);

        doReturn(listaCeps).when(cepRepository).findByCidadeUfSigla("RS");

        List<Cep> retorno = service.buscaPorUf("RS");

        verify(cepRepository, times(1)).findByCidadeUfSigla("RS");

        assertThat("Não retornou o CEP Correto", retorno.get(0).getNum_cep(), equalTo(cep.getNum_cep()));
    }
        public Cep build(Integer Cep,Cidade cidade, String logradouro, Integer num_inicio, Integer num_fim){
            return new Cep(Cep,cidade, logradouro, num_inicio, num_fim);
        }
        public Cidade build (String nome, Uf uf){
            return new Cidade(nome, uf);
        }
        public Uf build(String sigla, String nome){
            return new Uf(sigla, nome);
        }
    }


