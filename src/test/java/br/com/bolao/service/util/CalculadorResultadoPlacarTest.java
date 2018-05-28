package br.com.bolao.service.util;

import static org.junit.Assert.*;

import org.junit.Test;

import br.com.bolao.domain.Placar;
import br.com.bolao.domain.Resultado;

public class CalculadorResultadoPlacarTest {

	@Test
	public void deveRetornarEmpateQuandoPlacaresIguais() {
		
		Short placarMandante = 2;
		Short placarVisitante = 2;
		
		Placar placar = new Placar(placarMandante, placarVisitante);
		
		Resultado resultadoCalculado = new CalculadorResultadoPlacar(placar).calcularResultado();
		
		assertEquals(Resultado.EMPATE, resultadoCalculado);
		
		
	}
	
	
	@Test
	public void deveRetornarMandanteQuandoPlacaresMandanteForMaiorQueOVisitante() {
		
		Short placarMandante = 3;
		Short placarVisitante = 2;
		
		Placar placar = new Placar(placarMandante, placarVisitante);
		
		Resultado resultadoCalculado = new CalculadorResultadoPlacar(placar).calcularResultado();
		
		assertEquals(Resultado.MANDANTE, resultadoCalculado);
		
		
	}
	
	
	@Test
	public void deveRetornarVisitanteQuandoPlacaresVisitanteForMaiorQueOMandante() {
		
		Short placarMandante = 3;
		Short placarVisitante = 6;
		
		Placar placar = new Placar(placarMandante, placarVisitante);
		
		Resultado resultadoCalculado = new CalculadorResultadoPlacar(placar).calcularResultado();
		
		assertEquals(Resultado.VISITANTE, resultadoCalculado);
		
		
	}
	

}
