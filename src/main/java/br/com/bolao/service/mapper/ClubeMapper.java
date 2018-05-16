package br.com.bolao.service.mapper;

import br.com.bolao.domain.*;
import br.com.bolao.service.dto.ClubeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Clube and its DTO ClubeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ClubeMapper extends EntityMapper<ClubeDTO, Clube> {



    default Clube fromId(Long id) {
        if (id == null) {
            return null;
        }
        Clube clube = new Clube();
        clube.setId(id);
        return clube;
    }
}
