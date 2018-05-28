package br.com.bolao.service.dto;


import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import javax.validation.constraints.NotNull;

public class PartidaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

	private Long id;

    @NotNull
    private ZonedDateTime dataPartida;

    private ClubeDTO clubeMandante;

    private ClubeDTO clubeVisitante;
    
    private PlacarDTO placar;
    
    private Long rodadaId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDataPartida() {
        return dataPartida;
    }

    public void setDataPartida(ZonedDateTime dataPartida) {
        this.dataPartida = dataPartida;
    }

    public ClubeDTO getClubeMandante() {
		return clubeMandante;
	}
    
    public void setClubeMandante(ClubeDTO clubeMandante) {
		this.clubeMandante = clubeMandante;
	}
    
    public ClubeDTO getClubeVisitante() {
		return clubeVisitante;
	}
    
    public void setClubeVisitante(ClubeDTO clubeVisitante) {
		this.clubeVisitante = clubeVisitante;
	}
    
    
    public Long getRodadaId() {
        return rodadaId;
    }

    public void setRodadaId(Long rodadaId) {
        this.rodadaId = rodadaId;
    }
    
    public PlacarDTO getPlacar() {
		return placar;
	}
    
    public void setPlacar(PlacarDTO placar) {
		this.placar = placar;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PartidaDTO partidaDTO = (PartidaDTO) o;
        if(partidaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), partidaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PartidaDTO{" +
            "id=" + getId() +
            ", dataPartida='" + getDataPartida() + "'" +
            "}";
    }
}
