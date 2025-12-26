package br.com.banksecure.app.domain;

import br.com.banksecure.app.enums.TipoSeguroeBem;
import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@Entity
@Table(name = "bem")
public class Bem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoSeguroeBem tipo;

    private String descricao;

}
