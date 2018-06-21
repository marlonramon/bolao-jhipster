package br.com.bolao.service;

import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.bolao.domain.Aposta;
import br.com.bolao.domain.Bolao;
import br.com.bolao.domain.Campeonato;
import br.com.bolao.domain.Partida;
import br.com.bolao.domain.Placar;
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
		
		Placar placarPartida = partida.getPlacar();
		
		if (placarPartida != null && placarPartida.isValido()) {
			
			Set<Aposta> apostasDaPartida = apostaRepository.findByPartida(partida);
			
			for (Aposta aposta : apostasDaPartida) {
				
				if (aposta.isValida()) {
					
					new ValidadorDataAposta(aposta, aposta.getPartida()).validar();
					
					aposta = atualizarPontuacaoAposta(partida, aposta);
					
					apostaRepository.save(aposta);					
					
				}
			}
		}
		
	}



	private Aposta atualizarPontuacaoAposta(Partida partida, Aposta aposta) {
		
		Campeonato campeonato = partida.getRodada().getCampeonato();
		
		Bolao bolao = bolaoRepository.findByCampeonatoAndUser(campeonato, aposta.getUser());
		
		Placar placarAposta  = aposta.getPlacar();
		Placar placarPartida = partida.getPlacar();
		
		Long pontuacaoAposta =  new CalculadoraPontuacaoAposta(placarAposta, placarPartida, bolao).calcularPontuacao();
		
		aposta.setPontuacao(pontuacaoAposta);
		
		return aposta;
	}
	
	


}
