package pl.mdcode.web.rest;

import pl.mdcode.WhisperApp;

import pl.mdcode.domain.AgentRate;
import pl.mdcode.repository.AgentRateRepository;
import pl.mdcode.web.rest.errors.ExceptionTranslator;

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

import static pl.mdcode.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AgentRateResource REST controller.
 *
 * @see AgentRateResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WhisperApp.class)
public class AgentRateResourceIntTest {

    private static final Integer DEFAULT_RATE = 1;
    private static final Integer UPDATED_RATE = 2;

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    @Autowired
    private AgentRateRepository agentRateRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAgentRateMockMvc;

    private AgentRate agentRate;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AgentRateResource agentRateResource = new AgentRateResource(agentRateRepository);
        this.restAgentRateMockMvc = MockMvcBuilders.standaloneSetup(agentRateResource)
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
    public static AgentRate createEntity(EntityManager em) {
        AgentRate agentRate = new AgentRate()
            .rate(DEFAULT_RATE)
            .comment(DEFAULT_COMMENT);
        return agentRate;
    }

    @Before
    public void initTest() {
        agentRate = createEntity(em);
    }

    @Test
    @Transactional
    public void createAgentRate() throws Exception {
        int databaseSizeBeforeCreate = agentRateRepository.findAll().size();

        // Create the AgentRate
        restAgentRateMockMvc.perform(post("/api/agent-rates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agentRate)))
            .andExpect(status().isCreated());

        // Validate the AgentRate in the database
        List<AgentRate> agentRateList = agentRateRepository.findAll();
        assertThat(agentRateList).hasSize(databaseSizeBeforeCreate + 1);
        AgentRate testAgentRate = agentRateList.get(agentRateList.size() - 1);
        assertThat(testAgentRate.getRate()).isEqualTo(DEFAULT_RATE);
        assertThat(testAgentRate.getComment()).isEqualTo(DEFAULT_COMMENT);
    }

    @Test
    @Transactional
    public void createAgentRateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = agentRateRepository.findAll().size();

        // Create the AgentRate with an existing ID
        agentRate.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAgentRateMockMvc.perform(post("/api/agent-rates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agentRate)))
            .andExpect(status().isBadRequest());

        // Validate the AgentRate in the database
        List<AgentRate> agentRateList = agentRateRepository.findAll();
        assertThat(agentRateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkRateIsRequired() throws Exception {
        int databaseSizeBeforeTest = agentRateRepository.findAll().size();
        // set the field null
        agentRate.setRate(null);

        // Create the AgentRate, which fails.

        restAgentRateMockMvc.perform(post("/api/agent-rates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agentRate)))
            .andExpect(status().isBadRequest());

        List<AgentRate> agentRateList = agentRateRepository.findAll();
        assertThat(agentRateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCommentIsRequired() throws Exception {
        int databaseSizeBeforeTest = agentRateRepository.findAll().size();
        // set the field null
        agentRate.setComment(null);

        // Create the AgentRate, which fails.

        restAgentRateMockMvc.perform(post("/api/agent-rates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agentRate)))
            .andExpect(status().isBadRequest());

        List<AgentRate> agentRateList = agentRateRepository.findAll();
        assertThat(agentRateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAgentRates() throws Exception {
        // Initialize the database
        agentRateRepository.saveAndFlush(agentRate);

        // Get all the agentRateList
        restAgentRateMockMvc.perform(get("/api/agent-rates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(agentRate.getId().intValue())))
            .andExpect(jsonPath("$.[*].rate").value(hasItem(DEFAULT_RATE)))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())));
    }

    @Test
    @Transactional
    public void getAgentRate() throws Exception {
        // Initialize the database
        agentRateRepository.saveAndFlush(agentRate);

        // Get the agentRate
        restAgentRateMockMvc.perform(get("/api/agent-rates/{id}", agentRate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(agentRate.getId().intValue()))
            .andExpect(jsonPath("$.rate").value(DEFAULT_RATE))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAgentRate() throws Exception {
        // Get the agentRate
        restAgentRateMockMvc.perform(get("/api/agent-rates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAgentRate() throws Exception {
        // Initialize the database
        agentRateRepository.saveAndFlush(agentRate);
        int databaseSizeBeforeUpdate = agentRateRepository.findAll().size();

        // Update the agentRate
        AgentRate updatedAgentRate = agentRateRepository.findOne(agentRate.getId());
        updatedAgentRate
            .rate(UPDATED_RATE)
            .comment(UPDATED_COMMENT);

        restAgentRateMockMvc.perform(put("/api/agent-rates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAgentRate)))
            .andExpect(status().isOk());

        // Validate the AgentRate in the database
        List<AgentRate> agentRateList = agentRateRepository.findAll();
        assertThat(agentRateList).hasSize(databaseSizeBeforeUpdate);
        AgentRate testAgentRate = agentRateList.get(agentRateList.size() - 1);
        assertThat(testAgentRate.getRate()).isEqualTo(UPDATED_RATE);
        assertThat(testAgentRate.getComment()).isEqualTo(UPDATED_COMMENT);
    }

    @Test
    @Transactional
    public void updateNonExistingAgentRate() throws Exception {
        int databaseSizeBeforeUpdate = agentRateRepository.findAll().size();

        // Create the AgentRate

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAgentRateMockMvc.perform(put("/api/agent-rates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agentRate)))
            .andExpect(status().isCreated());

        // Validate the AgentRate in the database
        List<AgentRate> agentRateList = agentRateRepository.findAll();
        assertThat(agentRateList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAgentRate() throws Exception {
        // Initialize the database
        agentRateRepository.saveAndFlush(agentRate);
        int databaseSizeBeforeDelete = agentRateRepository.findAll().size();

        // Get the agentRate
        restAgentRateMockMvc.perform(delete("/api/agent-rates/{id}", agentRate.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AgentRate> agentRateList = agentRateRepository.findAll();
        assertThat(agentRateList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AgentRate.class);
        AgentRate agentRate1 = new AgentRate();
        agentRate1.setId(1L);
        AgentRate agentRate2 = new AgentRate();
        agentRate2.setId(agentRate1.getId());
        assertThat(agentRate1).isEqualTo(agentRate2);
        agentRate2.setId(2L);
        assertThat(agentRate1).isNotEqualTo(agentRate2);
        agentRate1.setId(null);
        assertThat(agentRate1).isNotEqualTo(agentRate2);
    }
}
