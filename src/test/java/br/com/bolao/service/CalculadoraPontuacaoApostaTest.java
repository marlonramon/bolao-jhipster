package br.com.bolao.service;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import br.com.bolao.domain.Bolao;
import br.com.bolao.domain.Placar;

public class CalculadoraPontuacaoApostaTest {
	
	private Bolao bolao;
	
	private static final Long PONTOS_ACERTO_EXATO = 20L;
	private static final Long PONTOS_ACERTO_RESULTADO = 10L;
	private static final Long PONTOS_ACERTO_UM_PLACAR = 5L;
	
	
	@Before
	public void before() {
		
		this.bolao = new Bolao();
		
		this.bolao.setPontosAcertoDoisPlacares(PONTOS_ACERTO_EXATO);
		this.bolao.setPontosAcertoResultado(PONTOS_ACERTO_RESULTADO);
		this.bolao.setPontosAcertoUmPlacar(PONTOS_ACERTO_UM_PLACAR);
		
	}

	@Test
	public void deveCalcularPontosAcertoExatoQuandoPlacaresIguaisAoDaAposta() {
		
		Long pontuacaoEsperada = PONTOS_ACERTO_EXATO;
		
		Short placarMandante = 2;
		Short placarVisitante = 3;
		
		Short placarMandanteAposta = 2;
		Short placarVisitanteAposta = 3;
		
		
		Placar placarAposta = new Placar(placarMandanteAposta, placarVisitanteAposta);
		Placar placarPartida = new Placar(placarMandante, placarVisitante);
		
		
		Bolao bolao = new Bolao();
		
		bolao.setPontosAcertoDoisPlacares(20L);
		bolao.setPontosAcertoResultado(10L);
		bolao.setPontosAcertoUmPlacar(5L);
		
		
		Long pontuacaoAposta = new CalculadoraPontuacaoAposta(placarAposta, placarPartida, bolao).calcularPontuacao();
		
		assertEquals(pontuacaoEsperada, pontuacaoAposta);
		
	}
	
	
	@Test
	public void deveCalcularPontosAcertoResultadoQuandoPlacaresComApenasResultadoIgualAoDaAposta() {
		
		Long pontuacaoEsperada = PONTOS_ACERTO_RESULTADO;
		
		Short placarMandante = 2;
		Short placarVisitante = 3;
		
		Short placarMandanteAposta = 5;
		Short placarVisitanteAposta = 7;
		
		
		Placar placarAposta = new Placar(placarMandanteAposta, placarVisitanteAposta);
		Placar placarPartida = new Placar(placarMandante, placarVisitante);
		
		
		Bolao bolao = new Bolao();
		
		bolao.setPontosAcertoDoisPlacares(20L);
		bolao.setPontosAcertoResultado(10L);
		bolao.setPontosAcertoUmPlacar(5L);
		
		
		Long pontuacaoAposta = new CalculadoraPontuacaoAposta(placarAposta, placarPartida, bolao).calcularPontuacao();
		
		assertEquals(pontuacaoEsperada, pontuacaoAposta);
		
	}
	
	
	@Test
	public void deveCalcularPontosAcertoResultadoEUmPlacarQuandoPlacaresComApenasResultadoIgualAoDaAposta() {
		
		Long pontuacaoEsperada = PONTOS_ACERTO_RESULTADO + PONTOS_ACERTO_UM_PLACAR;
		
		Short placarMandante = 2;
		Short placarVisitante = 3;
		
		Short placarMandanteAposta = 2;
		Short placarVisitanteAposta = 7;
		
		
		Placar placarAposta = new Placar(placarMandanteAposta, placarVisitanteAposta);
		Placar placarPartida = new Placar(placarMandante, placarVisitante);
		
		
		Bolao bolao = new Bolao();
		
		bolao.setPontosAcertoDoisPlacares(20L);
		bolao.setPontosAcertoResultado(10L);
		bolao.setPontosAcertoUmPlacar(5L);
		
		
		Long pontuacaoAposta = new CalculadoraPontuacaoAposta(placarAposta, placarPartida, bolao).calcularPontuacao();
		
		assertEquals(pontuacaoEsperada, pontuacaoAposta);
		
	}
	
	@Test
	public void deveCalcularPontosAcertoUmPlacarQuandoPlacaresComApenasUmPlacarIgualAoDaAposta() {
		
		Long pontuacaoEsperada = PONTOS_ACERTO_UM_PLACAR;
		
		Short placarMandante = 2;
		Short placarVisitante = 2;
		
		Short placarMandanteAposta = 2;
		Short placarVisitanteAposta = 7;
		
		
		Placar placarAposta = new Placar(placarMandanteAposta, placarVisitanteAposta);
		Placar placarPartida = new Placar(placarMandante, placarVisitante);
		
		
		Bolao bolao = new Bolao();
		
		bolao.setPontosAcertoDoisPlacares(20L);
		bolao.setPontosAcertoResultado(10L);
		bolao.setPontosAcertoUmPlacar(5L);
		
		
		Long pontuacaoAposta = new CalculadoraPontuacaoAposta(placarAposta, placarPartida, bolao).calcularPontuacao();
		
		assertEquals(pontuacaoEsperada, pontuacaoAposta);
		
	}


}
