package br.com.banksecure.app.repository;

import br.com.banksecure.app.domain.entity.Seguro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeguroRepository extends JpaRepository<Seguro, Long> {
}
