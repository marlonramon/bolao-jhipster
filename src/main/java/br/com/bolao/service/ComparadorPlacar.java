package br.com.bolao.service;

import br.com.bolao.domain.Placar;

public class ComparadorPlacar {
	
	private Placar primeiroPlacar;
	private Placar segundoPlacar;
	
	public ComparadorPlacar(Placar primeiroPlacar,Placar segundoPlacar ) {
		this.primeiroPlacar = primeiroPlacar;
		this.segundoPlacar  = segundoPlacar;
	}
	
	
	public boolean isEquals() {
		
		Short primeiroPlacarPlacarMandante = primeiroPlacar.getPlacarMandante();
		Short segundoPlacarPlacarMandante = segundoPlacar.getPlacarMandante();
		
		boolean isPlacarMandantePrimeiroPlacarEqualsToPlacarMandanteSegundoPlacar = primeiroPlacarPlacarMandante.compareTo(segundoPlacarPlacarMandante) == 0;
		
		boolean isPlacarVisitantePrimeiroPlacarEqualsToPlacarVisitanteSegundoPlacar = primeiroPlacar.getPlacarVisitante().compareTo(segundoPlacar.getPlacarVisitante()) == 0;
		
		return isPlacarMandantePrimeiroPlacarEqualsToPlacarMandanteSegundoPlacar && isPlacarVisitantePrimeiroPlacarEqualsToPlacarVisitanteSegundoPlacar;
	}
	
	
	public boolean isOneEqual() {
		
		Short primeiroPlacarPlacarMandante = primeiroPlacar.getPlacarMandante();
		Short segundoPlacarPlacarMandante = segundoPlacar.getPlacarMandante();
		
		boolean isPlacarMandantePrimeiroPlacarEqualsToPlacarMandanteSegundoPlacar = primeiroPlacarPlacarMandante.compareTo(segundoPlacarPlacarMandante) == 0;
		
		boolean isPlacarVisitantePrimeiroPlacarEqualsToPlacarVisitanteSegundoPlacar = primeiroPlacar.getPlacarVisitante().compareTo(segundoPlacar.getPlacarVisitante()) == 0;
		
		return isPlacarMandantePrimeiroPlacarEqualsToPlacarMandanteSegundoPlacar || isPlacarVisitantePrimeiroPlacarEqualsToPlacarVisitanteSegundoPlacar;
	}

}
