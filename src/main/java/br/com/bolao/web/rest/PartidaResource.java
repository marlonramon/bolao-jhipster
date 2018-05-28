package br.com.bolao.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.bolao.domain.Partida;

import br.com.bolao.repository.PartidaRepository;
import br.com.bolao.security.AuthoritiesConstants;
import br.com.bolao.web.rest.errors.BadRequestAlertException;
import br.com.bolao.web.rest.util.HeaderUtil;
import br.com.bolao.web.rest.util.PaginationUtil;
import br.com.bolao.service.dto.PartidaDTO;
import br.com.bolao.service.mapper.PartidaMapper;
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

/**
 * REST controller for managing Partida.
 */
@RestController
@RequestMapping("/api")
public class PartidaResource {

    private final Logger log = LoggerFactory.getLogger(PartidaResource.class);

    private static final String ENTITY_NAME = "partida";

    private final PartidaRepository partidaRepository;

    private final PartidaMapper partidaMapper;

    public PartidaResource(PartidaRepository partidaRepository, PartidaMapper partidaMapper) {
        this.partidaRepository = partidaRepository;
        this.partidaMapper = partidaMapper;
    }

    @PostMapping("/partidas")
    @Secured(AuthoritiesConstants.ADMIN)
    @Timed
    public ResponseEntity<PartidaDTO> createPartida(@Valid @RequestBody PartidaDTO partidaDTO) throws URISyntaxException {
        log.debug("REST request to save Partida : {}", partidaDTO);
        if (partidaDTO.getId() != null) {
            throw new BadRequestAlertException("A new partida cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Partida partida = partidaMapper.toEntity(partidaDTO);
        partida = partidaRepository.save(partida);
        PartidaDTO result = partidaMapper.toDto(partida);
        return ResponseEntity.created(new URI("/api/partidas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/partidas")
    @Secured(AuthoritiesConstants.ADMIN)
    @Timed
    public ResponseEntity<PartidaDTO> updatePartida(@Valid @RequestBody PartidaDTO partidaDTO) throws URISyntaxException {
        log.debug("REST request to update Partida : {}", partidaDTO);
        if (partidaDTO.getId() == null) {
            return createPartida(partidaDTO);
        }
        Partida partida = partidaMapper.toEntity(partidaDTO);
        partida = partidaRepository.save(partida);
        PartidaDTO result = partidaMapper.toDto(partida);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, partidaDTO.getId().toString()))
            .body(result);
    }

    @GetMapping("/partidas")
    @Timed
    public ResponseEntity<List<PartidaDTO>> getAllPartidas(Pageable pageable) {
        log.debug("REST request to get a page of Partidas");
        Page<Partida> page = partidaRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/partidas");
        return new ResponseEntity<>(partidaMapper.toDto(page.getContent()), headers, HttpStatus.OK);
    }

    @GetMapping("/partidas/{id}")
    @Timed
    public ResponseEntity<PartidaDTO> getPartida(@PathVariable Long id) {
        log.debug("REST request to get Partida : {}", id);
        Partida partida = partidaRepository.findOne(id);
        PartidaDTO partidaDTO = partidaMapper.toDto(partida);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(partidaDTO));
    }

    @DeleteMapping("/partidas/{id}")
    @Secured(AuthoritiesConstants.ADMIN)
    @Timed
    public ResponseEntity<Void> deletePartida(@PathVariable Long id) {
        log.debug("REST request to delete Partida : {}", id);
        partidaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
