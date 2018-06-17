package br.com.bolao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.bolao.domain.Partida;
import br.com.bolao.domain.Rodada;



@Repository
public interface PartidaRepository extends JpaRepository<Partida, Long> {
	
	List<Partida> findByRodadaOrderByDataPartida(Rodada rodada);
	
	
	
	
}
