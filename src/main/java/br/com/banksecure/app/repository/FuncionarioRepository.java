package br.com.banksecure.app.repository;

import br.com.banksecure.app.domain.entity.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

    Optional<Funcionario> findByUsernameAndPassword(String username, String password);

    boolean existsByUsername(String username);
}
