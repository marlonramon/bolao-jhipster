package br.com.bolao.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import br.com.bolao.domain.Aposta;
import br.com.bolao.domain.Partida;
import br.com.bolao.domain.Rodada;
import br.com.bolao.domain.User;
import br.com.bolao.repository.ApostaRepository;
import br.com.bolao.repository.RodadaRepository;

@Service
@Transactional
public class ApostaService {
	
	private ApostaRepository apostaRepository;
	private RodadaRepository rodadaRepository;
	private UserService userService;
	
	public ApostaService(RodadaRepository rodadaRepository, ApostaRepository apostaRepository ) {
		this.rodadaRepository = rodadaRepository;		
		this.apostaRepository = apostaRepository;		
	}
	
	
	
	public List<Aposta> obterApostasUsuarioParaRodada(User user, Long idRodada) {

    	Rodada rodadaSolicitada = rodadaRepository.findOne(idRodada);
    	
    	List<Aposta> apostas = new ArrayList<>();
    	
    	Set<Partida> partidas = rodadaSolicitada.getPartidas();
    	
    	User userLogged = userService.getUserWithAuthorities().get();
    	
    	for (Partida partida : partidas) {
			
    		Aposta aposta = apostaRepository.findByRodadaAndUser(rodadaSolicitada, userLogged);
			
			if (aposta == null) {
				aposta = new Aposta();
				aposta.setUser(userLogged);
				aposta.setPartida(partida);
			}
			
			apostas.add(aposta);			
			
		}
    	
    	return apostas;
	}
	
	

}
