package br.com.bolao.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Placar {
	
	@Column(name="placar_mandante")
	private Short placarMandante;
	
	@Column(name="placar_visitante")
	private Short placarVisitante;
	
	public Placar() {
	
	}
	
	public Placar(Short placarMandante, Short placarVisitante) {
		this.placarMandante = placarMandante;
		this.placarVisitante = placarVisitante;
	}
	
	public Short getPlacarMandante() {
		return placarMandante;
	}
	
	public void setPlacarMandante(Short placarMandante) {
		this.placarMandante = placarMandante;
	}
	
	public Short getPlacarVisitante() {
		return placarVisitante;
	}
	
	public void setPlacarVisitante(Short placarVisitante) {
		this.placarVisitante = placarVisitante;
	}
	
	public boolean isValido() {
		return placarMandante != null && placarVisitante != null;
	}
	
	

}
