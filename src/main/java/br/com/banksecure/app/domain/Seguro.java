package br.com.banksecure.app.domain;

import br.com.banksecure.app.enums.TipoSeguroeBem;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "seguro")
public class Seguro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String titulo;

    private String coberturaMinima;

    @Column(nullable = false)
    private BigDecimal valorPremioBase;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoSeguroeBem tipo;
}
