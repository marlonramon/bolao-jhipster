package br.com.bolao.service.dto;


import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;


public class ApostaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

    private ZonedDateTime dataAposta;

    private PartidaDTO partida;
    
    private PlacarDTO placar;
    
    private Long pontuacao;
    
    private String userFirstName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getUserFirstName() {
		return userFirstName;
	}
    
    public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}

    public ZonedDateTime getDataAposta() {
        return dataAposta;
    }

    public void setDataAposta(ZonedDateTime dataAposta) {
        this.dataAposta = dataAposta;
    }

    public PartidaDTO getPartida() {
        return partida;
    }

    public void setPartida(PartidaDTO partida) {
        this.partida = partida;
    }
    
    public PlacarDTO getPlacar() {
		return placar;
	}
    
    public void setPlacar(PlacarDTO placar) {
		this.placar = placar;
	}
    
    public Long getPontuacao() {
		return pontuacao;
	}
    
    public void setPontuacao(Long pontuacao) {
		this.pontuacao = pontuacao;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ApostaDTO apostaDTO = (ApostaDTO) o;
        if(apostaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), apostaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ApostaDTO{" +
            "id=" + getId() +
            ", dataAposta='" + getDataAposta() + "'" +
            "}";
    }
}
