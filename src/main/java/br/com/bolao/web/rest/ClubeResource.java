package br.com.bolao.web.rest;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

import br.com.bolao.domain.Clube;
import br.com.bolao.repository.ClubeRepository;
import br.com.bolao.service.dto.ClubeDTO;
import br.com.bolao.service.mapper.ClubeMapper;
import br.com.bolao.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;


@RestController
@RequestMapping("/api")
public class ClubeResource {

    private final Logger log = LoggerFactory.getLogger(ClubeResource.class);

    private final ClubeRepository clubeRepository;

    private final ClubeMapper clubeMapper;

    public ClubeResource(ClubeRepository clubeRepository, ClubeMapper clubeMapper) {
        this.clubeRepository = clubeRepository;
        this.clubeMapper = clubeMapper;
    }
    
    @GetMapping("/clubes")
    @Timed
    public ResponseEntity<List<ClubeDTO>> getAllClubes(Pageable pageable) {
        log.debug("REST request to get a page of Clubes");
        Page<Clube> page = clubeRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/clubes");
        return new ResponseEntity<>(clubeMapper.toDto(page.getContent()), headers, HttpStatus.OK);
    }

    @GetMapping("/clubes/{id}")
    @Timed
    public ResponseEntity<ClubeDTO> getClube(@PathVariable Long id) {
        log.debug("REST request to get Clube : {}", id);
        Clube clube = clubeRepository.findOne(id);
        ClubeDTO clubeDTO = clubeMapper.toDto(clube);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(clubeDTO));
    }

}
