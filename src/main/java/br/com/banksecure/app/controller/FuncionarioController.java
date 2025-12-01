package br.com.banksecure.app.controller;

import br.com.banksecure.app.dto.request.FuncionarioRequest;
import br.com.banksecure.app.dto.request.LoginRequest;
import br.com.banksecure.app.dto.response.FuncionarioResponse;
import br.com.banksecure.app.service.FuncionarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("funcionarios")
public class FuncionarioController {

    private final FuncionarioService service;

    public FuncionarioController(FuncionarioService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<FuncionarioResponse> cadastrar(@RequestBody @Valid FuncionarioRequest request) {
        FuncionarioResponse funcionario = service.cadastrarFuncionario(request);
        return ResponseEntity.ok(funcionario);
    }

    @PostMapping("login")
    public ResponseEntity<FuncionarioResponse> login(@RequestBody @Valid LoginRequest request) {
        FuncionarioResponse funcionario = service.login(request);
        return ResponseEntity.ok(funcionario);
    }
}
