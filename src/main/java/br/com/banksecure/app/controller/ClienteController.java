package br.com.banksecure.app.controller;

import br.com.banksecure.app.dto.request.ClienteRequest;
import br.com.banksecure.app.dto.response.ClienteResponse;
import br.com.banksecure.app.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("clientes")
public class ClienteController {

    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    @PostMapping
    public ClienteResponse cadastrar(@RequestBody @Valid ClienteRequest request,
                                     @RequestHeader("X-Funcionario-Id") Long funcionarioId) {
        return service.cadastrar(request, funcionarioId);
    }

    @GetMapping
    public List<ClienteResponse> listarTodosClientes(@RequestHeader("X-Funcionario-Id") Long funcionarioId) {
        return service.listarTodosClientes(funcionarioId);
    }
}
