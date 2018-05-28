package br.com.bolao.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.bolao.domain.Aposta;
import br.com.bolao.domain.Partida;
import br.com.bolao.domain.User;


@Repository
public interface ApostaRepository extends JpaRepository<Aposta, Long> {
	
	Optional<Aposta> findByPartidaAndUser(Partida partida, User user);
	
	Set<Aposta> findByPartida(Partida partida);
	
	

}
