package br.com.bolao.repository;

import br.com.bolao.domain.Campeonato;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Campeonato entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CampeonatoRepository extends JpaRepository<Campeonato, Long> {

}
