package br.com.banksecure.app.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record ClienteResponse(
        Long id,
        String nome,
        String cpf,

        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate dataNascimento) {
}
