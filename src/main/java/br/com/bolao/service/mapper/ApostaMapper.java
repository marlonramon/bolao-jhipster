package br.com.bolao.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.bolao.domain.Aposta;
import br.com.bolao.service.dto.ApostaDTO;

@Mapper(componentModel = "spring", uses = {PartidaMapper.class, PlacarMapper.class})
public interface ApostaMapper extends EntityMapper<ApostaDTO, Aposta> {

	@Mapping(source="user.firstName" , target="userFirstName")
    ApostaDTO toDto(Aposta aposta);

    @Mapping(target="user", ignore=true)
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
