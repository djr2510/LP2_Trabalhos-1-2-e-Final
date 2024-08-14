package LP2.Trabalho_final.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "uf")
public class Uf {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Sigla não pode ser nula")
    @Column(unique = true)
    private String sigla;

    @NotBlank(message = "Nome não pode ser nulo")
    private String nome;

    public Uf() {
    }

    public Uf(String sigla, String nome) {
        this.sigla = sigla;
        this.nome = nome;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Uf uf = (Uf) o;
        return Objects.equals(sigla, uf.sigla) &&
                Objects.equals(nome, uf.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sigla, nome);
    }
}

