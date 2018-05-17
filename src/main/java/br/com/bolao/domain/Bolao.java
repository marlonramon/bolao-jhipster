package br.com.bolao.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Bolao.
 */
@Entity
@Table(name = "bolao")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Bolao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "descricao", nullable = false)
    private String descricao;

    @NotNull
    @Column(name = "pontos_acerto_dois_placares", nullable = false)
    private Long pontosAcertoDoisPlacares;

    @NotNull
    @Column(name = "pontos_acerto_um_placar", nullable = false)
    private Long pontosAcertoUmPlacar;

    @NotNull
    @Column(name = "pontos_acerto_resultado", nullable = false)
    private Long pontosAcertoResultado;
    
    @ManyToOne
    private Campeonato campeonato;
    

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "bolao_users_bolao",
               joinColumns = @JoinColumn(name="bolaos_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="users_bolaos_id", referencedColumnName="id"))
    private Set<User> usersBolaos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public Bolao descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getPontosAcertoDoisPlacares() {
        return pontosAcertoDoisPlacares;
    }

    public Bolao pontosAcertoDoisPlacares(Long pontosAcertoDoisPlacares) {
        this.pontosAcertoDoisPlacares = pontosAcertoDoisPlacares;
        return this;
    }

    public void setPontosAcertoDoisPlacares(Long pontosAcertoDoisPlacares) {
        this.pontosAcertoDoisPlacares = pontosAcertoDoisPlacares;
    }

    public Long getPontosAcertoUmPlacar() {
        return pontosAcertoUmPlacar;
    }

    public Bolao pontosAcertoUmPlacar(Long pontosAcertoUmPlacar) {
        this.pontosAcertoUmPlacar = pontosAcertoUmPlacar;
        return this;
    }

    public void setPontosAcertoUmPlacar(Long pontosAcertoUmPlacar) {
        this.pontosAcertoUmPlacar = pontosAcertoUmPlacar;
    }

    public Long getPontosAcertoResultado() {
        return pontosAcertoResultado;
    }

    public Bolao pontosAcertoResultado(Long pontosAcertoResultado) {
        this.pontosAcertoResultado = pontosAcertoResultado;
        return this;
    }

    public void setPontosAcertoResultado(Long pontosAcertoResultado) {
        this.pontosAcertoResultado = pontosAcertoResultado;
    }

    public Set<User> getUsersBolaos() {
        return usersBolaos;
    }

    public Bolao usersBolaos(Set<User> users) {
        this.usersBolaos = users;
        return this;
    }

    public Bolao addUsersBolao(User user) {
        this.usersBolaos.add(user);
        return this;
    }

    public Bolao removeUsersBolao(User user) {
        this.usersBolaos.remove(user);
        return this;
    }

    public void setUsersBolaos(Set<User> users) {
        this.usersBolaos = users;
    }
    
    public Campeonato getCampeonato() {
		return campeonato;
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
        Bolao bolao = (Bolao) o;
        if (bolao.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bolao.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Bolao{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            ", pontosAcertoDoisPlacares=" + getPontosAcertoDoisPlacares() +
            ", pontosAcertoUmPlacar=" + getPontosAcertoUmPlacar() +
            ", pontosAcertoResultado=" + getPontosAcertoResultado() +
            "}";
    }
}
