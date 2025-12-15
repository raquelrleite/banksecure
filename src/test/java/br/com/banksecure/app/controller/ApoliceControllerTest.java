package br.com.banksecure.app.controller;

import br.com.banksecure.app.dto.request.ApoliceRequest;
import br.com.banksecure.app.dto.request.ApoliceUpdateRequest;
import br.com.banksecure.app.dto.response.ApoliceResponse;
import br.com.banksecure.app.enums.ApoliceStatus;
import br.com.banksecure.app.service.ApoliceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ApoliceController.class)
public class ApoliceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ApoliceService service;

    private ApoliceRequest request;
    private ApoliceUpdateRequest updateRequest;
    private ApoliceResponse response;
    private ApoliceResponse responseUpdate;

    @BeforeEach
    void setUp() {

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        LocalDate inicio = LocalDate.parse("14/12/2025", fmt);

        LocalDate fim = LocalDate.parse("14/12/2026", fmt);

        request = ApoliceRequest.builder()
                .clienteId(1L)
                .seguroId(1L)
                .bemId(1L)
                .build();

        updateRequest = ApoliceUpdateRequest.builder()
                .inicioVigencia(inicio.plusYears(1))
                .fimVigencia(fim.plusYears(1))
                .build();

        responseUpdate = ApoliceResponse.builder()
                .id(1L)
                .inicioVigencia(inicio.plusYears(1))
                .fimVigencia(fim.plusYears(1))
                .build();

        response = ApoliceResponse.builder()
                .id(1L)
                .clienteId(1L)
                .seguroId(1L)
                .valorFinal(new BigDecimal("100.00"))
                .inicioVigencia(inicio)
                .fimVigencia(fim)
                .status(ApoliceStatus.ATIVA)
                .build();
    }

    @Test
    void deveGerarApoliceComSucesso() throws Exception {
        when(service.gerarApolice(any(ApoliceRequest.class), eq(1L)))
                .thenReturn(response);

        mockMvc.perform(post("/apolices")
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-Funcionario-Id", 1L)
                .content(objectMapper.writeValueAsString(request)))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(response.id()))
                .andExpect(jsonPath("$.status").value(response.status().name()));
    }

    @Test
    void deveListarApolicesAVencer() throws Exception {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        when(service.apolicesAvencer(1L)).thenReturn(List.of(response));

        mockMvc.perform(get("/apolices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Funcionario-Id", 1L))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(response.id()))
                .andExpect(jsonPath("$[0].inicioVigencia").value(response.inicioVigencia().format(fmt)))
                .andExpect(jsonPath("$[0].fimVigencia").value(response.fimVigencia().format(fmt)))
                .andExpect(jsonPath("$[0].status").value(response.status().name()));
    }

    @Test
    void renovar() throws Exception {
        when(service.renovar(1L, 1L)).thenReturn(response);

        mockMvc.perform(post("/apolices/renovar/{apoliceId}", 1L)
                        .header("X-Funcionario-Id", 1L))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(response.id()))
                .andExpect(jsonPath("$.status").value(response.status().name()));
    }

    @Test
    void deveAtualizarApolice() throws Exception {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        when(service.atualizar(eq(1L), any(ApoliceUpdateRequest.class), eq(1L)))
                .thenReturn(responseUpdate);

        mockMvc.perform(patch("/apolices/{apoliceId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Funcionario-Id", 1L)
                        .content(objectMapper.writeValueAsString(updateRequest)))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(responseUpdate.id()))
                .andExpect(jsonPath("$.inicioVigencia").value(responseUpdate.inicioVigencia().format(fmt)))
                .andExpect(jsonPath("$.fimVigencia").value(responseUpdate.fimVigencia().format(fmt)));
    }

    @Test
    void cancelar() throws Exception {
        doNothing().when(service).cancelar(1L, 1L);

        mockMvc.perform(put("/apolices/cancelar/{apoliceId}", 1L)
                .header("X-Funcionario-Id", 1L))

                .andExpect(status().isOk());
    }

    @Test
    void deveListarTodasApolices() throws Exception {
        when(service.apolices(1L)).thenReturn(List.of(response));

        mockMvc.perform(get("/apolices/lista")
                .header("X-Funcionario-Id", 1L))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(response.id()))
                .andExpect(jsonPath("$.length()").value(1));

    }
}