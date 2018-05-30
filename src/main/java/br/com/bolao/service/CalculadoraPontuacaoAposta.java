package br.com.bolao.service;

import br.com.bolao.domain.Bolao;
import br.com.bolao.domain.Placar;
import br.com.bolao.domain.Resultado;
import br.com.bolao.service.util.CalculadorResultadoPlacar;

public class CalculadoraPontuacaoAposta {
	
	private Placar placarAposta;
	
	private Placar placarPartida;
	
	private Bolao bolao;
	
	private ComparadorPlacar comparadorPlacar;
	
	
	
	public CalculadoraPontuacaoAposta(Placar placarAposta, Placar placarPartida, Bolao bolao) {
		this.comparadorPlacar = new ComparadorPlacar(placarPartida, placarAposta);
		this.placarAposta = placarAposta;
		this.placarPartida = placarPartida;
		this.bolao = bolao;
	}
	
	public Long calcularPontuacao() {
		
		Long pontosAcertoPlacarExato = bolao.getPontosAcertoDoisPlacares();
		
		Long pontosAcertoResultado = bolao.getPontosAcertoResultado();
		
		Long pontosAcertoUmPlacar = bolao.getPontosAcertoUmPlacar();
		
		
		if (comparadorPlacar.isEquals()) {
			return pontosAcertoPlacarExato;
		}
		
				
		Long pontosAcumuladosResultadoEPlacar = calcularPontosQuandoApostaNaoAcertouPlacarExato(pontosAcertoResultado, pontosAcertoUmPlacar);
		
		
		return pontosAcumuladosResultadoEPlacar;
	}

	private Long calcularPontosQuandoApostaNaoAcertouPlacarExato(Long pontosAcertoResultado, Long pontosAcertoUmPlacar) {
		
		boolean isApostaComAcertoResultado = isApostaComAcertoResultado(placarPartida, placarAposta);
		
		Long pontosAcumuladosResultadoEPlacar = 0L;
		
		if (isApostaComAcertoResultado) {
			pontosAcumuladosResultadoEPlacar += pontosAcertoResultado;
		}
		
		boolean isUmPlacarApostaCorreto = comparadorPlacar.isOneEqual();
		
		if (isUmPlacarApostaCorreto) {
			pontosAcumuladosResultadoEPlacar += pontosAcertoUmPlacar;
			
		}
		return pontosAcumuladosResultadoEPlacar;
	}
	
	private boolean isApostaComAcertoResultado(Placar placarPartida, Placar placarAposta) {
		CalculadorResultadoPlacar calculadorResultadoPlacar = new CalculadorResultadoPlacar();
		
		Resultado resultadoPartida = calculadorResultadoPlacar.calcularResultado(placarPartida);
		
		Resultado resultadoAposta = calculadorResultadoPlacar.calcularResultado(placarAposta);
		
		return resultadoPartida.equals(resultadoAposta);
	}
	
	
	
	
	

}
