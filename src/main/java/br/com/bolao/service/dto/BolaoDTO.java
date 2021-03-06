package br.com.bolao.service.dto;


import java.io.Serializable;
import java.util.Objects;

import javax.validation.constraints.NotNull;


public class BolaoDTO implements Serializable {

    private static final long serialVersionUID = 460728414415156336L;

	private Long id;

    @NotNull
    private String descricao;

    @NotNull
    private Long pontosAcertoDoisPlacares;

    @NotNull
    private Long pontosAcertoUmPlacar;

    @NotNull
    private Long pontosAcertoResultado;
    
    private CampeonatoDTO campeonatoDTO;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getPontosAcertoDoisPlacares() {
        return pontosAcertoDoisPlacares;
    }

    public void setPontosAcertoDoisPlacares(Long pontosAcertoDoisPlacares) {
        this.pontosAcertoDoisPlacares = pontosAcertoDoisPlacares;
    }

    public Long getPontosAcertoUmPlacar() {
        return pontosAcertoUmPlacar;
    }

    public void setPontosAcertoUmPlacar(Long pontosAcertoUmPlacar) {
        this.pontosAcertoUmPlacar = pontosAcertoUmPlacar;
    }

    public Long getPontosAcertoResultado() {
        return pontosAcertoResultado;
    }

    public void setPontosAcertoResultado(Long pontosAcertoResultado) {
        this.pontosAcertoResultado = pontosAcertoResultado;
    }
    
    public CampeonatoDTO getCampeonatoDTO() {
		return campeonatoDTO;
	}
    
    public void setCampeonatoDTO(CampeonatoDTO campeonatoDTO) {
		this.campeonatoDTO = campeonatoDTO;
	}

    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BolaoDTO bolaoDTO = (BolaoDTO) o;
        if(bolaoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bolaoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BolaoDTO{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            ", pontosAcertoDoisPlacares=" + getPontosAcertoDoisPlacares() +
            ", pontosAcertoUmPlacar=" + getPontosAcertoUmPlacar() +
            ", pontosAcertoResultado=" + getPontosAcertoResultado() +
            "}";
    }
}
