package br.com.bolao.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Aposta.
 */
@Entity
@Table(name = "aposta")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Aposta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_aposta")
    private ZonedDateTime dataAposta;

    @ManyToOne
    private Partida partida;
    
    @Embedded
    private Placar placar;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDataAposta() {
        return dataAposta;
    }

    public Aposta dataAposta(ZonedDateTime dataAposta) {
        this.dataAposta = dataAposta;
        return this;
    }

    public void setDataAposta(ZonedDateTime dataAposta) {
        this.dataAposta = dataAposta;
    }

    public Partida getPartida() {
        return partida;
    }

    public Aposta partida(Partida partida) {
        this.partida = partida;
        return this;
    }

    public void setPartida(Partida partida) {
        this.partida = partida;
    }
    
    public Placar getPlacar() {
		return placar;
	}
    
    public void setPlacar(Placar placar) {
		this.placar = placar;
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
        Aposta aposta = (Aposta) o;
        if (aposta.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), aposta.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Aposta{" +
            "id=" + getId() +
            ", dataAposta='" + getDataAposta() + "'" +
            "}";
    }
}
