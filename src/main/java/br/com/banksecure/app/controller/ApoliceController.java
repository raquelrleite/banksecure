package br.com.banksecure.app.controller;

import br.com.banksecure.app.dto.request.ApoliceRequest;
import br.com.banksecure.app.dto.response.ApoliceResponse;
import br.com.banksecure.app.service.ApoliceService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

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
}
