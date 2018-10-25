package br.com.bolao.web.rest;

import static br.com.bolao.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import br.com.bolao.BolaoApp;
import br.com.bolao.domain.Aposta;
import br.com.bolao.domain.Partida;
import br.com.bolao.domain.Placar;
import br.com.bolao.domain.User;
import br.com.bolao.repository.ApostaRepository;
import br.com.bolao.repository.PartidaRepository;
import br.com.bolao.repository.RodadaRepository;
import br.com.bolao.service.ApostaService;
import br.com.bolao.service.UserService;
import br.com.bolao.service.dto.ApostaDTO;
import br.com.bolao.service.mapper.ApostaMapper;
import br.com.bolao.web.rest.errors.ExceptionTranslator;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BolaoApp.class)
public class ApostaResourceIntTest {

	private static final ZonedDateTime DEFAULT_DATA_APOSTA = ZonedDateTime.of(LocalDateTime.of(2018, Month.JUNE,  21,   16,   30), 
																							   ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATA_APOSTA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    
    
    
    private static final ZonedDateTime DEFAULT_DATA_PARTIDA = ZonedDateTime.of(LocalDateTime.of(2018, Month.JUNE,  22,   16,   30), 
			   																	ZoneOffset.UTC);

    @Mock
    private ApostaRepository apostaRepositoryMock;
    
    
    @Autowired
    private PartidaRepository partidaRepository;

    @Autowired
    private ApostaMapper apostaMapper;

    @Mock    
    private UserService userServiceMock;
    
    @Autowired
    private RodadaRepository rodadaRepository;
    
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
        apostaService = new ApostaService(userServiceMock, partidaRepository, rodadaRepository, apostaRepositoryMock);
        
        final ApostaResource apostaResource = new ApostaResource(apostaMapper, apostaService, userServiceMock);
        this.restApostaMockMvc = MockMvcBuilders.standaloneSetup(apostaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

   
    public static Aposta createEntity(EntityManager em) {
        Aposta aposta = new Aposta()
            .dataAposta(DEFAULT_DATA_APOSTA);
        
        aposta.setPontuacao(0L);
        
        return aposta;
    }

    @Before
    public void initTest() {
        aposta = createEntity(em);
    }

    @Test
    @Transactional    
    public void createAposta() throws Exception {
        int databaseSizeBeforeCreate = apostaRepositoryMock.findAll().size();

        Partida partida = new Partida();
        partida.setDataPartida(DEFAULT_DATA_PARTIDA);
        
        partida = partidaRepository.save(partida);
        
        Short placarMandante = 1;
        Short placarVisitante = 0;
        
        aposta.setPlacar(new Placar(placarMandante,placarVisitante));
        
        aposta.setPartida(partida);
        
        User user = new User();
        	
        
        
        when(userServiceMock.getUserWithAuthorities()).thenReturn(Optional.of(user));
        when(apostaRepositoryMock.findByPartidaAndUser(partida, user)).thenReturn(Optional.ofNullable(null));
       // when(apostaRepositoryMock.save(aposta)).thenReturn(aposta);
        
        // Create the Aposta
        ApostaDTO apostaDTO = apostaMapper.toDto(aposta);
        restApostaMockMvc.perform(post("/api/apostas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apostaDTO)))
            .andExpect(status().isCreated());


        List<Aposta> apostaList = apostaRepositoryMock.findAll();
        assertThat(apostaList).hasSize(databaseSizeBeforeCreate + 1);
        Aposta testAposta = apostaList.get(apostaList.size() - 1);
        assertThat(testAposta.getDataAposta()).isEqualTo(DEFAULT_DATA_APOSTA);
    }

    @Test
    @Transactional
    @Ignore
    public void createApostaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = apostaRepositoryMock.findAll().size();

        // Create the Aposta with an existing ID
        aposta.setId(1L);
        ApostaDTO apostaDTO = apostaMapper.toDto(aposta);

        // An entity with an existing ID cannot be created, so this API call must fail
        restApostaMockMvc.perform(post("/api/apostas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apostaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Aposta in the database
        List<Aposta> apostaList = apostaRepositoryMock.findAll();
        assertThat(apostaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    @Ignore
    public void updateAposta() throws Exception {
        // Initialize the database
        apostaRepositoryMock.saveAndFlush(aposta);
        int databaseSizeBeforeUpdate = apostaRepositoryMock.findAll().size();

        // Update the aposta
        Aposta updatedAposta = apostaRepositoryMock.findOne(aposta.getId());
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
        List<Aposta> apostaList = apostaRepositoryMock.findAll();
        assertThat(apostaList).hasSize(databaseSizeBeforeUpdate);
        Aposta testAposta = apostaList.get(apostaList.size() - 1);
        assertThat(testAposta.getDataAposta()).isEqualTo(UPDATED_DATA_APOSTA);
    }

    @Test
    @Transactional
    @Ignore
    public void updateNonExistingAposta() throws Exception {
        int databaseSizeBeforeUpdate = apostaRepositoryMock.findAll().size();

        // Create the Aposta
        ApostaDTO apostaDTO = apostaMapper.toDto(aposta);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restApostaMockMvc.perform(put("/api/apostas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apostaDTO)))
            .andExpect(status().isCreated());

        // Validate the Aposta in the database
        List<Aposta> apostaList = apostaRepositoryMock.findAll();
        assertThat(apostaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    @Ignore
    public void deleteAposta() throws Exception {
        // Initialize the database
        apostaRepositoryMock.saveAndFlush(aposta);
        int databaseSizeBeforeDelete = apostaRepositoryMock.findAll().size();

        // Get the aposta
        restApostaMockMvc.perform(delete("/api/apostas/{id}", aposta.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Aposta> apostaList = apostaRepositoryMock.findAll();
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
