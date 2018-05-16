package br.com.bolao.web.rest;

import br.com.bolao.BolaoApp;

import br.com.bolao.domain.Campeonato;
import br.com.bolao.repository.CampeonatoRepository;
import br.com.bolao.service.dto.CampeonatoDTO;
import br.com.bolao.service.mapper.CampeonatoMapper;
import br.com.bolao.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
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
import java.util.List;

import static br.com.bolao.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CampeonatoResource REST controller.
 *
 * @see CampeonatoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BolaoApp.class)
public class CampeonatoResourceIntTest {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    @Autowired
    private CampeonatoRepository campeonatoRepository;

    @Autowired
    private CampeonatoMapper campeonatoMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCampeonatoMockMvc;

    private Campeonato campeonato;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CampeonatoResource campeonatoResource = new CampeonatoResource(campeonatoRepository, campeonatoMapper);
        this.restCampeonatoMockMvc = MockMvcBuilders.standaloneSetup(campeonatoResource)
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
    public static Campeonato createEntity(EntityManager em) {
        Campeonato campeonato = new Campeonato()
            .descricao(DEFAULT_DESCRICAO);
        return campeonato;
    }

    @Before
    public void initTest() {
        campeonato = createEntity(em);
    }

    @Test
    @Transactional
    public void createCampeonato() throws Exception {
        int databaseSizeBeforeCreate = campeonatoRepository.findAll().size();

        // Create the Campeonato
        CampeonatoDTO campeonatoDTO = campeonatoMapper.toDto(campeonato);
        restCampeonatoMockMvc.perform(post("/api/campeonatoes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(campeonatoDTO)))
            .andExpect(status().isCreated());

        // Validate the Campeonato in the database
        List<Campeonato> campeonatoList = campeonatoRepository.findAll();
        assertThat(campeonatoList).hasSize(databaseSizeBeforeCreate + 1);
        Campeonato testCampeonato = campeonatoList.get(campeonatoList.size() - 1);
        assertThat(testCampeonato.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    public void createCampeonatoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = campeonatoRepository.findAll().size();

        // Create the Campeonato with an existing ID
        campeonato.setId(1L);
        CampeonatoDTO campeonatoDTO = campeonatoMapper.toDto(campeonato);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCampeonatoMockMvc.perform(post("/api/campeonatoes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(campeonatoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Campeonato in the database
        List<Campeonato> campeonatoList = campeonatoRepository.findAll();
        assertThat(campeonatoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCampeonatoes() throws Exception {
        // Initialize the database
        campeonatoRepository.saveAndFlush(campeonato);

        // Get all the campeonatoList
        restCampeonatoMockMvc.perform(get("/api/campeonatoes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(campeonato.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));
    }

    @Test
    @Transactional
    public void getCampeonato() throws Exception {
        // Initialize the database
        campeonatoRepository.saveAndFlush(campeonato);

        // Get the campeonato
        restCampeonatoMockMvc.perform(get("/api/campeonatoes/{id}", campeonato.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(campeonato.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCampeonato() throws Exception {
        // Get the campeonato
        restCampeonatoMockMvc.perform(get("/api/campeonatoes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCampeonato() throws Exception {
        // Initialize the database
        campeonatoRepository.saveAndFlush(campeonato);
        int databaseSizeBeforeUpdate = campeonatoRepository.findAll().size();

        // Update the campeonato
        Campeonato updatedCampeonato = campeonatoRepository.findOne(campeonato.getId());
        // Disconnect from session so that the updates on updatedCampeonato are not directly saved in db
        em.detach(updatedCampeonato);
        updatedCampeonato
            .descricao(UPDATED_DESCRICAO);
        CampeonatoDTO campeonatoDTO = campeonatoMapper.toDto(updatedCampeonato);

        restCampeonatoMockMvc.perform(put("/api/campeonatoes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(campeonatoDTO)))
            .andExpect(status().isOk());

        // Validate the Campeonato in the database
        List<Campeonato> campeonatoList = campeonatoRepository.findAll();
        assertThat(campeonatoList).hasSize(databaseSizeBeforeUpdate);
        Campeonato testCampeonato = campeonatoList.get(campeonatoList.size() - 1);
        assertThat(testCampeonato.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void updateNonExistingCampeonato() throws Exception {
        int databaseSizeBeforeUpdate = campeonatoRepository.findAll().size();

        // Create the Campeonato
        CampeonatoDTO campeonatoDTO = campeonatoMapper.toDto(campeonato);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCampeonatoMockMvc.perform(put("/api/campeonatoes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(campeonatoDTO)))
            .andExpect(status().isCreated());

        // Validate the Campeonato in the database
        List<Campeonato> campeonatoList = campeonatoRepository.findAll();
        assertThat(campeonatoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCampeonato() throws Exception {
        // Initialize the database
        campeonatoRepository.saveAndFlush(campeonato);
        int databaseSizeBeforeDelete = campeonatoRepository.findAll().size();

        // Get the campeonato
        restCampeonatoMockMvc.perform(delete("/api/campeonatoes/{id}", campeonato.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Campeonato> campeonatoList = campeonatoRepository.findAll();
        assertThat(campeonatoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Campeonato.class);
        Campeonato campeonato1 = new Campeonato();
        campeonato1.setId(1L);
        Campeonato campeonato2 = new Campeonato();
        campeonato2.setId(campeonato1.getId());
        assertThat(campeonato1).isEqualTo(campeonato2);
        campeonato2.setId(2L);
        assertThat(campeonato1).isNotEqualTo(campeonato2);
        campeonato1.setId(null);
        assertThat(campeonato1).isNotEqualTo(campeonato2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CampeonatoDTO.class);
        CampeonatoDTO campeonatoDTO1 = new CampeonatoDTO();
        campeonatoDTO1.setId(1L);
        CampeonatoDTO campeonatoDTO2 = new CampeonatoDTO();
        assertThat(campeonatoDTO1).isNotEqualTo(campeonatoDTO2);
        campeonatoDTO2.setId(campeonatoDTO1.getId());
        assertThat(campeonatoDTO1).isEqualTo(campeonatoDTO2);
        campeonatoDTO2.setId(2L);
        assertThat(campeonatoDTO1).isNotEqualTo(campeonatoDTO2);
        campeonatoDTO1.setId(null);
        assertThat(campeonatoDTO1).isNotEqualTo(campeonatoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(campeonatoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(campeonatoMapper.fromId(null)).isNull();
    }
}
