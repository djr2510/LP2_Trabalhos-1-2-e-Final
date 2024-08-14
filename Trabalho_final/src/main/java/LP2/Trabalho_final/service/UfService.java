package LP2.Trabalho_final.service;

import LP2.Trabalho_final.exception.handler.RegistroNaoEcontradoException;
import LP2.Trabalho_final.model.Uf;
import LP2.Trabalho_final.repository.UfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UfService {

    @Autowired
    private UfRepository ufRepository;

    public Uf salvar(Uf uf) {
        return ufRepository.save(uf);
    }

    public List<Uf> buscaTodos() {
        return ufRepository.findAll();
    }

    public Uf buscaPorId(Integer id) {
        Optional<Uf> uf = ufRepository.findById(id);
        return uf.orElseThrow(() -> new RegistroNaoEcontradoException("UF não encontrada"));
    }
}
