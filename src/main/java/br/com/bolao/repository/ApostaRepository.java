package br.com.bolao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.bolao.domain.Aposta;
import br.com.bolao.domain.Rodada;
import br.com.bolao.domain.User;


@Repository
public interface ApostaRepository extends JpaRepository<Aposta, Long> {
	
	Aposta findByRodadaAndUser(Rodada rodada, User user);
	
	

}
