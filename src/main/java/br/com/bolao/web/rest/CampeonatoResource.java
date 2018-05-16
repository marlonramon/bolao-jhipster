package br.com.bolao.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.bolao.domain.Campeonato;

import br.com.bolao.repository.CampeonatoRepository;
import br.com.bolao.web.rest.errors.BadRequestAlertException;
import br.com.bolao.web.rest.util.HeaderUtil;
import br.com.bolao.web.rest.util.PaginationUtil;
import br.com.bolao.service.dto.CampeonatoDTO;
import br.com.bolao.service.mapper.CampeonatoMapper;
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
 * REST controller for managing Campeonato.
 */
@RestController
@RequestMapping("/api")
public class CampeonatoResource {

    private final Logger log = LoggerFactory.getLogger(CampeonatoResource.class);

    private static final String ENTITY_NAME = "campeonato";

    private final CampeonatoRepository campeonatoRepository;

    private final CampeonatoMapper campeonatoMapper;

    public CampeonatoResource(CampeonatoRepository campeonatoRepository, CampeonatoMapper campeonatoMapper) {
        this.campeonatoRepository = campeonatoRepository;
        this.campeonatoMapper = campeonatoMapper;
    }

    /**
     * POST  /campeonatoes : Create a new campeonato.
     *
     * @param campeonatoDTO the campeonatoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new campeonatoDTO, or with status 400 (Bad Request) if the campeonato has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/campeonatoes")
    @Timed
    public ResponseEntity<CampeonatoDTO> createCampeonato(@RequestBody CampeonatoDTO campeonatoDTO) throws URISyntaxException {
        log.debug("REST request to save Campeonato : {}", campeonatoDTO);
        if (campeonatoDTO.getId() != null) {
            throw new BadRequestAlertException("A new campeonato cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Campeonato campeonato = campeonatoMapper.toEntity(campeonatoDTO);
        campeonato = campeonatoRepository.save(campeonato);
        CampeonatoDTO result = campeonatoMapper.toDto(campeonato);
        return ResponseEntity.created(new URI("/api/campeonatoes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /campeonatoes : Updates an existing campeonato.
     *
     * @param campeonatoDTO the campeonatoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated campeonatoDTO,
     * or with status 400 (Bad Request) if the campeonatoDTO is not valid,
     * or with status 500 (Internal Server Error) if the campeonatoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/campeonatoes")
    @Timed
    public ResponseEntity<CampeonatoDTO> updateCampeonato(@RequestBody CampeonatoDTO campeonatoDTO) throws URISyntaxException {
        log.debug("REST request to update Campeonato : {}", campeonatoDTO);
        if (campeonatoDTO.getId() == null) {
            return createCampeonato(campeonatoDTO);
        }
        Campeonato campeonato = campeonatoMapper.toEntity(campeonatoDTO);
        campeonato = campeonatoRepository.save(campeonato);
        CampeonatoDTO result = campeonatoMapper.toDto(campeonato);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, campeonatoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /campeonatoes : get all the campeonatoes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of campeonatoes in body
     */
    @GetMapping("/campeonatoes")
    @Timed
    public ResponseEntity<List<CampeonatoDTO>> getAllCampeonatoes(Pageable pageable) {
        log.debug("REST request to get a page of Campeonatoes");
        Page<Campeonato> page = campeonatoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/campeonatoes");
        return new ResponseEntity<>(campeonatoMapper.toDto(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /campeonatoes/:id : get the "id" campeonato.
     *
     * @param id the id of the campeonatoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the campeonatoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/campeonatoes/{id}")
    @Timed
    public ResponseEntity<CampeonatoDTO> getCampeonato(@PathVariable Long id) {
        log.debug("REST request to get Campeonato : {}", id);
        Campeonato campeonato = campeonatoRepository.findOne(id);
        CampeonatoDTO campeonatoDTO = campeonatoMapper.toDto(campeonato);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(campeonatoDTO));
    }

    /**
     * DELETE  /campeonatoes/:id : delete the "id" campeonato.
     *
     * @param id the id of the campeonatoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/campeonatoes/{id}")
    @Timed
    public ResponseEntity<Void> deleteCampeonato(@PathVariable Long id) {
        log.debug("REST request to delete Campeonato : {}", id);
        campeonatoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
