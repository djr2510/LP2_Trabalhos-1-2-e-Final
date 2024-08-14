package LP2.Trabalho_final.service;

import LP2.Trabalho_final.exception.handler.RegistroNaoEcontradoException;
import LP2.Trabalho_final.model.Cidade;
import LP2.Trabalho_final.model.Uf;
import LP2.Trabalho_final.repository.CidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CidadeService {

    @Autowired
    private CidadeRepository cidadeRepository;

    public Cidade salvar(Cidade cidade) {
        return cidadeRepository.save(cidade);
    }

    public List<Cidade> buscaTodos() {
        return cidadeRepository.findAll();
    }

    public List<Cidade> buscaPorUf(Uf uf) {
        return cidadeRepository.findByUf(uf);
    }

    public Cidade buscaPorId(Integer id) {
        Optional<Cidade> cidade = cidadeRepository.findById(id);
        return cidade.orElseThrow(() -> new RegistroNaoEcontradoException("Cidade não encontrada"));
    }
}
