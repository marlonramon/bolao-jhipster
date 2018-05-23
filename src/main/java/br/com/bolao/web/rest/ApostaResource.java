package br.com.bolao.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

import br.com.bolao.domain.Aposta;
import br.com.bolao.domain.User;
import br.com.bolao.repository.ApostaRepository;
import br.com.bolao.security.AuthoritiesConstants;
import br.com.bolao.service.ApostaService;
import br.com.bolao.service.UserService;
import br.com.bolao.service.dto.ApostaDTO;
import br.com.bolao.service.mapper.ApostaMapper;
import br.com.bolao.web.rest.errors.BadRequestAlertException;
import br.com.bolao.web.rest.util.HeaderUtil;

@RestController
@RequestMapping("/api")
public class ApostaResource {

    private final Logger log = LoggerFactory.getLogger(ApostaResource.class);

    private static final String ENTITY_NAME = "aposta";

    private final ApostaRepository apostaRepository;

    private final ApostaMapper apostaMapper;

	private ApostaService apostaService;

	private UserService userService;
	

    public ApostaResource(ApostaRepository apostaRepository, ApostaMapper apostaMapper, ApostaService apostaService, UserService userService) {
        this.apostaRepository = apostaRepository;
        this.apostaMapper = apostaMapper;
        this.apostaService = apostaService;
        this.userService = userService;
        
    }

    @PostMapping("/apostas")
    @Timed
    public ResponseEntity<ApostaDTO> createAposta(@RequestBody ApostaDTO apostaDTO) throws URISyntaxException {
        log.debug("REST request to save Aposta : {}", apostaDTO);
        if (apostaDTO.getId() != null) {
            throw new BadRequestAlertException("A new aposta cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Aposta aposta = apostaMapper.toEntity(apostaDTO);
        aposta = apostaRepository.save(aposta);
        ApostaDTO result = apostaMapper.toDto(aposta);
        return ResponseEntity.created(new URI("/api/apostas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/apostas")
    @Timed
    public ResponseEntity<ApostaDTO> updateAposta(@RequestBody ApostaDTO apostaDTO) throws URISyntaxException {
        log.debug("REST request to update Aposta : {}", apostaDTO);
        if (apostaDTO.getId() == null) {
            return createAposta(apostaDTO);
        }
        Aposta aposta = apostaMapper.toEntity(apostaDTO);
        aposta = apostaRepository.save(aposta);
        ApostaDTO result = apostaMapper.toDto(aposta);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, apostaDTO.getId().toString()))
            .body(result);
    }
    
    @GetMapping("/user/me/rodada/{idRodada}/apostas")
    @Timed
    @Secured(AuthoritiesConstants.USER)
    public ResponseEntity<List<ApostaDTO>> getApostasFromRodada(@PathVariable Long idRodada) {
        
    	log.debug("REST request to get a page of Rodadas");
    	
        User userLogado = userService.getUserWithAuthorities().get();
        
        List<Aposta> obterApostasUsuarioParaRodada = apostaService.obterApostasUsuarioParaRodada(userLogado, idRodada);
        
		return new ResponseEntity<>(apostaMapper.toDto(obterApostasUsuarioParaRodada), HttpStatus.OK);
    }

    
    
}
