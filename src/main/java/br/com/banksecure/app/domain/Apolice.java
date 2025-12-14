package br.com.banksecure.app.domain;

import br.com.banksecure.app.enums.ApoliceStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "apolice")
public class Apolice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "seguro_id", nullable = false)
    private Seguro seguro;

    @ManyToOne
    @JoinColumn(name = "bem_id")
    private Bem bem;

    @Column(nullable = false)
    private BigDecimal valorFinal;

    @Column(nullable = false)
    private LocalDate inicioVigencia;

    @Column(nullable = false)
    private LocalDate fimVigencia;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApoliceStatus status;
}
