package br.com.bolao.service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.bolao.domain.Aposta;
import br.com.bolao.domain.Partida;
import br.com.bolao.domain.Placar;
import br.com.bolao.domain.Rodada;
import br.com.bolao.domain.User;
import br.com.bolao.repository.ApostaRepository;
import br.com.bolao.repository.PartidaRepository;
import br.com.bolao.repository.RodadaRepository;
import br.com.bolao.web.rest.errors.ApostaDuplicadaException;
import br.com.bolao.web.rest.errors.CustomParameterizedException;

@Service
@Transactional
public class ApostaService {
	
	private ApostaRepository apostaRepository;
	private RodadaRepository rodadaRepository;
	
	@Autowired
	private PartidaRepository partidaRepository;
	
	@Autowired
	private UserService userService;
	
	public ApostaService(RodadaRepository rodadaRepository, ApostaRepository apostaRepository ) {
		this.rodadaRepository = rodadaRepository;		
		this.apostaRepository = apostaRepository;		
	}
	
	public List<Aposta> obterApostasUsuarioParaRodada(User user, Long idRodada) {

    	Rodada rodadaSolicitada = rodadaRepository.findOne(idRodada);
    	
    	List<Aposta> apostas = new ArrayList<>();
    	
    	List<Partida> partidas = partidaRepository.findByRodada(rodadaSolicitada);
    	
    	for (Partida partida : partidas) {
    		
    		Aposta apostaPadrao = criarApostaPadrao(partida);
    		
			Aposta aposta = apostaRepository.findByPartidaAndUser(partida, user).orElse(apostaPadrao);
			
			apostas.add(aposta);			
			
		}
    	
    	return apostas;
	}
	
	
	private Aposta criarApostaPadrao(Partida partida) {
		
		Aposta aposta = new Aposta();
		
		aposta.setPartida(partida);
		aposta.setPlacar(new Placar());
		
		return aposta;
		
	}
	
	public Aposta criarAposta(Aposta aposta) {
		
		if (aposta.isValida()) {
		
			User user = userService.getUserWithAuthorities().get();
			
			Optional<Aposta> apostaBanco = apostaRepository.findByPartidaAndUser(aposta.getPartida(), user);
			
			if (apostaBanco.isPresent()) {
				throw new ApostaDuplicadaException("Já existe uma aposta para esta partida e usuário");
			}
			
			aposta.setUser(user);
			aposta.setDataAposta(ZonedDateTime.now());
			
			new ValidadorAposta(aposta).validar();
			
			aposta = apostaRepository.save(aposta);
			
		}
		
		return aposta;
		
	}
	
	public Aposta alterarAposta(Aposta aposta) {
		
		if (aposta.isValida()) {
			
			User user = userService.getUserWithAuthorities().get();
			
			Aposta apostaBanco = apostaRepository.findOne(aposta.getId());
			
			User usuarioAposta = apostaBanco.getUser();
			
			if (!user.equals(usuarioAposta)) {
				throw new CustomParameterizedException("Não é possivel alterar a aposta de outro usuário");
			}
			
			apostaBanco.setPlacar(aposta.getPlacar());
			apostaBanco.setDataAposta(ZonedDateTime.now());		
			
			new ValidadorAposta(apostaBanco).validar();
			
			aposta = apostaRepository.save(apostaBanco);
			
		}
		
		return aposta;
		
	}
	
	

}
