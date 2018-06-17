package br.com.bolao.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

import br.com.bolao.domain.Bolao;
import br.com.bolao.repository.BolaoRepository;
import br.com.bolao.security.AuthoritiesConstants;
import br.com.bolao.security.SecurityUtils;
import br.com.bolao.service.dto.BolaoDTO;
import br.com.bolao.service.dto.RankingDTO;
import br.com.bolao.service.mapper.BolaoMapper;
import br.com.bolao.service.util.AtualizadorPosicaoRanking;
import br.com.bolao.service.util.CalculadoraPosicaoRanking;
import br.com.bolao.web.rest.errors.BadRequestAlertException;
import br.com.bolao.web.rest.util.HeaderUtil;
import br.com.bolao.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;


@RestController
@RequestMapping("/api")
public class BolaoResource {

    private final Logger log = LoggerFactory.getLogger(BolaoResource.class);

    private static final String ENTITY_NAME = "bolao";

    private final BolaoRepository bolaoRepository;

    private final BolaoMapper bolaoMapper;

    public BolaoResource(BolaoRepository bolaoRepository, BolaoMapper bolaoMapper) {
        this.bolaoRepository = bolaoRepository;
        this.bolaoMapper = bolaoMapper;
    }

    
    @PostMapping("/bolao")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<BolaoDTO> createBolao(@Valid @RequestBody BolaoDTO bolaoDTO) throws URISyntaxException {
        log.debug("REST request to save Bolao : {}", bolaoDTO);
        if (bolaoDTO.getId() != null) {
            throw new BadRequestAlertException("A new bolao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Bolao bolao = bolaoMapper.toEntity(bolaoDTO);
        bolao = bolaoRepository.save(bolao);
        BolaoDTO result = bolaoMapper.toDto(bolao);
        return ResponseEntity.created(new URI("/api/bolaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/bolao")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<BolaoDTO> updateBolao(@Valid @RequestBody BolaoDTO bolaoDTO) throws URISyntaxException {
        log.debug("REST request to update Bolao : {}", bolaoDTO);
        if (bolaoDTO.getId() == null) {
            return createBolao(bolaoDTO);
        }
        Bolao bolao = bolaoMapper.toEntity(bolaoDTO);
        bolao = bolaoRepository.save(bolao);
        BolaoDTO result = bolaoMapper.toDto(bolao);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bolaoDTO.getId().toString()))
            .body(result);
    }

    @GetMapping("/bolao")
    @Timed    
    public ResponseEntity<List<BolaoDTO>> getAllBolaos(Pageable pageable) {
        log.debug("REST request to get a page of Bolaos");
        Page<Bolao> page = bolaoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/bolaos");
        return new ResponseEntity<>(bolaoMapper.toDtoLazy(page.getContent()), headers, HttpStatus.OK);
    }
    
    
    @GetMapping("/user/me/bolao")
    @Timed
    @Secured(AuthoritiesConstants.USER)
    public ResponseEntity<List<BolaoDTO>> getAllBoloesFromLoggedUser() {
        log.debug("REST request to get a page of Bolaos from logged user");
        
        String currentUserLogin = SecurityUtils.getCurrentUserLogin().get();
        
        List<Bolao> boloes = bolaoRepository.findAllByUserLogin(currentUserLogin);        
        
        return new ResponseEntity<>(bolaoMapper.toDto(boloes), HttpStatus.OK);
    }

    @GetMapping("/bolao/{id}")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<BolaoDTO> getBolao(@PathVariable Long id) {
        log.debug("REST request to get Bolao : {}", id);
        Bolao bolao = bolaoRepository.findOneWithEagerRelationships(id);
        BolaoDTO bolaoDTO = bolaoMapper.toDto(bolao);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(bolaoDTO));
    }

    @DeleteMapping("/bolao/{id}")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<Void> deleteBolao(@PathVariable Long id) {
        log.debug("REST request to delete Bolao : {}", id);
        bolaoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
    
    
    @GetMapping("/bolao/{id}/ranking")
    @Timed    
    public ResponseEntity<List<RankingDTO>> getRankingBolao(@PathVariable Long id) {
        
    	log.debug("REST request to get Bolao : {}", id);
        
        List<RankingDTO> results = bolaoRepository.findByRankingFromBolao(id);
        
        Collections.sort(results, new CalculadoraPosicaoRanking());
        
        new AtualizadorPosicaoRanking(results).atualizarPosicoes();
        
        return new ResponseEntity<>(results, HttpStatus.OK);
    }
    
    
    
    
    
    
}
