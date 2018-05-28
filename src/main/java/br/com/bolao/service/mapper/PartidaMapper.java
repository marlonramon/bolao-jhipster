package br.com.bolao.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.bolao.domain.Partida;
import br.com.bolao.service.dto.PartidaDTO;

/**
 * Mapper for the entity Partida and its DTO PartidaDTO.
 */
@Mapper(componentModel = "spring", uses = {ClubeMapper.class, RodadaMapper.class, ClubeMapper.class, PlacarMapper.class})
public interface PartidaMapper extends EntityMapper<PartidaDTO, Partida> {

    @Mapping(source = "rodada.id", target = "rodadaId")
    PartidaDTO toDto(Partida partida);

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
