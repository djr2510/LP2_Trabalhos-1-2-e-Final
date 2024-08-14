package com.LP2.Trabalho.exception;

public class RegistroNaoEcontradoException extends RuntimeException {

    public RegistroNaoEcontradoException(String mensagem) {
        super(mensagem);
    }
}