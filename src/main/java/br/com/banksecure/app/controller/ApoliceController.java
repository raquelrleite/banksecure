package br.com.banksecure.app.controller;

import br.com.banksecure.app.dto.request.ApoliceRequest;
import br.com.banksecure.app.dto.request.ApoliceUpdateRequest;
import br.com.banksecure.app.dto.response.ApoliceResponse;
import br.com.banksecure.app.service.ApoliceService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("apolices")
public class ApoliceController {

    private final ApoliceService service;

    public ApoliceController(ApoliceService service) {
        this.service = service;
    }

    @PostMapping
    public ApoliceResponse gerar(@RequestBody @Valid ApoliceRequest request) {
        return service.gerarApolice(request);
    }
    @GetMapping
    public List<ApoliceResponse> apolicesAvencer(
            @RequestHeader("X-Funcionario-Id") Long funcionarioId) {
        return service.apolicesAvencer(funcionarioId);
    }

    @PostMapping("renovacao/{apoliceId}")
    public ApoliceResponse renovar(
            @PathVariable Long apoliceId,
            @RequestHeader("X-Funcionario-Id") Long funcionarioId) {
        return service.renovar(funcionarioId, apoliceId);
    }

    @PatchMapping("{apoliceId}")
    public ApoliceResponse atualizar(@PathVariable Long apoliceId,
                                     @RequestBody @Valid ApoliceUpdateRequest request,
                                     @RequestHeader("X-Funcionario-Id") Long funcionarioId) {
        return service.atualizar(apoliceId, request, funcionarioId);

    }

    @DeleteMapping("{apoliceId}")
    public void excluir(@PathVariable Long apoliceId,
                        @RequestHeader("X-Funcionario-Id") Long funcionarioId) {
        service.excluir(funcionarioId, apoliceId);
    }
}
