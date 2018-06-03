package br.com.bolao.web.rest;

import static br.com.bolao.web.rest.TestUtil.createFormattingConversionService;
import static br.com.bolao.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
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
import br.com.bolao.domain.Aposta;
import br.com.bolao.domain.Partida;
import br.com.bolao.domain.Placar;
import br.com.bolao.repository.ApostaRepository;
import br.com.bolao.repository.PartidaRepository;
import br.com.bolao.service.ApostaService;
import br.com.bolao.service.UserService;
import br.com.bolao.service.dto.ApostaDTO;
import br.com.bolao.service.mapper.ApostaMapper;
import br.com.bolao.web.rest.errors.ExceptionTranslator;

/**
 * Test class for the ApostaResource REST controller.
 *
 * @see ApostaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BolaoApp.class)
public class ApostaResourceIntTest {

	private static final ZonedDateTime DEFAULT_DATA_APOSTA = ZonedDateTime.of(LocalDateTime.of(2018, Month.JUNE,  21,   16,   30), 
																							   ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATA_APOSTA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    
    
    
    private static final ZonedDateTime DEFAULT_DATA_PARTIDA = ZonedDateTime.of(LocalDateTime.of(2018, Month.JUNE,  22,   16,   30), 
			   																	ZoneOffset.UTC);

    @Autowired
    private ApostaRepository apostaRepository;
    
    @Autowired
    private PartidaRepository partidaRepository;

    @Autowired
    private ApostaMapper apostaMapper;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private ApostaService apostaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restApostaMockMvc;

    private Aposta aposta;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ApostaResource apostaResource = new ApostaResource(apostaMapper, apostaService, userService);
        this.restApostaMockMvc = MockMvcBuilders.standaloneSetup(apostaResource)
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
    public static Aposta createEntity(EntityManager em) {
        Aposta aposta = new Aposta()
            .dataAposta(DEFAULT_DATA_APOSTA);
        
        return aposta;
    }

    @Before
    public void initTest() {
        aposta = createEntity(em);
    }

    @Test
    @Transactional
    @Ignore
    public void createAposta() throws Exception {
        int databaseSizeBeforeCreate = apostaRepository.findAll().size();

        Partida partida = new Partida();
        partida.setDataPartida(DEFAULT_DATA_PARTIDA);
        
        partida = partidaRepository.save(partida);
        
        Short placarMandante = 1;
        Short placarVisitante = 0;
        
        aposta.setPlacar(new Placar(placarMandante,placarVisitante));
        
        aposta.setPartida(partida);
        
        // Create the Aposta
        ApostaDTO apostaDTO = apostaMapper.toDto(aposta);
        restApostaMockMvc.perform(post("/api/apostas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apostaDTO)))
            .andExpect(status().isCreated());

        // Validate the Aposta in the database
        List<Aposta> apostaList = apostaRepository.findAll();
        assertThat(apostaList).hasSize(databaseSizeBeforeCreate + 1);
        Aposta testAposta = apostaList.get(apostaList.size() - 1);
        assertThat(testAposta.getDataAposta()).isEqualTo(DEFAULT_DATA_APOSTA);
    }

    @Test
    @Transactional
    @Ignore
    public void createApostaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = apostaRepository.findAll().size();

        // Create the Aposta with an existing ID
        aposta.setId(1L);
        ApostaDTO apostaDTO = apostaMapper.toDto(aposta);

        // An entity with an existing ID cannot be created, so this API call must fail
        restApostaMockMvc.perform(post("/api/apostas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apostaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Aposta in the database
        List<Aposta> apostaList = apostaRepository.findAll();
        assertThat(apostaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    @Ignore
    public void getAllApostas() throws Exception {
        // Initialize the database
        apostaRepository.saveAndFlush(aposta);

        // Get all the apostaList
        restApostaMockMvc.perform(get("/api/apostas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aposta.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataAposta").value(hasItem(sameInstant(DEFAULT_DATA_APOSTA))));
    }

    @Test
    @Transactional
    @Ignore
    public void getAposta() throws Exception {
        // Initialize the database
        apostaRepository.saveAndFlush(aposta);

        // Get the aposta
        restApostaMockMvc.perform(get("/api/apostas/{id}", aposta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(aposta.getId().intValue()))
            .andExpect(jsonPath("$.dataAposta").value(sameInstant(DEFAULT_DATA_APOSTA)));
    }

    @Test
    @Transactional
    @Ignore
    public void getNonExistingAposta() throws Exception {
        // Get the aposta
        restApostaMockMvc.perform(get("/api/apostas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    @Ignore
    public void updateAposta() throws Exception {
        // Initialize the database
        apostaRepository.saveAndFlush(aposta);
        int databaseSizeBeforeUpdate = apostaRepository.findAll().size();

        // Update the aposta
        Aposta updatedAposta = apostaRepository.findOne(aposta.getId());
        // Disconnect from session so that the updates on updatedAposta are not directly saved in db
        em.detach(updatedAposta);
        updatedAposta
            .dataAposta(UPDATED_DATA_APOSTA);
        ApostaDTO apostaDTO = apostaMapper.toDto(updatedAposta);

        restApostaMockMvc.perform(put("/api/apostas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apostaDTO)))
            .andExpect(status().isOk());

        // Validate the Aposta in the database
        List<Aposta> apostaList = apostaRepository.findAll();
        assertThat(apostaList).hasSize(databaseSizeBeforeUpdate);
        Aposta testAposta = apostaList.get(apostaList.size() - 1);
        assertThat(testAposta.getDataAposta()).isEqualTo(UPDATED_DATA_APOSTA);
    }

    @Test
    @Transactional
    @Ignore
    public void updateNonExistingAposta() throws Exception {
        int databaseSizeBeforeUpdate = apostaRepository.findAll().size();

        // Create the Aposta
        ApostaDTO apostaDTO = apostaMapper.toDto(aposta);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restApostaMockMvc.perform(put("/api/apostas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apostaDTO)))
            .andExpect(status().isCreated());

        // Validate the Aposta in the database
        List<Aposta> apostaList = apostaRepository.findAll();
        assertThat(apostaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    @Ignore
    public void deleteAposta() throws Exception {
        // Initialize the database
        apostaRepository.saveAndFlush(aposta);
        int databaseSizeBeforeDelete = apostaRepository.findAll().size();

        // Get the aposta
        restApostaMockMvc.perform(delete("/api/apostas/{id}", aposta.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Aposta> apostaList = apostaRepository.findAll();
        assertThat(apostaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    @Ignore
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Aposta.class);
        Aposta aposta1 = new Aposta();
        aposta1.setId(1L);
        Aposta aposta2 = new Aposta();
        aposta2.setId(aposta1.getId());
        assertThat(aposta1).isEqualTo(aposta2);
        aposta2.setId(2L);
        assertThat(aposta1).isNotEqualTo(aposta2);
        aposta1.setId(null);
        assertThat(aposta1).isNotEqualTo(aposta2);
    }

    @Test
    @Transactional
    @Ignore
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApostaDTO.class);
        ApostaDTO apostaDTO1 = new ApostaDTO();
        apostaDTO1.setId(1L);
        ApostaDTO apostaDTO2 = new ApostaDTO();
        assertThat(apostaDTO1).isNotEqualTo(apostaDTO2);
        apostaDTO2.setId(apostaDTO1.getId());
        assertThat(apostaDTO1).isEqualTo(apostaDTO2);
        apostaDTO2.setId(2L);
        assertThat(apostaDTO1).isNotEqualTo(apostaDTO2);
        apostaDTO1.setId(null);
        assertThat(apostaDTO1).isNotEqualTo(apostaDTO2);
    }

    @Test
    @Transactional
    @Ignore
    public void testEntityFromId() {
        assertThat(apostaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(apostaMapper.fromId(null)).isNull();
    }
}
