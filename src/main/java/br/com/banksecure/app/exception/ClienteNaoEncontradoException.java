package br.com.banksecure.app.exception;

public class ClienteNaoEncontradoException extends RuntimeException {
  public ClienteNaoEncontradoException(String message) {
    super(message);
  }
}
