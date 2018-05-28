package br.com.bolao.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.bolao.domain.Campeonato;
import br.com.bolao.domain.Rodada;
import br.com.bolao.repository.CampeonatoRepository;
import br.com.bolao.repository.RodadaRepository;

@Service
@Transactional
public class RodadaService {
	
	@Autowired
	private RodadaRepository rodadaRepository;
	
	
	@Autowired
	private CampeonatoRepository campeonatoRepository;
	
	public List<Rodada> findRodadaByidCampeonato(Long idCapeonato) {
		
		Campeonato campeonato = campeonatoRepository.findOne(idCapeonato);
		
		return rodadaRepository.findByCampeonato(campeonato);
		
	}
	
	
	

}
