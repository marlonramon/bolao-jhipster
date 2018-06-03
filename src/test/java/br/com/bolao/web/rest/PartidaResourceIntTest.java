package br.com.bolao.web.rest;

import br.com.bolao.BolaoApp;

import br.com.bolao.domain.Partida;
import br.com.bolao.repository.PartidaRepository;
import br.com.bolao.service.dto.PartidaDTO;
import br.com.bolao.service.mapper.PartidaMapper;
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
 * Test class for the PartidaResource REST controller.
 *
 * @see PartidaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BolaoApp.class)
public class PartidaResourceIntTest {

    private static final ZonedDateTime DEFAULT_DATA_PARTIDA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATA_PARTIDA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private PartidaRepository partidaRepository;

    @Autowired
    private PartidaMapper partidaMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPartidaMockMvc;

    private Partida partida;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PartidaResource partidaResource = new PartidaResource(partidaRepository, partidaMapper);
        this.restPartidaMockMvc = MockMvcBuilders.standaloneSetup(partidaResource)
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
    public static Partida createEntity(EntityManager em) {
        Partida partida = new Partida()
            .dataPartida(DEFAULT_DATA_PARTIDA);
        return partida;
    }

    @Before
    public void initTest() {
        partida = createEntity(em);
    }

    @Test
    @Transactional
    public void createPartida() throws Exception {
        int databaseSizeBeforeCreate = partidaRepository.findAll().size();

        // Create the Partida
        PartidaDTO partidaDTO = partidaMapper.toDto(partida);
        restPartidaMockMvc.perform(post("/api/partidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partidaDTO)))
            .andExpect(status().isCreated());

        // Validate the Partida in the database
        List<Partida> partidaList = partidaRepository.findAll();
        assertThat(partidaList).hasSize(databaseSizeBeforeCreate + 1);
        Partida testPartida = partidaList.get(partidaList.size() - 1);
        assertThat(testPartida.getDataPartida()).isEqualTo(DEFAULT_DATA_PARTIDA);
    }

    @Test
    @Transactional
    public void createPartidaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = partidaRepository.findAll().size();

        // Create the Partida with an existing ID
        partida.setId(1L);
        PartidaDTO partidaDTO = partidaMapper.toDto(partida);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPartidaMockMvc.perform(post("/api/partidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partidaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Partida in the database
        List<Partida> partidaList = partidaRepository.findAll();
        assertThat(partidaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDataPartidaIsRequired() throws Exception {
        int databaseSizeBeforeTest = partidaRepository.findAll().size();
        // set the field null
        partida.setDataPartida(null);

        // Create the Partida, which fails.
        PartidaDTO partidaDTO = partidaMapper.toDto(partida);

        restPartidaMockMvc.perform(post("/api/partidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partidaDTO)))
            .andExpect(status().isBadRequest());

        List<Partida> partidaList = partidaRepository.findAll();
        assertThat(partidaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPartidas() throws Exception {
        // Initialize the database
        partidaRepository.saveAndFlush(partida);

        // Get all the partidaList
        restPartidaMockMvc.perform(get("/api/partidas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partida.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataPartida").value(hasItem(sameInstant(DEFAULT_DATA_PARTIDA))));
    }

    @Test
    @Transactional
    public void getPartida() throws Exception {
        // Initialize the database
        partidaRepository.saveAndFlush(partida);

        // Get the partida
        restPartidaMockMvc.perform(get("/api/partidas/{id}", partida.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(partida.getId().intValue()))
            .andExpect(jsonPath("$.dataPartida").value(sameInstant(DEFAULT_DATA_PARTIDA)));
    }

    @Test
    @Transactional
    public void getNonExistingPartida() throws Exception {
        // Get the partida
        restPartidaMockMvc.perform(get("/api/partidas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    @Ignore
    public void updatePartida() throws Exception {
        // Initialize the database
        partidaRepository.saveAndFlush(partida);
        int databaseSizeBeforeUpdate = partidaRepository.findAll().size();

        // Update the partida
        Partida updatedPartida = partidaRepository.findOne(partida.getId());
        // Disconnect from session so that the updates on updatedPartida are not directly saved in db
        em.detach(updatedPartida);
        updatedPartida
            .dataPartida(UPDATED_DATA_PARTIDA);
        PartidaDTO partidaDTO = partidaMapper.toDto(updatedPartida);

        restPartidaMockMvc.perform(put("/api/partidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partidaDTO)))
            .andExpect(status().isOk());

        // Validate the Partida in the database
        List<Partida> partidaList = partidaRepository.findAll();
        assertThat(partidaList).hasSize(databaseSizeBeforeUpdate);
        Partida testPartida = partidaList.get(partidaList.size() - 1);
        assertThat(testPartida.getDataPartida()).isEqualTo(UPDATED_DATA_PARTIDA);
    }

    @Test
    @Transactional
    public void updateNonExistingPartida() throws Exception {
        int databaseSizeBeforeUpdate = partidaRepository.findAll().size();

        // Create the Partida
        PartidaDTO partidaDTO = partidaMapper.toDto(partida);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPartidaMockMvc.perform(put("/api/partidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partidaDTO)))
            .andExpect(status().isCreated());

        // Validate the Partida in the database
        List<Partida> partidaList = partidaRepository.findAll();
        assertThat(partidaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePartida() throws Exception {
        // Initialize the database
        partidaRepository.saveAndFlush(partida);
        int databaseSizeBeforeDelete = partidaRepository.findAll().size();

        // Get the partida
        restPartidaMockMvc.perform(delete("/api/partidas/{id}", partida.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Partida> partidaList = partidaRepository.findAll();
        assertThat(partidaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Partida.class);
        Partida partida1 = new Partida();
        partida1.setId(1L);
        Partida partida2 = new Partida();
        partida2.setId(partida1.getId());
        assertThat(partida1).isEqualTo(partida2);
        partida2.setId(2L);
        assertThat(partida1).isNotEqualTo(partida2);
        partida1.setId(null);
        assertThat(partida1).isNotEqualTo(partida2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PartidaDTO.class);
        PartidaDTO partidaDTO1 = new PartidaDTO();
        partidaDTO1.setId(1L);
        PartidaDTO partidaDTO2 = new PartidaDTO();
        assertThat(partidaDTO1).isNotEqualTo(partidaDTO2);
        partidaDTO2.setId(partidaDTO1.getId());
        assertThat(partidaDTO1).isEqualTo(partidaDTO2);
        partidaDTO2.setId(2L);
        assertThat(partidaDTO1).isNotEqualTo(partidaDTO2);
        partidaDTO1.setId(null);
        assertThat(partidaDTO1).isNotEqualTo(partidaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(partidaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(partidaMapper.fromId(null)).isNull();
    }
}
