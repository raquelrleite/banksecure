package br.com.banksecure.app.repository;

import br.com.banksecure.app.domain.entity.Bem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BemRepository extends JpaRepository<Bem, Long> {
}
