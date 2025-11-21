package br.com.banksecure.app.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "funcionario")
public class Funcionario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Nome é obrigatório.")
    private String nome;

    @Column(nullable = false)
    @NotBlank(message = "Cargo é obrigatório.")
    private String cargo;

    @Column(nullable = false,  unique = true)
    @NotBlank(message = "Usuário é obrigatório.")
    private String username;

    @Column(nullable = false)
    @NotBlank(message = "Senha é obrigatória.")
    private String password;
}
