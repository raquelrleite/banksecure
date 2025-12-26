package br.com.banksecure.app.controller;

import br.com.banksecure.app.dto.request.FuncionarioRequest;
import br.com.banksecure.app.dto.request.LoginRequest;
import br.com.banksecure.app.dto.response.FuncionarioResponse;
import br.com.banksecure.app.service.FuncionarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FuncionarioController.class)
public class FuncionarioControllerTest {

    private static final String URL_BASE = "/funcionarios";


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private FuncionarioService service;

    private FuncionarioRequest request;
    private FuncionarioResponse response;

    @BeforeEach
    void setUp() {
        request = FuncionarioRequest.builder()
                .nome("Maria Oliveira")
                .cargo("Analista de Riscos")
                .username("maria.oliveira")
                .password("senhaSegura123")
                .build();

        response = FuncionarioResponse.builder()
                .id(1L)
                .nome("Maria Oliveira")
                .cargo("Analista de Riscos")
                .username("maria.oliveira")
                .build();
    }

    @Test
    void deveCadastrarFuncionarioComSucesso() throws Exception {
        when(service.cadastrar(request)).thenReturn(response);

        mockMvc.perform(post(URL_BASE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(response.id()))
                .andExpect(jsonPath("$.nome").value(response.nome()))
                .andExpect(jsonPath("$.cargo").value(response.cargo()))
                .andExpect(jsonPath("$.username").value(response.username()));
    }

    @Test
    void deveLogarFuncionarioComSucesso() throws Exception {
        LoginRequest loginRequest = LoginRequest.builder()
                .username("maria.oliveira")
                .password("senhaSegura123")
                .build();

        when(service.login(loginRequest)).thenReturn(response);

        mockMvc.perform(post(URL_BASE + "/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(response.id()))
                .andExpect(jsonPath("$.username").value(response.username()));
    }
}