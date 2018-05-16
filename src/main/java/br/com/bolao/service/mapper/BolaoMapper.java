package br.com.bolao.service.mapper;

import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import br.com.bolao.domain.Bolao;
import br.com.bolao.service.dto.BolaoDTO;

/**
 * Mapper for the entity Bolao and its DTO BolaoDTO.
 */
@Mapper(componentModel = "spring", uses = { UserMapper.class })
public interface BolaoMapper extends EntityMapper<BolaoDTO, Bolao> {

	@Named("lazy")
	@Mapping(target = "usersBolaos",source="usersBolaos", ignore = true)
	BolaoDTO toDtoLazy(Bolao entity);

	@IterableMapping(qualifiedByName = "lazy")
	List<BolaoDTO> toDtoLazy(List<Bolao> entityList);

	default Bolao fromId(Long id) {
		if (id == null) {
			return null;
		}
		Bolao bolao = new Bolao();
		bolao.setId(id);
		return bolao;
	}
}
