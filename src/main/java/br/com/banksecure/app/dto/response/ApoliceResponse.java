package br.com.banksecure.app.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ApoliceResponse(Long id, Long idCliente,
                              Long idSeguro,
                              BigDecimal valorFinal,
                              @JsonFormat(pattern = "dd/MM/yyyy")
                              LocalDate inicioVigencia,
                              @JsonFormat(pattern = "dd/MM/yyyy")
                              LocalDate fimVigencia)
{ }
