package br.com.bolao.service.mapper;

import br.com.bolao.domain.*;
import br.com.bolao.service.dto.ApostaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Aposta and its DTO ApostaDTO.
 */
@Mapper(componentModel = "spring", uses = {PartidaMapper.class})
public interface ApostaMapper extends EntityMapper<ApostaDTO, Aposta> {

    @Mapping(source = "partida.id", target = "partidaId")
    ApostaDTO toDto(Aposta aposta);

    @Mapping(source = "partidaId", target = "partida")
    Aposta toEntity(ApostaDTO apostaDTO);

    default Aposta fromId(Long id) {
        if (id == null) {
            return null;
        }
        Aposta aposta = new Aposta();
        aposta.setId(id);
        return aposta;
    }
}
