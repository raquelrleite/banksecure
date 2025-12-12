package br.com.banksecure.app.controller;

import br.com.banksecure.app.dto.request.SeguroRequest;
import br.com.banksecure.app.dto.request.SeguroUpdateRequest;
import br.com.banksecure.app.dto.response.SeguroResponse;
import br.com.banksecure.app.service.SeguroService;
import jakarta.validation.Valid;
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
    public SeguroResponse cadastrar(@RequestBody @Valid SeguroRequest request,
                                    @RequestHeader("X-Funcionario-Id") Long funcionarioId) {

        return service.cadastrar(request, funcionarioId);
    }

    @GetMapping
    public List<SeguroResponse> listarTodos() {
        return service.listarTodos();
    }

    @PatchMapping("{id}")
    public SeguroResponse atualizar(
            @PathVariable Long id,
            @RequestBody @Valid SeguroUpdateRequest request,
            @RequestHeader("X-Funcionario-Id") Long funcionarioId) {
        return service.atualizar(id, request, funcionarioId);
    }

    @DeleteMapping("{idSeguro}")
    public void excluir(@PathVariable Long idSeguro,
                        @RequestHeader("X-Funcionario-Id") Long funcionarioId) {

        service.excluir(idSeguro, funcionarioId);
    }


}
