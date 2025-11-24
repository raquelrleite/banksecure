package br.com.banksecure.app.controller;

import br.com.banksecure.app.dto.request.ClienteRequest;
import br.com.banksecure.app.dto.response.ClienteResponse;
import br.com.banksecure.app.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("clientes")
public class ClienteController {

    private ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<String> cadastrar(@RequestBody @Valid ClienteRequest request) {
        service.cadastrar(request);
        return ResponseEntity.ok("Cliente cadastrado.");
    }

    @GetMapping
    public ResponseEntity<List<ClienteResponse>> listarTodosClientes() {
        return ResponseEntity.ok(service.listarTodosClientes());
    }
}
