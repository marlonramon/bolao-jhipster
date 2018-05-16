package br.com.bolao.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

public class ClubeDTO implements Serializable {

    private static final long serialVersionUID = -3617903671138578360L;

	private Long id;

    @NotNull
    private String nome;

    @NotNull
    @Lob
    private String bandeira;
    private String bandeiraContentType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getBandeira() {
        return bandeira;
    }

    public void setBandeira(String bandeira) {
        this.bandeira = bandeira;
    }

    public String getBandeiraContentType() {
        return bandeiraContentType;
    }

    public void setBandeiraContentType(String bandeiraContentType) {
        this.bandeiraContentType = bandeiraContentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ClubeDTO clubeDTO = (ClubeDTO) o;
        if(clubeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), clubeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ClubeDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", bandeira='" + getBandeira() + "'" +
            "}";
    }
}
