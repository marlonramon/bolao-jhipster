package br.com.bolao.service.dto;


import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Aposta entity.
 */
public class ApostaDTO implements Serializable {

    private Long id;

    private ZonedDateTime dataAposta;

    private Long partidaId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDataAposta() {
        return dataAposta;
    }

    public void setDataAposta(ZonedDateTime dataAposta) {
        this.dataAposta = dataAposta;
    }

    public Long getPartidaId() {
        return partidaId;
    }

    public void setPartidaId(Long partidaId) {
        this.partidaId = partidaId;
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
