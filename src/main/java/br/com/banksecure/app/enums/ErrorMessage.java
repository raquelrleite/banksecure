package br.com.banksecure.app.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorMessage {
    MENOR_IDADE("Cliente deve ser maior de 18 anos."),
    MAIOR_QUE_120("Cliente tem mais de 120 anos."),
    SEGURO_NAO_ENCONTRADO("Seguro não encontrado!"),
    LOGIN_INVALIDO("Usuário ou senha incorretos"),
    USERNAME_INVALIDO("Usuário já existe."),
    CPF_JA_EXISTE("Já existe um cliente cadastrado com este CPF.");

    private String message;
}
