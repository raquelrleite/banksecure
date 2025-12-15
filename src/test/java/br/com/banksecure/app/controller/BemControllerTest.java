package br.com.banksecure.app.controller;

import br.com.banksecure.app.dto.request.BemRequest;
import br.com.banksecure.app.dto.request.BemUpdateRequest;
import br.com.banksecure.app.dto.response.BemResponse;
import br.com.banksecure.app.enums.TipoSeguroeBem;
import br.com.banksecure.app.service.BemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.webmvc.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BemController.class)
public class BemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private BemService service;

    @Test
    void deveCadastrarBemComSucesso() throws Exception {

        BemRequest request = BemRequest.builder()
                .clienteId(1L)
                .tipo(TipoSeguroeBem.RESIDENCIAL)
                .descricao("Rua Localhost, nº 127.0.0.1, Apto 404")
                .build();

        BemResponse response = BemResponse.builder()
                .id(1L)
                .clienteId(1L)
                .tipo(TipoSeguroeBem.RESIDENCIAL)
                .descricao("Rua Localhost, nº 127.0.0.1, Apto 404")
                .build();

        when(service.cadastrar(request, 1L)).thenReturn(response);

        mockMvc.perform(post("/bem")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Funcionario-Id", 1L)
                        .content(objectMapper.writeValueAsString(request)))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(response.id()))
                .andExpect(jsonPath("$.clienteId").value(response.clienteId()))
                .andExpect(jsonPath("$.tipo").value(response.tipo().name()));
    }

    @Test
    void atualizar() throws Exception {
        BemUpdateRequest updateRequest = BemUpdateRequest.builder()
                .tipo(TipoSeguroeBem.RESIDENCIAL)
                .descricao("Rua Localhost, nº 500")
                .build();

        BemResponse responseUpdate = BemResponse.builder()
                .id(1L)
                .clienteId(1L)
                .tipo(TipoSeguroeBem.RESIDENCIAL)
                .descricao("Rua Localhost, nº 500")
                .build();

        when(service.atualizar(1L, updateRequest, 1L)).thenReturn(responseUpdate);

        mockMvc.perform(patch("/bem/{bemId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Funcionario-Id", 1L)
                        .content(objectMapper.writeValueAsString(updateRequest)))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(responseUpdate.id()))
                .andExpect(jsonPath("$.tipo").value(responseUpdate.tipo().name()))
                .andExpect(jsonPath("$.descricao").value(responseUpdate.descricao()));


    }
}