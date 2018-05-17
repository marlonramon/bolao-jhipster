package br.com.bolao.service.dto;


import java.io.Serializable;
import java.util.Objects;

public class CampeonatoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

	private Long id;

    private String descricao;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CampeonatoDTO campeonatoDTO = (CampeonatoDTO) o;
        if(campeonatoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), campeonatoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CampeonatoDTO{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            "}";
    }
}
