package br.com.bolao.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

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
    
    @OneToMany(mappedBy="rodada",fetch=FetchType.LAZY)
    private Set<Partida> partidas; 

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
    
    public Set<Partida> getPartidas() {
		return partidas;
	}
    
    public void setPartidas(Set<Partida> partidas) {
		this.partidas = partidas;
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
