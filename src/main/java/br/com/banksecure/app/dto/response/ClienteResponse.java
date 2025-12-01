package br.com.banksecure.app.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record ClienteResponse(Long id, String nome, String cpf,
                              @JsonFormat(pattern = "dd/MM/yyyy")
                              LocalDate dataNascimento) {
}
