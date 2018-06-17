package br.com.bolao.repository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.bolao.domain.Aposta;
import br.com.bolao.domain.Partida;
import br.com.bolao.domain.Rodada;
import br.com.bolao.domain.User;


@Repository
public interface ApostaRepository extends JpaRepository<Aposta, Long> {
	
	Optional<Aposta> findByPartidaAndUser(Partida partida, User user);
	
	@Query("select aposta from Aposta aposta join fetch aposta.partida partida where partida.dataPartida < :data and aposta.user =:user and partida.rodada =:rodada order by partida.dataPartida desc")
	List<Aposta> findByDateAndUser(@Param("data") ZonedDateTime data, @Param("user") User user, @Param("rodada") Rodada rodada);
	
	Set<Aposta> findByPartida(Partida partida);
	
	

}
