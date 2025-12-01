package br.com.banksecure.app.exception;

public class UsernameInvalidoException extends RuntimeException {
    public UsernameInvalidoException(String message) {
        super(message);
    }
}
