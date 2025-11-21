package br.com.banksecure.app.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cliente")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Nome é obrigatório.")
    private String nome;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "O CPF é obrigatório.")
    @CPF(message = "O CPF informado é inválido.")
    private String cpf;

    @Column(nullable = false)
    @NotNull(message = "Data de nascimento é obrigatória.")
    private LocalDate dataNascimento;
}
