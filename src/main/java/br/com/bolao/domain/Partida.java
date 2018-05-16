package br.com.bolao.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Partida.
 */
@Entity
@Table(name = "partida")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Partida implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "data_partida", nullable = false)
    private ZonedDateTime dataPartida;

    @ManyToOne
    private Clube clubeMandante;

    @ManyToOne
    private Clube clubeVisitante;

    @ManyToOne
    private Rodada rodada;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDataPartida() {
        return dataPartida;
    }

    public Partida dataPartida(ZonedDateTime dataPartida) {
        this.dataPartida = dataPartida;
        return this;
    }

    public void setDataPartida(ZonedDateTime dataPartida) {
        this.dataPartida = dataPartida;
    }

    public Clube getClubeMandante() {
        return clubeMandante;
    }

    public Partida clubeMandante(Clube clube) {
        this.clubeMandante = clube;
        return this;
    }

    public void setClubeMandante(Clube clube) {
        this.clubeMandante = clube;
    }

    public Clube getClubeVisitante() {
        return clubeVisitante;
    }

    public Partida clubeVisitante(Clube clube) {
        this.clubeVisitante = clube;
        return this;
    }

    public void setClubeVisitante(Clube clube) {
        this.clubeVisitante = clube;
    }

    public Rodada getRodada() {
        return rodada;
    }

    public Partida rodada(Rodada rodada) {
        this.rodada = rodada;
        return this;
    }

    public void setRodada(Rodada rodada) {
        this.rodada = rodada;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Partida partida = (Partida) o;
        if (partida.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), partida.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Partida{" +
            "id=" + getId() +
            ", dataPartida='" + getDataPartida() + "'" +
            "}";
    }
}
