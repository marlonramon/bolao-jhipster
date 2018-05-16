package br.com.bolao.repository;

import br.com.bolao.domain.Partida;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Partida entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PartidaRepository extends JpaRepository<Partida, Long> {

}
