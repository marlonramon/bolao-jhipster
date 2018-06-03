package br.com.bolao.web.rest;

import br.com.bolao.BolaoApp;

import br.com.bolao.domain.Bolao;
import br.com.bolao.repository.BolaoRepository;
import br.com.bolao.service.dto.BolaoDTO;
import br.com.bolao.service.mapper.BolaoMapper;
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
 * Test class for the BolaoResource REST controller.
 *
 * @see BolaoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BolaoApp.class)
public class BolaoResourceIntTest {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Long DEFAULT_PONTOS_ACERTO_DOIS_PLACARES = 1L;
    private static final Long UPDATED_PONTOS_ACERTO_DOIS_PLACARES = 2L;

    private static final Long DEFAULT_PONTOS_ACERTO_UM_PLACAR = 1L;
    private static final Long UPDATED_PONTOS_ACERTO_UM_PLACAR = 2L;

    private static final Long DEFAULT_PONTOS_ACERTO_RESULTADO = 1L;
    private static final Long UPDATED_PONTOS_ACERTO_RESULTADO = 2L;

    @Autowired
    private BolaoRepository bolaoRepository;

    @Autowired
    private BolaoMapper bolaoMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBolaoMockMvc;

    private Bolao bolao;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BolaoResource bolaoResource = new BolaoResource(bolaoRepository, bolaoMapper);
        this.restBolaoMockMvc = MockMvcBuilders.standaloneSetup(bolaoResource)
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
    public static Bolao createEntity(EntityManager em) {
        Bolao bolao = new Bolao()
            .descricao(DEFAULT_DESCRICAO)
            .pontosAcertoDoisPlacares(DEFAULT_PONTOS_ACERTO_DOIS_PLACARES)
            .pontosAcertoUmPlacar(DEFAULT_PONTOS_ACERTO_UM_PLACAR)
            .pontosAcertoResultado(DEFAULT_PONTOS_ACERTO_RESULTADO);
        return bolao;
    }

    @Before
    public void initTest() {
        bolao = createEntity(em);
    }

