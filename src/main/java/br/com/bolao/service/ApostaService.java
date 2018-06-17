package br.com.bolao.service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

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
import br.com.bolao.web.rest.errors.ApostaInvalidaException;
import br.com.bolao.web.rest.errors.CustomParameterizedException;

@Service
@Transactional
public class ApostaService {

	private ApostaRepository apostaRepository;
	private RodadaRepository rodadaRepository;
	private UserService userService;
	private PartidaRepository partidaRepository;

	public ApostaService(UserService userService, PartidaRepository partidaRepository,
			RodadaRepository rodadaRepository, ApostaRepository apostaRepository) {
		this.rodadaRepository = rodadaRepository;
		this.apostaRepository = apostaRepository;
		this.userService = userService;
		this.partidaRepository = partidaRepository;
	}

	public List<Aposta> obterApostasUsuarioParaRodada(User user, Long idRodada) {

		Rodada rodadaSolicitada = rodadaRepository.findOne(idRodada);

		List<Aposta> apostas = new ArrayList<>();

		List<Partida> partidas = partidaRepository.findByRodadaOrderByDataPartida(rodadaSolicitada);

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
		aposta.setPontuacao(0L);

		return aposta;

	}

	public Aposta criarAposta(Aposta aposta) {

		if (!aposta.isValida()) {
			throw new ApostaInvalidaException();
		}

		User user = userService.getUserWithAuthorities().get();

		Optional<Aposta> apostaBanco = apostaRepository.findByPartidaAndUser(aposta.getPartida(), user);

		if (apostaBanco.isPresent()) {
			throw new ApostaDuplicadaException("Já existe uma aposta para esta partida e usuário");
		}

		aposta.setUser(user);
		aposta.setDataAposta(ZonedDateTime.now());
		aposta.setPontuacao(0L);

		new ValidadorDataAposta(aposta).validar();

		aposta = apostaRepository.save(aposta);

		return aposta;

	}

	public Aposta alterarAposta(Aposta aposta) {

		if (!aposta.isValida()) {
			throw new ApostaInvalidaException();
		}
		User user = userService.getUserWithAuthorities().get();

		Aposta apostaBanco = apostaRepository.findOne(aposta.getId());

		User usuarioAposta = apostaBanco.getUser();

		if (!user.equals(usuarioAposta)) {
			throw new CustomParameterizedException("Não é possivel alterar a aposta de outro usuário");
		}

		apostaBanco.setPlacar(aposta.getPlacar());
		apostaBanco.setDataAposta(ZonedDateTime.now());

		new ValidadorDataAposta(apostaBanco).validar();

		aposta = apostaRepository.save(apostaBanco);

		return aposta;

	}
	
	public List<Aposta> obterApostasFinalizadasPorUsuario(User user, Long idRodada) {
		
		Rodada rodada = rodadaRepository.findOne(idRodada);
		
		return apostaRepository.findByDateAndUser(ZonedDateTime.now(), user, rodada);		
	}

}
