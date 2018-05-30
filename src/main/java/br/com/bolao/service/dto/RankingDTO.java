package br.com.bolao.service.dto;

import java.io.Serializable;

public class RankingDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long idBolao;
	
	private String login;
	
	private String username;
	
	private Long pontuacaoAtual;
	
	public RankingDTO() {
	
	}
	
	public RankingDTO(Long idBolao, String login, String userName, Long pontuacaoAtual) {
		this.idBolao = idBolao;
		this.login = login;
		this.username = userName;
		this.pontuacaoAtual = pontuacaoAtual;
	}

	public Long getIdBolao() {
		return idBolao;
	}

	public void setIdBolao(Long idBolao) {
		this.idBolao = idBolao;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String userName) {
		this.username = userName;
	}

	public Long getPontuacaoAtual() {
		return pontuacaoAtual;
	}

	public void setPontuacaoAtual(Long pontuacaoAtual) {
		this.pontuacaoAtual = pontuacaoAtual;
	}
	
	

}
