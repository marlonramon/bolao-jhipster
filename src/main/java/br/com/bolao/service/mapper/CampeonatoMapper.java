package br.com.bolao.service.mapper;

import br.com.bolao.domain.*;
import br.com.bolao.service.dto.CampeonatoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Campeonato and its DTO CampeonatoDTO.
 */
@Mapper(componentModel = "spring", uses = {BolaoMapper.class})
public interface CampeonatoMapper extends EntityMapper<CampeonatoDTO, Campeonato> {

    
    default Campeonato fromId(Long id) {
        if (id == null) {
            return null;
        }
        Campeonato campeonato = new Campeonato();
        campeonato.setId(id);
        return campeonato;
    }
}
