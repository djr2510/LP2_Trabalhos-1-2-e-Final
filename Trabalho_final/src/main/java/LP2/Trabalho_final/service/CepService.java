package LP2.Trabalho_final.service;

import LP2.Trabalho_final.exception.handler.RegistroNaoEcontradoException;
import LP2.Trabalho_final.model.Cep;
import LP2.Trabalho_final.repository.CepRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CepService {

    @Autowired
    private CepRepository cepRepository;

    public Cep salvar(Cep cep) {
        return cepRepository.save(cep);
    }

    public List<Cep> buscaTodos() {
        return cepRepository.findAll();
    }

    public List<Cep> buscaPorNomeCidade(String nomeCidade) {
        return cepRepository.findByCidadeNome(nomeCidade);
    }

    public List<Cep> buscaPorUf(String siglaUf) {
        return cepRepository.findByCidadeUfSigla(siglaUf);
    }

    public Cep buscaPorId(Integer numCep) {
        Optional<Cep> cep = cepRepository.findById(numCep);
        return cep.orElseThrow(() -> new RegistroNaoEcontradoException("Cep não encontrado"));
    }

    public void remover(Integer numCep) {
        Cep cep = buscaPorId(numCep);
        cepRepository.delete(cep);
    }
}
