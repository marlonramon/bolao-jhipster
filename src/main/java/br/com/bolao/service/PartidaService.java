package br.com.bolao.service;

import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.bolao.domain.Aposta;
import br.com.bolao.domain.Bolao;
import br.com.bolao.domain.Campeonato;
import br.com.bolao.domain.Partida;
import br.com.bolao.repository.ApostaRepository;
import br.com.bolao.repository.BolaoRepository;

@Service
@Transactional
public class PartidaService {
	
	@Autowired
	private ApostaRepository apostaRepository;
	
	@Autowired
	private BolaoRepository bolaoRepository;
	
	
	
	public void encerrarPartida(Partida partida) {
		
		Set<Aposta> apostasDaPartida = apostaRepository.findByPartida(partida);
		
		for (Aposta aposta : apostasDaPartida) {
			
			Campeonato campeonato = partida.getRodada().getCampeonato();
			
			Bolao bolao = bolaoRepository.findByCampeonatoAndUser(campeonato, aposta.getUser().getLogin());
			
			
			
			
		}
		
		
	}
	
	
	private Enum

}
