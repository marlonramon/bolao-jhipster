package br.com.bolao.service.dto;


import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import javax.validation.constraints.NotNull;

public class RodadaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

	private Long id;

    private Long numero;

    @NotNull
    private ZonedDateTime inicioRodada;
    
    @NotNull
    private ZonedDateTime fimRodada;

    private Long campeonatoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public ZonedDateTime getInicioRodada() {
        return inicioRodada;
    }

    public void setInicioRodada(ZonedDateTime inicioRodada) {
        this.inicioRodada = inicioRodada;
    }
    
    public ZonedDateTime getFimRodada() {
		return fimRodada;
	}
    
    public void setFimRodada(ZonedDateTime fimRodada) {
		this.fimRodada = fimRodada;
	}

    public Long getCampeonatoId() {
        return campeonatoId;
    }

    public void setCampeonatoId(Long campeonatoId) {
        this.campeonatoId = campeonatoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RodadaDTO rodadaDTO = (RodadaDTO) o;
        if(rodadaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rodadaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RodadaDTO{" +
            "id=" + getId() +
            ", numero=" + getNumero() +
            ", inicioRodada='" + getInicioRodada() + "'" +
            "}";
    }
}
