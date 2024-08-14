package LP2.Trabalho_final.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

@Entity
@Table(name = "cep")
public class Cep {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer num_cep;

    @ManyToOne
    @JoinColumn(name = "cidade_id")
    private Cidade cidade;

    @NotBlank(message = "Logradouro não pode ser nulo")
    private String logradouro;

    @NotNull(message = "Número de início não pode ser nulo")
    private Integer num_inicio;

    @NotNull(message = "Número final não pode ser nulo")
    private Integer num_fim;

    public Cep() {
    }

    public Cep(Integer num_cep, Cidade cidade, String logradouro, Integer num_inicio, Integer num_fim) {
        this.num_cep = num_cep;
        this.cidade = cidade;
        this.logradouro = logradouro;
        this.num_inicio = num_inicio;
        this.num_fim = num_fim;
    }

    public Integer getNum_cep() {
        return num_cep;
    }

    public void setNum_cep(Integer num_cep) {
        this.num_cep = num_cep;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public Integer getNum_inicio() {
        return num_inicio;
    }

    public void setNum_inicio(Integer num_inicio) {
        this.num_inicio = num_inicio;
    }

    public Integer getNum_fim() {
        return num_fim;
    }

    public void setNum_fim(Integer num_fim) {
        this.num_fim = num_fim;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cep cep = (Cep) o;
        return Objects.equals(num_cep, cep.num_cep) &&
                Objects.equals(cidade, cep.cidade) &&
                Objects.equals(logradouro, cep.logradouro) &&
                Objects.equals(num_inicio, cep.num_inicio) &&
                Objects.equals(num_fim, cep.num_fim);
    }

    @Override
    public int hashCode() {
        return Objects.hash(num_cep, cidade, logradouro, num_inicio, num_fim);
    }
}
