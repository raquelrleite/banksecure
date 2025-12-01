package br.com.banksecure.app.repository;

import br.com.banksecure.app.domain.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    boolean existsByCpf(String cpf);

    Optional<Cliente> findById(Long clienteId);
}
