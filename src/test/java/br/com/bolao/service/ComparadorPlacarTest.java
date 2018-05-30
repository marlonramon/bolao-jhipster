package br.com.bolao.service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.com.bolao.domain.Placar;

public class ComparadorPlacarTest {

	@Test
	public void deveRetornarVerdadeiroQuandoOsDoisPlacaresForamIguais() {
		
		Short placarMandante = 2;
		Short placarVisitante = 7;
		
		Placar primeiroPlacar = new Placar(placarMandante,placarVisitante);
		
		Placar segundoPlacar = new Placar(placarMandante,placarVisitante);
		
		boolean isEquals = new ComparadorPlacar(primeiroPlacar,segundoPlacar).isEquals();
		
		assertEquals(Boolean.TRUE, isEquals);
		
	}
	
	
	@Test
	public void deveRetornarFalsoQuandoOsDoisPlacaresForamDiferentes() {
		
		Short placarMandante = 2;
		Short placarVisitante = 7;
		
		Short placarMandanteSegundoPlacar = 5;
		Short placarVisitanteSegundoPlacar = 10;
		
		Placar primeiroPlacar = new Placar(placarMandante,placarVisitante);
		
		Placar segundoPlacar = new Placar(placarMandanteSegundoPlacar,placarVisitanteSegundoPlacar);
		
		boolean isEquals = new ComparadorPlacar(primeiroPlacar,segundoPlacar).isEquals();
		
		assertEquals(Boolean.FALSE, isEquals);
		
	}
	
	
	
	@Test
	public void deveRetornarVerdadeiroQuandoUmDosPlacaresForemIguais() {
		
		Short placarMandante = 2;
		Short placarVisitante = 10;
		
		Short placarMandanteSegundoPlacar = 2;
		Short placarVisitanteSegundoPlacar = 7;
		
		Placar primeiroPlacar = new Placar(placarMandante,placarVisitante);
		
		Placar segundoPlacar = new Placar(placarMandanteSegundoPlacar,placarVisitanteSegundoPlacar);
		
		boolean isEquals = new ComparadorPlacar(primeiroPlacar,segundoPlacar).isOneEqual();
		
		assertEquals(Boolean.TRUE, isEquals);
		
	}
	
	@Test
	public void deveRetornarFalsoQuandoNenhumDosPlacaresForemIguais() {
		
		Short placarMandante = 2;
		Short placarVisitante = 10;
		
		Short placarMandanteSegundoPlacar = 10;
		Short placarVisitanteSegundoPlacar = 2;
		
		Placar primeiroPlacar = new Placar(placarMandante,placarVisitante);
		
		Placar segundoPlacar = new Placar(placarMandanteSegundoPlacar,placarVisitanteSegundoPlacar);
		
		boolean isEquals = new ComparadorPlacar(primeiroPlacar,segundoPlacar).isOneEqual();
		
		assertEquals(Boolean.FALSE, isEquals);
		
	}
	
	
	

}
