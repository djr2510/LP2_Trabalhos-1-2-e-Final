package LP2.Trabalho_final.repository;

import LP2.Trabalho_final.model.Cep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CepRepository extends JpaRepository<Cep, Integer> {
    List<Cep> findByCidadeNome(String nomeCidade);

    List<Cep> findByCidadeUfSigla(String sigla);

}
