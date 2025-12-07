package br.com.banksecure.app.controller;

import br.com.banksecure.app.dto.request.BemRequest;
import br.com.banksecure.app.dto.request.BemUpdateRequest;
import br.com.banksecure.app.dto.response.BemResponse;
import br.com.banksecure.app.service.BemService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("bem")
public class BemController {

    private final BemService service;

    public BemController(BemService service) {
        this.service = service;
    }

    @PostMapping
    public BemResponse cadastrar(@RequestBody @Valid BemRequest request,
                                 @RequestHeader("X-Funcionario-Id") Long funcionarioId) {
        return service.cadastrar(request, funcionarioId);
    }

    @PatchMapping("{bemId}")
    public BemResponse atualizar(@PathVariable Long bemId,
                                 @RequestBody @Valid BemUpdateRequest request,
                                 @RequestHeader("X-Funcionario-Id") Long funcionarioId) {
        return service.atualizar(bemId, request, funcionarioId);
    }
}
