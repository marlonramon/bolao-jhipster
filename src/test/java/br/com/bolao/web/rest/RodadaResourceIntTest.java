package br.com.bolao.web.rest;

import br.com.bolao.BolaoApp;

import br.com.bolao.domain.Rodada;
import br.com.bolao.repository.RodadaRepository;
import br.com.bolao.service.RodadaService;
import br.com.bolao.service.dto.RodadaDTO;
import br.com.bolao.service.mapper.RodadaMapper;
import br.com.bolao.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static br.com.bolao.web.rest.TestUtil.sameInstant;
import static br.com.bolao.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the RodadaResource REST controller.
 *
 * @see RodadaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BolaoApp.class)
public class RodadaResourceIntTest {

    private static final Long DEFAULT_NUMERO = 1L;
    private static final Long UPDATED_NUMERO = 2L;

    private static final ZonedDateTime DEFAULT_INICIO_RODADA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_INICIO_RODADA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private RodadaRepository rodadaRepository;

    @Autowired
    private RodadaMapper rodadaMapper;
    
    @Autowired
    private RodadaService rodadaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRodadaMockMvc;

    private Rodada rodada;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RodadaResource rodadaResource = new RodadaResource(rodadaRepository, rodadaMapper, rodadaService);
        this.restRodadaMockMvc = MockMvcBuilders.standaloneSetup(rodadaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rodada createEntity(EntityManager em) {
        Rodada rodada = new Rodada()
            .numero(DEFAULT_NUMERO)
            .inicioRodada(DEFAULT_INICIO_RODADA);
        return rodada;
    }

    @Before
    public void initTest() {
        rodada = createEntity(em);
    }

    @Test
    @Transactional
    @Ignore
    public void createRodada() throws Exception {
        int databaseSizeBeforeCreate = rodadaRepository.findAll().size();

        // Create the Rodada
        RodadaDTO rodadaDTO = rodadaMapper.toDto(rodada);
        restRodadaMockMvc.perform(post("/api/rodadas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rodadaDTO)))
            .andExpect(status().isCreated());

        // Validate the Rodada in the database
        List<Rodada> rodadaList = rodadaRepository.findAll();
        assertThat(rodadaList).hasSize(databaseSizeBeforeCreate + 1);
        Rodada testRodada = rodadaList.get(rodadaList.size() - 1);
        assertThat(testRodada.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testRodada.getInicioRodada()).isEqualTo(DEFAULT_INICIO_RODADA);
    }

    @Test
    @Transactional
    @Ignore
    public void createRodadaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rodadaRepository.findAll().size();

        // Create the Rodada with an existing ID
        rodada.setId(1L);
        RodadaDTO rodadaDTO = rodadaMapper.toDto(rodada);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRodadaMockMvc.perform(post("/api/rodadas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rodadaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Rodada in the database
        List<Rodada> rodadaList = rodadaRepository.findAll();
        assertThat(rodadaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkInicioRodadaIsRequired() throws Exception {
        int databaseSizeBeforeTest = rodadaRepository.findAll().size();
        // set the field null
        rodada.setInicioRodada(null);

        // Create the Rodada, which fails.
        RodadaDTO rodadaDTO = rodadaMapper.toDto(rodada);

        restRodadaMockMvc.perform(post("/api/rodadas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rodadaDTO)))
            .andExpect(status().isBadRequest());

        List<Rodada> rodadaList = rodadaRepository.findAll();
        assertThat(rodadaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    @Ignore
    public void getAllRodadas() throws Exception {
        // Initialize the database
        rodadaRepository.saveAndFlush(rodada);

        // Get all the rodadaList
        restRodadaMockMvc.perform(get("/api/rodadas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rodada.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO.intValue())))
            .andExpect(jsonPath("$.[*].inicioRodada").value(hasItem(sameInstant(DEFAULT_INICIO_RODADA))));
    }

    @Test
    @Transactional
    @Ignore
    public void getRodada() throws Exception {
        // Initialize the database
        rodadaRepository.saveAndFlush(rodada);

        // Get the rodada
        restRodadaMockMvc.perform(get("/api/rodadas/{id}", rodada.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(rodada.getId().intValue()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO.intValue()))
            .andExpect(jsonPath("$.inicioRodada").value(sameInstant(DEFAULT_INICIO_RODADA)));
    }

    @Test
    @Transactional
    public void getNonExistingRodada() throws Exception {
        // Get the rodada
        restRodadaMockMvc.perform(get("/api/rodadas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    @Ignore
    public void updateRodada() throws Exception {
        // Initialize the database
        rodadaRepository.saveAndFlush(rodada);
        int databaseSizeBeforeUpdate = rodadaRepository.findAll().size();

        // Update the rodada
        Rodada updatedRodada = rodadaRepository.findOne(rodada.getId());
        // Disconnect from session so that the updates on updatedRodada are not directly saved in db
        em.detach(updatedRodada);
        updatedRodada
            .numero(UPDATED_NUMERO)
            .inicioRodada(UPDATED_INICIO_RODADA);
        RodadaDTO rodadaDTO = rodadaMapper.toDto(updatedRodada);

        restRodadaMockMvc.perform(put("/api/rodadas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rodadaDTO)))
            .andExpect(status().isOk());

        // Validate the Rodada in the database
        List<Rodada> rodadaList = rodadaRepository.findAll();
        assertThat(rodadaList).hasSize(databaseSizeBeforeUpdate);
        Rodada testRodada = rodadaList.get(rodadaList.size() - 1);
        assertThat(testRodada.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testRodada.getInicioRodada()).isEqualTo(UPDATED_INICIO_RODADA);
    }

    @Test
    @Transactional
    @Ignore
    public void updateNonExistingRodada() throws Exception {
        int databaseSizeBeforeUpdate = rodadaRepository.findAll().size();

        // Create the Rodada
        RodadaDTO rodadaDTO = rodadaMapper.toDto(rodada);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRodadaMockMvc.perform(put("/api/rodadas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rodadaDTO)))
            .andExpect(status().isCreated());

        // Validate the Rodada in the database
        List<Rodada> rodadaList = rodadaRepository.findAll();
        assertThat(rodadaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    @Ignore
    public void deleteRodada() throws Exception {
        // Initialize the database
        rodadaRepository.saveAndFlush(rodada);
        int databaseSizeBeforeDelete = rodadaRepository.findAll().size();

        // Get the rodada
        restRodadaMockMvc.perform(delete("/api/rodadas/{id}", rodada.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Rodada> rodadaList = rodadaRepository.findAll();
        assertThat(rodadaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Rodada.class);
        Rodada rodada1 = new Rodada();
        rodada1.setId(1L);
        Rodada rodada2 = new Rodada();
        rodada2.setId(rodada1.getId());
        assertThat(rodada1).isEqualTo(rodada2);
        rodada2.setId(2L);
        assertThat(rodada1).isNotEqualTo(rodada2);
        rodada1.setId(null);
        assertThat(rodada1).isNotEqualTo(rodada2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RodadaDTO.class);
        RodadaDTO rodadaDTO1 = new RodadaDTO();
        rodadaDTO1.setId(1L);
        RodadaDTO rodadaDTO2 = new RodadaDTO();
        assertThat(rodadaDTO1).isNotEqualTo(rodadaDTO2);
        rodadaDTO2.setId(rodadaDTO1.getId());
        assertThat(rodadaDTO1).isEqualTo(rodadaDTO2);
        rodadaDTO2.setId(2L);
        assertThat(rodadaDTO1).isNotEqualTo(rodadaDTO2);
        rodadaDTO1.setId(null);
        assertThat(rodadaDTO1).isNotEqualTo(rodadaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(rodadaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(rodadaMapper.fromId(null)).isNull();
    }
}
