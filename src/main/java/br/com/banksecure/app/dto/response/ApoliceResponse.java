package br.com.banksecure.app.dto.response;

import br.com.banksecure.app.enums.ApoliceStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record ApoliceResponse(
        Long id,
        Long clienteId,
        Long seguroId,
        BigDecimal valorFinal,

        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate inicioVigencia,

        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate fimVigencia,

        ApoliceStatus status
){
}
