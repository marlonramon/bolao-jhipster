package br.com.bolao.service.mapper;

import br.com.bolao.domain.*;
import br.com.bolao.service.dto.RodadaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Rodada and its DTO RodadaDTO.
 */
@Mapper(componentModel = "spring", uses = {CampeonatoMapper.class})
public interface RodadaMapper extends EntityMapper<RodadaDTO, Rodada> {

    @Mapping(source = "campeonato.id", target = "campeonatoId")
    RodadaDTO toDto(Rodada rodada);

    @Mapping(source = "campeonatoId", target = "campeonato")
    Rodada toEntity(RodadaDTO rodadaDTO);

    default Rodada fromId(Long id) {
        if (id == null) {
            return null;
        }
        Rodada rodada = new Rodada();
        rodada.setId(id);
        return rodada;
    }
}