    @Test
    @Transactional
    public void createBolao() throws Exception {
        int databaseSizeBeforeCreate = bolaoRepository.findAll().size();

        // Create the Bolao
        BolaoDTO bolaoDTO = bolaoMapper.toDto(bolao);
        restBolaoMockMvc.perform(post("/api/bolao")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bolaoDTO)))
            .andExpect(status().isCreated());

        // Validate the Bolao in the database
        List<Bolao> bolaoList = bolaoRepository.findAll();
        assertThat(bolaoList).hasSize(databaseSizeBeforeCreate + 1);
        Bolao testBolao = bolaoList.get(bolaoList.size() - 1);
        assertThat(testBolao.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testBolao.getPontosAcertoDoisPlacares()).isEqualTo(DEFAULT_PONTOS_ACERTO_DOIS_PLACARES);
        assertThat(testBolao.getPontosAcertoUmPlacar()).isEqualTo(DEFAULT_PONTOS_ACERTO_UM_PLACAR);
        assertThat(testBolao.getPontosAcertoResultado()).isEqualTo(DEFAULT_PONTOS_ACERTO_RESULTADO);
    }

    @Test
    @Transactional
    public void createBolaoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bolaoRepository.findAll().size();

        // Create the Bolao with an existing ID
        bolao.setId(1L);
        BolaoDTO bolaoDTO = bolaoMapper.toDto(bolao);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBolaoMockMvc.perform(post("/api/bolao")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bolaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Bolao in the database
        List<Bolao> bolaoList = bolaoRepository.findAll();
        assertThat(bolaoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = bolaoRepository.findAll().size();
        // set the field null
        bolao.setDescricao(null);

        // Create the Bolao, which fails.
        BolaoDTO bolaoDTO = bolaoMapper.toDto(bolao);

        restBolaoMockMvc.perform(post("/api/bolao")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bolaoDTO)))
            .andExpect(status().isBadRequest());

        List<Bolao> bolaoList = bolaoRepository.findAll();
        assertThat(bolaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPontosAcertoDoisPlacaresIsRequired() throws Exception {
        int databaseSizeBeforeTest = bolaoRepository.findAll().size();
        // set the field null
        bolao.setPontosAcertoDoisPlacares(null);

        // Create the Bolao, which fails.
        BolaoDTO bolaoDTO = bolaoMapper.toDto(bolao);

        restBolaoMockMvc.perform(post("/api/bolao")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bolaoDTO)))
            .andExpect(status().isBadRequest());

        List<Bolao> bolaoList = bolaoRepository.findAll();
        assertThat(bolaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPontosAcertoUmPlacarIsRequired() throws Exception {
        int databaseSizeBeforeTest = bolaoRepository.findAll().size();
        // set the field null
        bolao.setPontosAcertoUmPlacar(null);

        // Create the Bolao, which fails.
        BolaoDTO bolaoDTO = bolaoMapper.toDto(bolao);

        restBolaoMockMvc.perform(post("/api/bolao")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bolaoDTO)))
            .andExpect(status().isBadRequest());

        List<Bolao> bolaoList = bolaoRepository.findAll();
        assertThat(bolaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPontosAcertoResultadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = bolaoRepository.findAll().size();
        // set the field null
        bolao.setPontosAcertoResultado(null);

        // Create the Bolao, which fails.
        BolaoDTO bolaoDTO = bolaoMapper.toDto(bolao);

        restBolaoMockMvc.perform(post("/api/bolao")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bolaoDTO)))
            .andExpect(status().isBadRequest());

        List<Bolao> bolaoList = bolaoRepository.findAll();
        assertThat(bolaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBolaos() throws Exception {
        // Initialize the database
        bolaoRepository.saveAndFlush(bolao);

        // Get all the bolaoList
        restBolaoMockMvc.perform(get("/api/bolao?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bolao.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].pontosAcertoDoisPlacares").value(hasItem(DEFAULT_PONTOS_ACERTO_DOIS_PLACARES.intValue())))
            .andExpect(jsonPath("$.[*].pontosAcertoUmPlacar").value(hasItem(DEFAULT_PONTOS_ACERTO_UM_PLACAR.intValue())))
            .andExpect(jsonPath("$.[*].pontosAcertoResultado").value(hasItem(DEFAULT_PONTOS_ACERTO_RESULTADO.intValue())));
    }

    @Test
    @Transactional
    public void getBolao() throws Exception {
        // Initialize the database
        bolaoRepository.saveAndFlush(bolao);

        // Get the bolao
        restBolaoMockMvc.perform(get("/api/bolao/{id}", bolao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bolao.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.pontosAcertoDoisPlacares").value(DEFAULT_PONTOS_ACERTO_DOIS_PLACARES.intValue()))
            .andExpect(jsonPath("$.pontosAcertoUmPlacar").value(DEFAULT_PONTOS_ACERTO_UM_PLACAR.intValue()))
            .andExpect(jsonPath("$.pontosAcertoResultado").value(DEFAULT_PONTOS_ACERTO_RESULTADO.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingBolao() throws Exception {
        // Get the bolao
        restBolaoMockMvc.perform(get("/api/bolao/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBolao() throws Exception {
        // Initialize the database
        bolaoRepository.saveAndFlush(bolao);
        int databaseSizeBeforeUpdate = bolaoRepository.findAll().size();

        // Update the bolao
        Bolao updatedBolao = bolaoRepository.findOne(bolao.getId());
        // Disconnect from session so that the updates on updatedBolao are not directly saved in db
        em.detach(updatedBolao);
        updatedBolao
            .descricao(UPDATED_DESCRICAO)
            .pontosAcertoDoisPlacares(UPDATED_PONTOS_ACERTO_DOIS_PLACARES)
            .pontosAcertoUmPlacar(UPDATED_PONTOS_ACERTO_UM_PLACAR)
            .pontosAcertoResultado(UPDATED_PONTOS_ACERTO_RESULTADO);
        BolaoDTO bolaoDTO = bolaoMapper.toDto(updatedBolao);

        restBolaoMockMvc.perform(put("/api/bolao")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bolaoDTO)))
            .andExpect(status().isOk());

        // Validate the Bolao in the database
        List<Bolao> bolaoList = bolaoRepository.findAll();
        assertThat(bolaoList).hasSize(databaseSizeBeforeUpdate);
        Bolao testBolao = bolaoList.get(bolaoList.size() - 1);
        assertThat(testBolao.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testBolao.getPontosAcertoDoisPlacares()).isEqualTo(UPDATED_PONTOS_ACERTO_DOIS_PLACARES);
        assertThat(testBolao.getPontosAcertoUmPlacar()).isEqualTo(UPDATED_PONTOS_ACERTO_UM_PLACAR);
        assertThat(testBolao.getPontosAcertoResultado()).isEqualTo(UPDATED_PONTOS_ACERTO_RESULTADO);
    }

    @Test
    @Transactional
    public void updateNonExistingBolao() throws Exception {
        int databaseSizeBeforeUpdate = bolaoRepository.findAll().size();

        // Create the Bolao
        BolaoDTO bolaoDTO = bolaoMapper.toDto(bolao);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBolaoMockMvc.perform(put("/api/bolao")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bolaoDTO)))
            .andExpect(status().isCreated());

        // Validate the Bolao in the database
        List<Bolao> bolaoList = bolaoRepository.findAll();
        assertThat(bolaoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBolao() throws Exception {
        // Initialize the database
        bolaoRepository.saveAndFlush(bolao);
        int databaseSizeBeforeDelete = bolaoRepository.findAll().size();

        // Get the bolao
        restBolaoMockMvc.perform(delete("/api/bolao/{id}", bolao.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Bolao> bolaoList = bolaoRepository.findAll();
        assertThat(bolaoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bolao.class);
        Bolao bolao1 = new Bolao();
        bolao1.setId(1L);
        Bolao bolao2 = new Bolao();
        bolao2.setId(bolao1.getId());
        assertThat(bolao1).isEqualTo(bolao2);
        bolao2.setId(2L);
        assertThat(bolao1).isNotEqualTo(bolao2);
        bolao1.setId(null);
        assertThat(bolao1).isNotEqualTo(bolao2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BolaoDTO.class);
        BolaoDTO bolaoDTO1 = new BolaoDTO();
        bolaoDTO1.setId(1L);
        BolaoDTO bolaoDTO2 = new BolaoDTO();
        assertThat(bolaoDTO1).isNotEqualTo(bolaoDTO2);
        bolaoDTO2.setId(bolaoDTO1.getId());
        assertThat(bolaoDTO1).isEqualTo(bolaoDTO2);
        bolaoDTO2.setId(2L);
        assertThat(bolaoDTO1).isNotEqualTo(bolaoDTO2);
        bolaoDTO1.setId(null);
        assertThat(bolaoDTO1).isNotEqualTo(bolaoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(bolaoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(bolaoMapper.fromId(null)).isNull();
    }
}
