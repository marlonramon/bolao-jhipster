package br.com.bolao.service.dto;

import java.io.Serializable;

public class RankingDTO implements Serializable, Comparable<RankingDTO> {

	private static final long serialVersionUID = 1L;

	private int posicao;

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
		this.posicao = posicao;
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

	@Override
	public int compareTo(RankingDTO rank) {

		if (this.pontuacaoAtual > rank.getPontuacaoAtual()) {
			return 1;
		} else if (this.pontuacaoAtual.intValue() == rank.getPontuacaoAtual().intValue()) {
			if (this.apostaCerteira > rank.getApostaCerteira()) {
				return 1;
			} else if (this.apostaCerteira == rank.getApostaCerteira()) {
				if (this.apostaResultado > rank.apostaResultado) {
					return 1;
				} else if (this.apostaResultado == rank.apostaResultado) {
					if (this.apostaUmPlacar > rank.getApostaUmPlacar()) {
						return 1;
					} else if (this.apostaUmPlacar == rank.getApostaUmPlacar()) {
						return 0;
					} else {
						return -1;
					}
				}
			}
		}

		return -1;
	}

}
