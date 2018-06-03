package br.com.bolao.web.rest;

import static br.com.bolao.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import javax.persistence.EntityManager;

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

import br.com.bolao.BolaoApp;
import br.com.bolao.domain.Clube;
import br.com.bolao.repository.ClubeRepository;
import br.com.bolao.service.dto.ClubeDTO;
import br.com.bolao.service.mapper.ClubeMapper;
import br.com.bolao.web.rest.errors.ExceptionTranslator;

/**
 * Test class for the ClubeResource REST controller.
 *
 * @see ClubeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BolaoApp.class)
public class ClubeResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_BANDEIRA = "";
    private static final String UPDATED_BANDEIRA = "1";
  
    

    @Autowired
    private ClubeRepository clubeRepository;

    @Autowired
    private ClubeMapper clubeMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restClubeMockMvc;

    private Clube clube;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClubeResource clubeResource = new ClubeResource(clubeRepository, clubeMapper);
        this.restClubeMockMvc = MockMvcBuilders.standaloneSetup(clubeResource)
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
    public static Clube createEntity(EntityManager em) {
        Clube clube = new Clube()
            .nome(DEFAULT_NOME)
            .bandeira(DEFAULT_BANDEIRA);
            
        return clube;
    }

    @Before
    public void initTest() {
        clube = createEntity(em);
    }

    @Test
    @Transactional
    @Ignore
    public void createClube() throws Exception {
        int databaseSizeBeforeCreate = clubeRepository.findAll().size();

        // Create the Clube
        ClubeDTO clubeDTO = clubeMapper.toDto(clube);
        restClubeMockMvc.perform(post("/api/clubes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clubeDTO)))
            .andExpect(status().isCreated());

        // Validate the Clube in the database
        List<Clube> clubeList = clubeRepository.findAll();
        assertThat(clubeList).hasSize(databaseSizeBeforeCreate + 1);
        Clube testClube = clubeList.get(clubeList.size() - 1);
        assertThat(testClube.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testClube.getBandeira()).isEqualTo(DEFAULT_BANDEIRA);
        
    }

    @Test
    @Transactional
    @Ignore
    public void createClubeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clubeRepository.findAll().size();

        // Create the Clube with an existing ID
        clube.setId(1L);
        ClubeDTO clubeDTO = clubeMapper.toDto(clube);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClubeMockMvc.perform(post("/api/clubes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clubeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Clube in the database
        List<Clube> clubeList = clubeRepository.findAll();
        assertThat(clubeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    @Ignore
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = clubeRepository.findAll().size();
        // set the field null
        clube.setNome(null);

        // Create the Clube, which fails.
        ClubeDTO clubeDTO = clubeMapper.toDto(clube);

        restClubeMockMvc.perform(post("/api/clubes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clubeDTO)))
            .andExpect(status().isBadRequest());

        List<Clube> clubeList = clubeRepository.findAll();
        assertThat(clubeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    @Ignore
    public void checkBandeiraIsRequired() throws Exception {
        int databaseSizeBeforeTest = clubeRepository.findAll().size();
        // set the field null
        clube.setBandeira(null);

        // Create the Clube, which fails.
        ClubeDTO clubeDTO = clubeMapper.toDto(clube);

        restClubeMockMvc.perform(post("/api/clubes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clubeDTO)))
            .andExpect(status().isBadRequest());

        List<Clube> clubeList = clubeRepository.findAll();
        assertThat(clubeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllClubes() throws Exception {
        // Initialize the database
        clubeRepository.saveAndFlush(clube);

        // Get all the clubeList
        restClubeMockMvc.perform(get("/api/clubes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clube.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].bandeira").value(hasItem(DEFAULT_BANDEIRA)));
    }

    @Test
    @Transactional
    public void getClube() throws Exception {
        // Initialize the database
        clubeRepository.saveAndFlush(clube);

        // Get the clube
        restClubeMockMvc.perform(get("/api/clubes/{id}", clube.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(clube.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.bandeira").value(DEFAULT_BANDEIRA));
    }

    @Test
    @Transactional
    public void getNonExistingClube() throws Exception {
        // Get the clube
        restClubeMockMvc.perform(get("/api/clubes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    @Ignore
    public void updateClube() throws Exception {
        // Initialize the database
        clubeRepository.saveAndFlush(clube);
        int databaseSizeBeforeUpdate = clubeRepository.findAll().size();

        // Update the clube
        Clube updatedClube = clubeRepository.findOne(clube.getId());
        // Disconnect from session so that the updates on updatedClube are not directly saved in db
        em.detach(updatedClube);
        updatedClube
            .nome(UPDATED_NOME)
            .bandeira(UPDATED_BANDEIRA);
        ClubeDTO clubeDTO = clubeMapper.toDto(updatedClube);

        restClubeMockMvc.perform(put("/api/clubes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clubeDTO)))
            .andExpect(status().isOk());

        // Validate the Clube in the database
        List<Clube> clubeList = clubeRepository.findAll();
        assertThat(clubeList).hasSize(databaseSizeBeforeUpdate);
        Clube testClube = clubeList.get(clubeList.size() - 1);
        assertThat(testClube.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testClube.getBandeira()).isEqualTo(UPDATED_BANDEIRA);
        
    }

    @Test
    @Transactional
    @Ignore
    public void updateNonExistingClube() throws Exception {
        int databaseSizeBeforeUpdate = clubeRepository.findAll().size();

        // Create the Clube
        ClubeDTO clubeDTO = clubeMapper.toDto(clube);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restClubeMockMvc.perform(put("/api/clubes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clubeDTO)))
            .andExpect(status().isCreated());

        // Validate the Clube in the database
        List<Clube> clubeList = clubeRepository.findAll();
        assertThat(clubeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    @Ignore
    public void deleteClube() throws Exception {
        // Initialize the database
        clubeRepository.saveAndFlush(clube);
        int databaseSizeBeforeDelete = clubeRepository.findAll().size();

        // Get the clube
        restClubeMockMvc.perform(delete("/api/clubes/{id}", clube.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Clube> clubeList = clubeRepository.findAll();
        assertThat(clubeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Clube.class);
        Clube clube1 = new Clube();
        clube1.setId(1L);
        Clube clube2 = new Clube();
        clube2.setId(clube1.getId());
        assertThat(clube1).isEqualTo(clube2);
        clube2.setId(2L);
        assertThat(clube1).isNotEqualTo(clube2);
        clube1.setId(null);
        assertThat(clube1).isNotEqualTo(clube2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClubeDTO.class);
        ClubeDTO clubeDTO1 = new ClubeDTO();
        clubeDTO1.setId(1L);
        ClubeDTO clubeDTO2 = new ClubeDTO();
        assertThat(clubeDTO1).isNotEqualTo(clubeDTO2);
        clubeDTO2.setId(clubeDTO1.getId());
        assertThat(clubeDTO1).isEqualTo(clubeDTO2);
        clubeDTO2.setId(2L);
        assertThat(clubeDTO1).isNotEqualTo(clubeDTO2);
        clubeDTO1.setId(null);
        assertThat(clubeDTO1).isNotEqualTo(clubeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(clubeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(clubeMapper.fromId(null)).isNull();
    }
}
