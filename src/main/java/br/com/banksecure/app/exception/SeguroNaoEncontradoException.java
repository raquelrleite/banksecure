package br.com.banksecure.app.exception;

public class SeguroNaoEncontradoException extends RuntimeException {
    public SeguroNaoEncontradoException(String message) {
        super(message);
    }
}
