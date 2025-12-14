package br.com.banksecure.app.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Builder;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

@Builder
public record ClienteRequest(
        @NotBlank(message = "O nome do cliente é obrigatório.")
        String nome,

        @NotBlank(message = "O número do CPF é obrigatório.")
        @CPF(message = "CPF informado inválido.")
        String cpf,

        @NotNull(message = "A data de nascimento é obrigatória.")
        @Past(message = "A data de nascimento deve ser anterior à data de hoje.")
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate dataNascimento) {
}
