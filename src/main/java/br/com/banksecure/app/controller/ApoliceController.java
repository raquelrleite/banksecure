package br.com.banksecure.app.controller;

import br.com.banksecure.app.dto.request.ApoliceRequest;
import br.com.banksecure.app.dto.request.RenovacaoApoliceRequest;
import br.com.banksecure.app.dto.response.ApoliceResponse;
import br.com.banksecure.app.dto.response.ApolicesAVencerResponse;
import br.com.banksecure.app.service.ApoliceService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("apolices")
public class ApoliceController {

    private final ApoliceService service;

    public ApoliceController(ApoliceService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ApoliceResponse> gerar(@RequestBody @Valid ApoliceRequest request) {
        ApoliceResponse response = service.gerarApolice(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/a-vencer")
    public ResponseEntity<List<ApolicesAVencerResponse>> listarApolicesAVencer() {
        List<ApolicesAVencerResponse> apolices = service.listarApolicesAVencer();
        return ResponseEntity.ok(apolices);
    }

    @PostMapping("/renovar")
    public ResponseEntity<ApoliceResponse> renovar(@RequestBody @Valid RenovacaoApoliceRequest request, @RequestHeader("X-Funcionario-Id") Long funcionarioId) {
        ApoliceResponse response = service.renovarApolice(request);
        return ResponseEntity.ok(response);
    }
}
