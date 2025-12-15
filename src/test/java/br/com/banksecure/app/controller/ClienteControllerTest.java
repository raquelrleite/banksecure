package br.com.banksecure.app.controller;


import br.com.banksecure.app.dto.request.ClienteRequest;
import br.com.banksecure.app.dto.response.ClienteResponse;
import br.com.banksecure.app.service.ClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClienteController.class)
public class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ClienteService service;

    private ClienteRequest request;
    private ClienteResponse response;

    @BeforeEach
    void setUp() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        request = ClienteRequest.builder()
                .nome("João da Silva")
                .cpf("983.107.290-14")
                .dataNascimento(LocalDate.parse("15/01/2000", fmt))
                .build();

        response = ClienteResponse.builder()
                .id(1L)
                .nome("João da Silva")
                .cpf("983.107.290-14")
                .dataNascimento(LocalDate.parse("15/01/2000", fmt))
                .build();
    }

    @Test
    void cadastrar() throws Exception {
        when(service.cadastrar(request, 1L)).thenReturn(response);

        mockMvc.perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Funcionario-Id", 1L)
                        .content(objectMapper.writeValueAsString(request)))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("João da Silva"))
                .andExpect(jsonPath("$.cpf").value("983.107.290-14"))
                .andExpect(jsonPath("$.dataNascimento").value("15/01/2000"));
    }

    @Test
    void listarTodosClientes() throws Exception {
        when(service.listarTodosClientes(1L)).thenReturn(List.of(response));

        mockMvc.perform(get("/clientes")
                        .header("X-Funcionario-Id", 1L))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$.length()").value(1));
    }
}