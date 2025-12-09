package br.com.banksecure.app.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ApolicesAVencerResponse(
        Long id,
        String nomeCliente,
        String cpfCliente,
        String tituloSeguro,
        BigDecimal valorFinal,
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate fimVigencia,
        Long diasParaVencer
) { }
