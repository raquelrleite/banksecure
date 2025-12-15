package br.com.banksecure.app.repository;

import br.com.banksecure.app.domain.Bem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BemRepository extends JpaRepository<Bem, Long> {
    List<Bem> findByClienteId(Long clienteId);
}
