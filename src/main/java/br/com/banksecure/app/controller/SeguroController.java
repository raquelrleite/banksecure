package br.com.banksecure.app.controller;

import br.com.banksecure.app.dto.request.SeguroRequest;
import br.com.banksecure.app.dto.request.SeguroUpdateRequest;
import br.com.banksecure.app.dto.response.SeguroResponse;
import br.com.banksecure.app.service.SeguroService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("seguros")
public class SeguroController {

    private final SeguroService service;

    public SeguroController(SeguroService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<SeguroResponse> cadastrar(@RequestBody @Valid SeguroRequest request) {
        SeguroResponse seguro = service.cadastrar(request);
        return ResponseEntity.ok(seguro);
    }

    @PatchMapping("{id}")
    public ResponseEntity<SeguroResponse> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid SeguroUpdateRequest request) {

        SeguroResponse seguro = service.atualizar(id, request);
        return ResponseEntity.ok(seguro);
    }

    @DeleteMapping("{id}")
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }


}
