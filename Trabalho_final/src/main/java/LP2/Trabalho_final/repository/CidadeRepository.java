package LP2.Trabalho_final.repository;

import LP2.Trabalho_final.model.Cidade;
import LP2.Trabalho_final.model.Uf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Integer> {
    List<Cidade> findByUf(Uf uf);
}
