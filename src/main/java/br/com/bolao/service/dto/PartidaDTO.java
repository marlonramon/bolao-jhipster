package br.com.bolao.service.dto;


import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Partida entity.
 */
public class PartidaDTO implements Serializable {

    private Long id;

    @NotNull
    private ZonedDateTime dataPartida;

    private Long clubeMandanteId;

    private Long clubeVisitanteId;

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

    public Long getClubeMandanteId() {
        return clubeMandanteId;
    }

    public void setClubeMandanteId(Long clubeId) {
        this.clubeMandanteId = clubeId;
    }

    public Long getClubeVisitanteId() {
        return clubeVisitanteId;
    }

    public void setClubeVisitanteId(Long clubeId) {
        this.clubeVisitanteId = clubeId;
    }

    public Long getRodadaId() {
        return rodadaId;
    }

    public void setRodadaId(Long rodadaId) {
        this.rodadaId = rodadaId;
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
