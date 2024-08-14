package com.LP2.Trabalho.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Calendar;
import java.util.Objects;

@Entity
public class ContatoModel {

    private static Integer proximoId = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Nome não pode ser nulo")
    private String nome;

    @NotNull(message = "Telefone não pode ser nulo")
    private Integer telefone;

    private String email;

    @NotNull(message = "Nascimento não pode ser nulo")
    private Calendar nascimento;

    public ContatoModel() {
        this.id = proximoId++;
    }

    public ContatoModel(String nome, Integer telefone, String email, Calendar nascimento) {
        this.id = proximoId++;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.nascimento = nascimento;
    }
    public ContatoModel(Integer id,String nome, Integer telefone, String email, Calendar nascimento) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.nascimento = nascimento;
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

    public Integer getTelefone() {
        return telefone;
    }

    public void setTelefone(Integer telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Calendar getNascimento() {
        return nascimento;
    }

    public void setNascimento(Calendar nascimento) {
        this.nascimento = nascimento;
    }

    public static Integer getProximoId() {
        return proximoId;
    }

    public static void setProximoId(Integer proximoId) {
        ContatoModel.proximoId = proximoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContatoModel that = (ContatoModel) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(nome, that.nome) &&
                Objects.equals(telefone, that.telefone) &&
                Objects.equals(email, that.email) &&
                Objects.equals(nascimento, that.nascimento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, telefone, email, nascimento);
    }
}
