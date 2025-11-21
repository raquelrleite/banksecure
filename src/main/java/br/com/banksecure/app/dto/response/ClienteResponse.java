package br.com.banksecure.app.dto.response;

import java.time.LocalDate;

public record ClienteResponse(Long id, String nome, String cpf, LocalDate dataNascimento) {
}
