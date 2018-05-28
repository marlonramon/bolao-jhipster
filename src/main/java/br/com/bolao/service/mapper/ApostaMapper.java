package br.com.bolao.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.bolao.domain.Aposta;
import br.com.bolao.service.dto.ApostaDTO;

/**
 * Mapper for the entity Aposta and its DTO ApostaDTO.
 */
@Mapper(componentModel = "spring", uses = {PartidaMapper.class, PlacarMapper.class})
public interface ApostaMapper extends EntityMapper<ApostaDTO, Aposta> {

    
    ApostaDTO toDto(Aposta aposta);

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
