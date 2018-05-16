package br.com.bolao.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.bolao.domain.Bolao;

import br.com.bolao.repository.BolaoRepository;
import br.com.bolao.web.rest.errors.BadRequestAlertException;
import br.com.bolao.web.rest.util.HeaderUtil;
import br.com.bolao.web.rest.util.PaginationUtil;
import br.com.bolao.service.dto.BolaoDTO;
import br.com.bolao.service.mapper.BolaoMapper;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Bolao.
 */
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

    /**
     * POST  /bolaos : Create a new bolao.
     *
     * @param bolaoDTO the bolaoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bolaoDTO, or with status 400 (Bad Request) if the bolao has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/bolaos")
    @Timed
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

    /**
     * PUT  /bolaos : Updates an existing bolao.
     *
     * @param bolaoDTO the bolaoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bolaoDTO,
     * or with status 400 (Bad Request) if the bolaoDTO is not valid,
     * or with status 500 (Internal Server Error) if the bolaoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/bolaos")
    @Timed
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

    /**
     * GET  /bolaos : get all the bolaos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of bolaos in body
     */
    @GetMapping("/bolaos")
    @Timed
    public ResponseEntity<List<BolaoDTO>> getAllBolaos(Pageable pageable) {
        log.debug("REST request to get a page of Bolaos");
        Page<Bolao> page = bolaoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/bolaos");
        return new ResponseEntity<>(bolaoMapper.toDtoLazy(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /bolaos/:id : get the "id" bolao.
     *
     * @param id the id of the bolaoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bolaoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/bolaos/{id}")
    @Timed
    public ResponseEntity<BolaoDTO> getBolao(@PathVariable Long id) {
        log.debug("REST request to get Bolao : {}", id);
        Bolao bolao = bolaoRepository.findOneWithEagerRelationships(id);
        BolaoDTO bolaoDTO = bolaoMapper.toDto(bolao);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(bolaoDTO));
    }

    /**
     * DELETE  /bolaos/:id : delete the "id" bolao.
     *
     * @param id the id of the bolaoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/bolaos/{id}")
    @Timed
    public ResponseEntity<Void> deleteBolao(@PathVariable Long id) {
        log.debug("REST request to delete Bolao : {}", id);
        bolaoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
