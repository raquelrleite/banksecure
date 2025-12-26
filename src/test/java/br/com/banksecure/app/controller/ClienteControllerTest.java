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

    private static final String URL_BASE = "/clientes";
    private static final String HEADER_FUNC_ID = "X-Funcionario-Id";
    private static final Long ID_PADRAO = 1L;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");


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

        request = ClienteRequest.builder()
                .nome("João da Silva")
                .cpf("983.107.290-14")
                .dataNascimento(LocalDate.parse("15/01/2000", FORMATTER))
                .build();

        response = ClienteResponse.builder()
                .id(ID_PADRAO)
                .nome("João da Silva")
                .cpf("983.107.290-14")
                .dataNascimento(LocalDate.parse("15/01/2000", FORMATTER))
                .build();
    }

    @Test
    void cadastrar() throws Exception {
        when(service.cadastrar(request, ID_PADRAO)).thenReturn(response);

        mockMvc.perform(post(URL_BASE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HEADER_FUNC_ID, ID_PADRAO)
                        .content(objectMapper.writeValueAsString(request)))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID_PADRAO))
                .andExpect(jsonPath("$.nome").value("João da Silva"))
                .andExpect(jsonPath("$.cpf").value("983.107.290-14"))
                .andExpect(jsonPath("$.dataNascimento").value("15/01/2000"));
    }

    @Test
    void listarTodosClientes() throws Exception {
        when(service.listarTodosClientes(ID_PADRAO)).thenReturn(List.of(response));

        mockMvc.perform(get(URL_BASE)
                        .header(HEADER_FUNC_ID, ID_PADRAO))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(ID_PADRAO))
                .andExpect(jsonPath("$.length()").value(1));
    }
}