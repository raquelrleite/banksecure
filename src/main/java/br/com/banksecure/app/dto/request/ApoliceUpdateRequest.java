package br.com.banksecure.app.dto.request;

import br.com.banksecure.app.enums.ApoliceStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record ApoliceUpdateRequest(
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate inicioVigencia,

        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate fimVigencia,

        ApoliceStatus status
) {
}
