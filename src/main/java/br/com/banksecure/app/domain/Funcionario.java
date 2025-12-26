package br.com.banksecure.app.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@Entity
@Table(name = "funcionario")
public class Funcionario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String cargo;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;
}
