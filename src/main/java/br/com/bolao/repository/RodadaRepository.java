package br.com.bolao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.bolao.domain.Campeonato;
import br.com.bolao.domain.Rodada;


@Repository
public interface RodadaRepository extends JpaRepository<Rodada, Long> {
	
	List<Rodada> findByCampeonato(Campeonato campeonato);

}
