package br.com.bolao.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.bolao.domain.Aposta;

import br.com.bolao.repository.ApostaRepository;
import br.com.bolao.web.rest.errors.BadRequestAlertException;
import br.com.bolao.web.rest.util.HeaderUtil;
import br.com.bolao.web.rest.util.PaginationUtil;
import br.com.bolao.service.dto.ApostaDTO;
import br.com.bolao.service.mapper.ApostaMapper;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Aposta.
 */
@RestController
@RequestMapping("/api")
public class ApostaResource {

    private final Logger log = LoggerFactory.getLogger(ApostaResource.class);

    private static final String ENTITY_NAME = "aposta";

    private final ApostaRepository apostaRepository;

    private final ApostaMapper apostaMapper;

    public ApostaResource(ApostaRepository apostaRepository, ApostaMapper apostaMapper) {
        this.apostaRepository = apostaRepository;
        this.apostaMapper = apostaMapper;
    }

    /**
     * POST  /apostas : Create a new aposta.
     *
     * @param apostaDTO the apostaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new apostaDTO, or with status 400 (Bad Request) if the aposta has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
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

    /**
     * PUT  /apostas : Updates an existing aposta.
     *
     * @param apostaDTO the apostaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated apostaDTO,
     * or with status 400 (Bad Request) if the apostaDTO is not valid,
     * or with status 500 (Internal Server Error) if the apostaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
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

    /**
     * GET  /apostas : get all the apostas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of apostas in body
     */
    @GetMapping("/apostas")
    @Timed
    public ResponseEntity<List<ApostaDTO>> getAllApostas(Pageable pageable) {
        log.debug("REST request to get a page of Apostas");
        Page<Aposta> page = apostaRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/apostas");
        return new ResponseEntity<>(apostaMapper.toDto(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /apostas/:id : get the "id" aposta.
     *
     * @param id the id of the apostaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the apostaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/apostas/{id}")
    @Timed
    public ResponseEntity<ApostaDTO> getAposta(@PathVariable Long id) {
        log.debug("REST request to get Aposta : {}", id);
        Aposta aposta = apostaRepository.findOne(id);
        ApostaDTO apostaDTO = apostaMapper.toDto(aposta);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(apostaDTO));
    }

    /**
     * DELETE  /apostas/:id : delete the "id" aposta.
     *
     * @param id the id of the apostaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/apostas/{id}")
    @Timed
    public ResponseEntity<Void> deleteAposta(@PathVariable Long id) {
        log.debug("REST request to delete Aposta : {}", id);
        apostaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
