package br.com.banksecure.app.repository;

import br.com.banksecure.app.domain.entity.Apolice;
// import br.com.banksecure.app.enums.TipoDeSeguro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ApoliceRepository extends JpaRepository<Apolice, Long> {

    @Query("SELECT a FROM Apolice a WHERE a.fimVigencia > :dataAtual AND a.fimVigencia <= :dataLimite ORDER BY a.fimVigencia ASC")
    List<Apolice> findApolicesAVencer(LocalDate dataAtual, LocalDate dataLimite);

    // boolean existsByBemId(Long bemId);
    // boolean existsByClienteIdAndSeguro_Tipo(Long clienteId, TipoDeSeguro tipo);
}


