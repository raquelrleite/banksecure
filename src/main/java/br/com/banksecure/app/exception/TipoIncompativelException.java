package br.com.banksecure.app.exception;

public class TipoIncompativelException extends RuntimeException {
    public TipoIncompativelException(String message) {
        super(message);
    }
}
