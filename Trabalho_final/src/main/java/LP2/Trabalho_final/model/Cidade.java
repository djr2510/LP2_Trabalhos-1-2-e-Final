package LP2.Trabalho_final.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

@Entity
@Table(name = "cidade")
public class Cidade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Nome da cidade não pode ser nulo")
    private String nome;

    @ManyToOne
    @JoinColumn(name = "uf_id")
    private Uf uf;

    public Cidade() {
    }

    public Cidade(String nome, Uf uf) {
        this.nome = nome;
        this.uf = uf;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Uf getUf() {
        return uf;
    }

    public void setUf(Uf uf) {
        this.uf = uf;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Uf uf = (Uf) o;
        return Objects.equals(uf, uf.getSigla()) &&
                Objects.equals(nome, uf.getNome());
    }

    @Override
    public int hashCode() {
        return Objects.hash(uf, nome);
    }
}
