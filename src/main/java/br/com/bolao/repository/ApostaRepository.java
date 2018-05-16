package br.com.bolao.repository;

import br.com.bolao.domain.Aposta;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Aposta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApostaRepository extends JpaRepository<Aposta, Long> {

}
