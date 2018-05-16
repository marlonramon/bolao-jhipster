package br.com.bolao.repository;

import br.com.bolao.domain.Rodada;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Rodada entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RodadaRepository extends JpaRepository<Rodada, Long> {

}
