package br.com.bolao.service;

import java.util.Objects;

import br.com.bolao.domain.Bolao;
import br.com.bolao.domain.Placar;
import br.com.bolao.domain.Resultado;

public class CalculadoraPontuacaoAposta {
	
	private Placar placarAposta;
	
	private Placar placarPartida;
	
	private Bolao bolao;
	
	public CalculadoraPontuacaoAposta(Placar placarAposta, Placar placarPartida, Bolao bolao) {
		this.placarAposta = placarAposta;
		this.placarPartida = placarPartida;
		this.bolao = bolao;
	}
	
	public Long calculaPontuacao() {
		
		
		
		
		return 1L;
	}
	
	
	
	
	

}
