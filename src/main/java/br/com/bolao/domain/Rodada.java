package br.com.bolao.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Rodada.
 */
@Entity
@Table(name = "rodada")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Rodada implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero")
    private Long numero;

    @NotNull
    @Column(name = "inicio_rodada", nullable = false)
    private ZonedDateTime inicioRodada;
    
    @NotNull
    @Column(name = "fim_rodada", nullable = false)
    private ZonedDateTime fimRodada;

    @ManyToOne
    private Campeonato campeonato;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumero() {
        return numero;
    }

    public Rodada numero(Long numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public ZonedDateTime getInicioRodada() {
        return inicioRodada;
    }

    public Rodada inicioRodada(ZonedDateTime inicioRodada) {
        this.inicioRodada = inicioRodada;
        return this;
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

    public Campeonato getCampeonato() {
        return campeonato;
    }

    public Rodada campeonato(Campeonato campeonato) {
        this.campeonato = campeonato;
        return this;
    }

    public void setCampeonato(Campeonato campeonato) {
        this.campeonato = campeonato;
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
        Rodada rodada = (Rodada) o;
        if (rodada.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rodada.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Rodada{" +
            "id=" + getId() +
            ", numero=" + getNumero() +
            ", inicioRodada='" + getInicioRodada() + "'" +
            "}";
    }
}
