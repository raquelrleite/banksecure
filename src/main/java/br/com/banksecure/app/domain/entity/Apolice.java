package br.com.banksecure.app.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "apolice")
public class Apolice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Cliente é obrigatório.")
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @NotNull(message = "Tipo de seguro é obrigatório.")
    @ManyToOne
    @JoinColumn(name = "seguro_id", nullable = false)
    private Seguro seguro;

    @Column(nullable = false)
    @NotNull
    private BigDecimal valorFinal;

    @Column(nullable = false)
    @NotNull
    private LocalDate dataInicioVigencia;

    @Column(nullable = false)
    @NotNull
    private LocalDate dataFimVigencia;

}
