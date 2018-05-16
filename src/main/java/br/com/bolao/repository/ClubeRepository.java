package br.com.bolao.repository;

import br.com.bolao.domain.Clube;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Clube entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClubeRepository extends JpaRepository<Clube, Long> {

}
