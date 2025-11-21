package br.com.banksecure.app.dto.request;

import java.time.LocalDate;

public record ClienteRequest(String nome, String cpf, LocalDate dataNascimento) {
}
