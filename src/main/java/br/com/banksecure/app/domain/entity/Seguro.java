package br.com.banksecure.app.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "seguro")
public class Seguro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Título é obrigatório.")
    private String titulo;

    private String coberturaMinima;

    @Column(nullable = false)
    @NotNull(message = "O prêmio base é obrigatório.")
    @Positive(message = "O valor do prêmio deve ser maior que zero.")
    private BigDecimal valorPremioBase;
}
