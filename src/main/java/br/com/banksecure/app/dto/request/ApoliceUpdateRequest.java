package br.com.banksecure.app.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record ApoliceUpdateRequest(
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate inicioVigencia,

        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate fimVigencia
) {
}
