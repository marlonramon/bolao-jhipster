package br.com.bolao.service;

import java.time.ZonedDateTime;
import java.util.Objects;

import br.com.bolao.domain.Aposta;
import br.com.bolao.domain.Partida;
import br.com.bolao.web.rest.errors.ApostaAposInicioPartidaException;
import br.com.bolao.web.rest.errors.CustomParameterizedException;

public class ValidadorAposta {
	
	private Aposta aposta;
	
	public ValidadorAposta(Aposta aposta) {
		 this.aposta = aposta;
	}
	
	public void validar() {
		
		if (Objects.isNull(aposta)) {
			throw new CustomParameterizedException("Aposta inválida");
		}

		Partida partida = aposta.getPartida();
		
		if (Objects.isNull(partida)) {
			throw new CustomParameterizedException("Aposta deve ser de uma partida");
		}
		
		ZonedDateTime dataAposta = aposta.getDataAposta();
		ZonedDateTime dataPartida = partida.getDataPartida();
		
		if (dataAposta.isAfter(dataPartida)) {
			throw new ApostaAposInicioPartidaException();
		}
		
		
	}

}
