package br.com.bolao.service.mapper;

import org.mapstruct.Mapper;

import br.com.bolao.domain.Placar;
import br.com.bolao.service.dto.PlacarDTO;

@Mapper(componentModel = "spring")
public interface PlacarMapper extends EntityMapper<PlacarDTO, Placar>  {
	

}
