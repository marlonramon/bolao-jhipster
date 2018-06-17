package br.com.bolao.service.dto;

import java.io.Serializable;

public class RankingDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private int posicao = 1;

	private Long idBolao;

	private String login;

	private String username;

	private Long pontuacaoAtual;

	private Long apostaCerteira;

	private Long apostaResultado;

	private Long apostaUmPlacar;

	public RankingDTO() {

	}

	public RankingDTO(Long idBolao, String login, String userName, Long pontuacaoAtual, Long apostaCerteira,
			Long apostaResultado, Long apostaUmPlacar) {
		this.idBolao = idBolao;
		this.login = login;
		this.username = userName;
		this.pontuacaoAtual = pontuacaoAtual;
		this.apostaCerteira = apostaCerteira;
		this.apostaResultado = apostaResultado;
		this.apostaUmPlacar = apostaUmPlacar;
	}

	public int getPosicao() {
		return posicao;
	}
	
	public void setPosicao(int posicao) {
		this.posicao = posicao > 0 ? posicao : 1;
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

	public Long getApostaCerteira() {
		return apostaCerteira;
	}

	public void setApostaCerteira(Long apostaCerteira) {
		this.apostaCerteira = apostaCerteira;
	}

	public Long getApostaResultado() {
		return apostaResultado;
	}

	public void setApostaResultado(Long apostaResultado) {
		this.apostaResultado = apostaResultado;
	}

	public Long getApostaUmPlacar() {
		return apostaUmPlacar;
	}

	public void setApostaUmPlacar(Long apostaUmPlacar) {
		this.apostaUmPlacar = apostaUmPlacar;
	}
	
	public void subirPosicao() {
		this.posicao = posicao-1 > 0 ? posicao-- : posicao;
		
	}
	
	public void descerPosicao() {
		this.posicao++;		
	}
	
	@Override
	public boolean equals(Object obj) {
		RankingDTO ranking = (RankingDTO) obj;
		return this.login.equals(ranking.getLogin());
	}
	

}
