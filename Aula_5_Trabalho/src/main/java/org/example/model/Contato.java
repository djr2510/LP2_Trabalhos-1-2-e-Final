package org.example.model;

import java.util.Calendar;

public class Contato {
    private static Integer proximoId = 1;
    private int id;
    private String nome;
    private int numero;
    private String email;
    private Calendar nascimento;

    public Contato() {
    }

    public Contato(String nome, int numero, String email, Calendar nascimento) {
        this.id = proximoId++;
        this.nome = nome;
        this.numero = numero;
        this.email = email;
        this.nascimento = nascimento;
    }
    public Contato(int id,String nome, int numero, String email, Calendar nascimento) {
        this.id = id;
        this.nome = nome;
        this.numero = numero;
        this.email = email;
        this.nascimento = nascimento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
