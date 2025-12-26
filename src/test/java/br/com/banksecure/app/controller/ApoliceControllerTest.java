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

    private static final String URL_BASE = "/apolices";
    private static final String HEADER_FUNC_ID = "X-Funcionario-Id";
    private static final Long ID_PADRAO = 1L;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

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
        LocalDate inicio = LocalDate.now();
        LocalDate fim = inicio.plusYears(1);

        request = ApoliceRequest.builder()
                .clienteId(ID_PADRAO)
                .seguroId(ID_PADRAO)
                .bemId(ID_PADRAO)
                .build();

        updateRequest = ApoliceUpdateRequest.builder()
                .inicioVigencia(inicio.plusYears(1))
                .fimVigencia(fim.plusYears(1))
                .build();

        responseUpdate = ApoliceResponse.builder()
                .id(ID_PADRAO)
                .inicioVigencia(inicio.plusYears(1))
                .fimVigencia(fim.plusYears(1))
                .build();

        response = ApoliceResponse.builder()
                .id(ID_PADRAO)
                .clienteId(ID_PADRAO)
                .seguroId(ID_PADRAO)
                .valorFinal(new BigDecimal("100.00"))
                .inicioVigencia(inicio)
                .fimVigencia(fim)
                .status(ApoliceStatus.ATIVA)
                .build();
    }

    @Test
    void deveGerarApoliceComSucesso() throws Exception {
        when(service.gerarApolice(any(ApoliceRequest.class), eq(ID_PADRAO)))
                .thenReturn(response);

        mockMvc.perform(post(URL_BASE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HEADER_FUNC_ID, ID_PADRAO)
                        .content(objectMapper.writeValueAsString(request)))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(response.id()))
                .andExpect(jsonPath("$.valorFinal").value(100.00))
                .andExpect(jsonPath("$.status").value(response.status().name()));
    }

    @Test
    void deveListarApolicesAVencer() throws Exception {
        DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        when(service.apolicesAvencer(ID_PADRAO)).thenReturn(List.of(response));

        mockMvc.perform(get(URL_BASE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HEADER_FUNC_ID, ID_PADRAO))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(response.id()))
                .andExpect(jsonPath("$[0].inicioVigencia").value(response.inicioVigencia().format(FORMATTER)))
                .andExpect(jsonPath("$[0].fimVigencia").value(response.fimVigencia().format(ApoliceControllerTest.FORMATTER)))
                .andExpect(jsonPath("$[0].status").value(response.status().name()));
    }

    @Test
    void renovar() throws Exception {
        when(service.renovar(ID_PADRAO, ID_PADRAO)).thenReturn(response);

        mockMvc.perform(post(URL_BASE + "/renovar/{apoliceId}", ID_PADRAO)
                        .header(HEADER_FUNC_ID, ID_PADRAO))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(response.id()))
                .andExpect(jsonPath("$.status").value(response.status().name()));
    }

    @Test
    void deveAtualizarApolice() throws Exception {
        DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        when(service.atualizar(eq(ID_PADRAO), any(ApoliceUpdateRequest.class), eq(ID_PADRAO)))
                .thenReturn(responseUpdate);

        mockMvc.perform(patch("/apolices/{apoliceId}", ID_PADRAO)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HEADER_FUNC_ID, ID_PADRAO)
                        .content(objectMapper.writeValueAsString(updateRequest)))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(responseUpdate.id()))
                .andExpect(jsonPath("$.inicioVigencia").value(responseUpdate.inicioVigencia().format(FORMATTER)))
                .andExpect(jsonPath("$.fimVigencia").value(responseUpdate.fimVigencia().format(FORMATTER)));
    }

    @Test
    void cancelar() throws Exception {
        doNothing().when(service).cancelar(ID_PADRAO, ID_PADRAO);

        mockMvc.perform(put("/apolices/cancelar/{apoliceId}", ID_PADRAO)
                .header(HEADER_FUNC_ID, ID_PADRAO))

                .andExpect(status().isOk());
    }

    @Test
    void deveListarTodasApolices() throws Exception {
        when(service.apolices(ID_PADRAO)).thenReturn(List.of(response));

        mockMvc.perform(get("/apolices/lista")
                .header(HEADER_FUNC_ID, ID_PADRAO))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(response.id()))
                .andExpect(jsonPath("$.length()").value(1));

    }
}