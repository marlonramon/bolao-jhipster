package br.com.bolao.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.junit.Test;

import br.com.bolao.domain.Aposta;
import br.com.bolao.domain.Partida;
import br.com.bolao.web.rest.errors.ApostaAposInicioPartidaException;

public class ValidadorApostaTest {

	@Test(expected=ApostaAposInicioPartidaException.class)
	public void deveRetornarErroAoValidarUmaApostaAposAHoraLimite() {
		
		LocalDateTime dataPartida = LocalDateTime.of(2018, 6, 14, 12, 00);
		
		LocalDateTime dataAposta = LocalDateTime.of(2018, 6, 14, 11, 51);
		
		Aposta aposta = new Aposta();
		
		aposta.setDataAposta(ZonedDateTime.of(dataAposta,ZoneId.systemDefault()));
		
		Partida partida = new Partida();
		partida.setDataPartida(ZonedDateTime.of(dataPartida,ZoneId.systemDefault()));
		
		aposta.setPartida(partida);
		
		new ValidadorDataAposta(aposta).validar();;
		
	}
	
	
	@Test
	public void naoDeveRetornarErroAoValidarUmaApostaDentroDaHoraLimite() {
		
		LocalDateTime dataPartida = LocalDateTime.of(2018, 6, 14, 12, 00);
		
		LocalDateTime dataAposta = LocalDateTime.of(2018, 6, 14, 11, 50);
		
		Aposta aposta = new Aposta();
		
		aposta.setDataAposta(ZonedDateTime.of(dataAposta,ZoneId.systemDefault()));
		
		Partida partida = new Partida();
		partida.setDataPartida(ZonedDateTime.of(dataPartida,ZoneId.systemDefault()));
		
		aposta.setPartida(partida);
		
		new ValidadorDataAposta(aposta).validar();;
		
	}

}
