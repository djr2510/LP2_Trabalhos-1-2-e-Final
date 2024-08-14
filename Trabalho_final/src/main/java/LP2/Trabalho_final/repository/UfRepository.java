package LP2.Trabalho_final.repository;

import LP2.Trabalho_final.model.Uf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UfRepository extends JpaRepository<Uf, Integer> {
}
