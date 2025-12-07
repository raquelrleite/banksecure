package br.com.banksecure.app.exception;

public class BemNaoEncontradoException extends RuntimeException {
    public BemNaoEncontradoException(String message) {
        super(message);
    }
}
