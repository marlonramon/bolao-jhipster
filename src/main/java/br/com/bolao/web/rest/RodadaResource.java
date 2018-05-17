package br.com.bolao.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.bolao.domain.Rodada;

import br.com.bolao.repository.RodadaRepository;
import br.com.bolao.security.AuthoritiesConstants;
import br.com.bolao.web.rest.errors.BadRequestAlertException;
import br.com.bolao.web.rest.util.HeaderUtil;
import br.com.bolao.web.rest.util.PaginationUtil;
import br.com.bolao.service.dto.RodadaDTO;
import br.com.bolao.service.mapper.RodadaMapper;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class RodadaResource {

    private final Logger log = LoggerFactory.getLogger(RodadaResource.class);

    private static final String ENTITY_NAME = "rodada";

    private final RodadaRepository rodadaRepository;

    private final RodadaMapper rodadaMapper;

    public RodadaResource(RodadaRepository rodadaRepository, RodadaMapper rodadaMapper) {
        this.rodadaRepository = rodadaRepository;
        this.rodadaMapper = rodadaMapper;
    }

    @PostMapping("/rodadas")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<RodadaDTO> createRodada(@Valid @RequestBody RodadaDTO rodadaDTO) throws URISyntaxException {
        log.debug("REST request to save Rodada : {}", rodadaDTO);
        if (rodadaDTO.getId() != null) {
            throw new BadRequestAlertException("A new rodada cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Rodada rodada = rodadaMapper.toEntity(rodadaDTO);
        rodada = rodadaRepository.save(rodada);
        RodadaDTO result = rodadaMapper.toDto(rodada);
        return ResponseEntity.created(new URI("/api/rodadas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/rodadas")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<RodadaDTO> updateRodada(@Valid @RequestBody RodadaDTO rodadaDTO) throws URISyntaxException {
        log.debug("REST request to update Rodada : {}", rodadaDTO);
        if (rodadaDTO.getId() == null) {
            return createRodada(rodadaDTO);
        }
        Rodada rodada = rodadaMapper.toEntity(rodadaDTO);
        rodada = rodadaRepository.save(rodada);
        RodadaDTO result = rodadaMapper.toDto(rodada);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rodadaDTO.getId().toString()))
            .body(result);
    }

    @GetMapping("/rodadas")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<List<RodadaDTO>> getAllRodadas(Pageable pageable) {
        log.debug("REST request to get a page of Rodadas");
        Page<Rodada> page = rodadaRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/rodadas");
        return new ResponseEntity<>(rodadaMapper.toDto(page.getContent()), headers, HttpStatus.OK);
    }

    @GetMapping("/rodadas/{id}")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<RodadaDTO> getRodada(@PathVariable Long id) {
        log.debug("REST request to get Rodada : {}", id);
        Rodada rodada = rodadaRepository.findOne(id);
        RodadaDTO rodadaDTO = rodadaMapper.toDto(rodada);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(rodadaDTO));
    }

    @DeleteMapping("/rodadas/{id}")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<Void> deleteRodada(@PathVariable Long id) {
        log.debug("REST request to delete Rodada : {}", id);
        rodadaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
    
   
    
    
}
