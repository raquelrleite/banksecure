package br.com.banksecure.app.repository;

import br.com.banksecure.app.domain.entity.Apolice;
import br.com.banksecure.app.enums.TipoDeSeguro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApoliceRepository extends JpaRepository<Apolice, Long> {

    boolean existsByBemId(Long bemId);
    boolean existsByClienteIdAndSeguro_Tipo(Long clienteId, TipoDeSeguro tipo);
}


