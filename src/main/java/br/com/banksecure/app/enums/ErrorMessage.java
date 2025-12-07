package br.com.banksecure.app.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorMessage {

    MENOR_IDADE("Permitido apenas para maiores de 18 anos."),
    MAIOR_QUE_120("Idade excede o limite permitido."),

    SEGURO_NAO_ENCONTRADO("Seguro não encontrado."),
    CLIENTE_NAO_ENCONTRADO("Cliente não encontrado."),
    APOLICE_NAO_ENCONTRADA("Apólice não encontrada."),
    BEM_NAO_ENCONTRADO("Bem não localizado para este cliente."),

    LOGIN_INVALIDO("Usuário ou senha inválidos."),
    ACESSO_NEGADO("Acesso não autorizado."),

    USERNAME_INVALIDO("Nome de usuário indisponível."),
    CPF_JA_EXISTE("CPF já cadastrado."),
    SEGURO_JA_EXISTE("Já existe um seguro cadastrado com este título."),

    CLIENTE_POSSUI_SEGVIDA("Cliente já possui Seguro de Vida ativo."),
    BEM_POSSUI_SEGURO("Este bem já possui seguro vigente."),
    BEM_NAO_PERTENCE_AO_CLIENTE("O bem informado não pertence ao cliente titular da apólice.");

    private final String message;
}