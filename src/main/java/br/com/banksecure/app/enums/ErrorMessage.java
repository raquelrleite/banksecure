package br.com.banksecure.app.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum ErrorMessage {
    MENOR_IDADE("Cliente deve ser maior de 18 anos."),
    SEGURO_NAO_ENCONTRADO("Seguro não encontrado!");


    private String message;
}
