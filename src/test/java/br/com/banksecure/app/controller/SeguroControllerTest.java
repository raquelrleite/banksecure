package br.com.banksecure.app.controller;

import br.com.banksecure.app.dto.request.ClienteRequest;
import br.com.banksecure.app.dto.request.SeguroRequest;
import br.com.banksecure.app.dto.request.SeguroUpdateRequest;
import br.com.banksecure.app.dto.response.SeguroResponse;
import br.com.banksecure.app.enums.TipoSeguroeBem;
import br.com.banksecure.app.service.ClienteService;
import br.com.banksecure.app.service.SeguroService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SeguroController.class)
public class SeguroControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private SeguroService service;

    private SeguroRequest request;
    private SeguroResponse response;

    @BeforeEach
    void setUp() {
        request = SeguroRequest.builder()
                .titulo("Seguro Celular Essencial")
                .coberturaMinima("Apenas roubo e furto qualificado.")
                .valorPremioBase(new BigDecimal("49.90"))
                .tipo(TipoSeguroeBem.CELULAR)
                .build();

        response = SeguroResponse.builder()
                .id(1L)
                .titulo("Seguro Celular Essencial")
                .coberturaMinima("Apenas roubo e furto qualificado.")
                .valorPremioBase(new BigDecimal("49.90"))
                .tipo(TipoSeguroeBem.CELULAR)
                .build();
    }

    @Test
    void deveCadastrarSeguroComSucesso() throws Exception {
        when(service.cadastrar(request, 1L)).thenReturn(response);

        mockMvc.perform(post("/seguros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Funcionario-Id", 1L)
                        .content(objectMapper.writeValueAsString(request)))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(response.id()))
                .andExpect(jsonPath("$.titulo").value(response.titulo()))
                .andExpect(jsonPath("$.valorPremioBase").value(response.valorPremioBase().doubleValue()))
                .andExpect(jsonPath("$.tipo").value(response.tipo().toString()));
    }

    @Test
    void deveListarTodosSeguros() throws Exception {
        when(service.listarTodos()).thenReturn(List.of(response));

        mockMvc.perform(get("/seguros"))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(response.id()));
    }

    @Test
    void deveAualizarSegurosComSucesso() throws Exception {
        SeguroUpdateRequest updateRequest = SeguroUpdateRequest.builder()
                .titulo("Seguro Celular Essencial")
                .coberturaMinima("Apenas roubo.")
                .valorPremioBase(new BigDecimal("57.90"))
                .build();

        SeguroResponse updateResponse = SeguroResponse.builder()
                .id(1L)
                .titulo("Seguro Celular Essencial")
                .coberturaMinima("Apenas roubo.")
                .valorPremioBase(new BigDecimal("57.90"))
                .tipo(TipoSeguroeBem.CELULAR)
                .build();

    when(service.atualizar(1L, updateRequest, 1L)).thenReturn(updateResponse);

    mockMvc.perform(patch("/seguros/{seguroId}", 1L)
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("X-Funcionario-Id", 1L)
                    .content(objectMapper.writeValueAsString(updateRequest)))

            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(updateResponse.id()))
            .andExpect(jsonPath("$.titulo").value(updateResponse.titulo()))
            .andExpect(jsonPath("$.coberturaMinima").value(updateResponse.coberturaMinima()))
            .andExpect(jsonPath("$.valorPremioBase").value(updateResponse.valorPremioBase().doubleValue()));
    }

    @Test
    void deveExcluirSeguroComSucesso() throws Exception {
        mockMvc.perform(delete("/seguros/{seguroId}", 1L)
                        .header("X-Funcionario-Id", 1L))

                .andExpect(status().isOk());
    }
}