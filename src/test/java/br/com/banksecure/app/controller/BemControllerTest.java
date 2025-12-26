package br.com.banksecure.app.controller;

import br.com.banksecure.app.dto.request.BemRequest;
import br.com.banksecure.app.dto.request.BemUpdateRequest;
import br.com.banksecure.app.dto.response.BemResponse;
import br.com.banksecure.app.enums.TipoSeguroeBem;
import br.com.banksecure.app.service.BemService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.format.DateTimeFormatter;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BemController.class)
public class BemControllerTest {

    private static final String URL_BASE = "/bem";
    private static final String HEADER_FUNC_ID = "X-Funcionario-Id";
    private static final Long ID_PADRAO = 1L;


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private BemService service;

    @Test
    void deveCadastrarBemComSucesso() throws Exception {

        BemRequest request = BemRequest.builder()
                .clienteId(ID_PADRAO)
                .tipo(TipoSeguroeBem.RESIDENCIAL)
                .descricao("Rua Localhost, nº 127.0.0.1, Apto 404")
                .build();

        BemResponse response = BemResponse.builder()
                .id(ID_PADRAO)
                .clienteId(ID_PADRAO)
                .tipo(TipoSeguroeBem.RESIDENCIAL)
                .descricao("Rua Localhost, nº 127.0.0.1, Apto 404")
                .build();

        when(service.cadastrar(request, ID_PADRAO)).thenReturn(response);

        mockMvc.perform(post(URL_BASE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HEADER_FUNC_ID, ID_PADRAO)
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
                .id(ID_PADRAO)
                .clienteId(ID_PADRAO)
                .tipo(TipoSeguroeBem.RESIDENCIAL)
                .descricao("Rua Localhost, nº 500")
                .build();

        when(service.atualizar(ID_PADRAO, updateRequest, ID_PADRAO)).thenReturn(responseUpdate);

        mockMvc.perform(patch(URL_BASE + "/{bemId}", ID_PADRAO)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HEADER_FUNC_ID, ID_PADRAO)
                        .content(objectMapper.writeValueAsString(updateRequest)))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(responseUpdate.id()))
                .andExpect(jsonPath("$.tipo").value(responseUpdate.tipo().name()))
                .andExpect(jsonPath("$.descricao").value(responseUpdate.descricao()));


    }
}