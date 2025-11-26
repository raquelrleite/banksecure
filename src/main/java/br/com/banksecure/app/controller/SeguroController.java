package br.com.banksecure.app.controller;

import br.com.banksecure.app.dto.request.SeguroRequest;
import br.com.banksecure.app.dto.request.SeguroUpdateRequest;
import br.com.banksecure.app.dto.response.SeguroResponse;
import br.com.banksecure.app.service.SeguroService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("seguros")
public class SeguroController {

    private final SeguroService service;

    public SeguroController(SeguroService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<SeguroResponse> cadastrar(@RequestBody @Valid SeguroRequest request,
                                                    @RequestHeader("X-Funcionario-Id") Long funcionarioId) {
        var autenticar = service.cadastrar(request, funcionarioId);
        return ResponseEntity.ok(autenticar);
    }

    @GetMapping
    public ResponseEntity<List<SeguroResponse>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @PatchMapping("{id}")
    public ResponseEntity<SeguroResponse> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid SeguroUpdateRequest request,
            @RequestHeader("X-Funcionario-Id") Long funcionarioId) {

        var autenticar = service.atualizar(id, request, funcionarioId);
        return ResponseEntity.ok(autenticar);
    }

    @DeleteMapping("{idSeguro}")
    public ResponseEntity<String> excluir(@PathVariable Long idSeguro,
                                          @RequestHeader("X-Funcionario-Id") Long funcionarioId) {
        service.excluir(idSeguro, funcionarioId);
        return ResponseEntity.ok("Seguro deletado!");
    }


}
