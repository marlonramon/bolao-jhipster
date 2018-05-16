package br.com.bolao.service.mapper;

import br.com.bolao.domain.*;
import br.com.bolao.service.dto.PartidaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Partida and its DTO PartidaDTO.
 */
@Mapper(componentModel = "spring", uses = {ClubeMapper.class, RodadaMapper.class})
public interface PartidaMapper extends EntityMapper<PartidaDTO, Partida> {

    @Mapping(source = "clubeMandante.id", target = "clubeMandanteId")
    @Mapping(source = "clubeVisitante.id", target = "clubeVisitanteId")
    @Mapping(source = "rodada.id", target = "rodadaId")
    PartidaDTO toDto(Partida partida);

    @Mapping(source = "clubeMandanteId", target = "clubeMandante")
    @Mapping(source = "clubeVisitanteId", target = "clubeVisitante")
    @Mapping(source = "rodadaId", target = "rodada")
    Partida toEntity(PartidaDTO partidaDTO);

    default Partida fromId(Long id) {
        if (id == null) {
            return null;
        }
        Partida partida = new Partida();
        partida.setId(id);
        return partida;
    }
}
