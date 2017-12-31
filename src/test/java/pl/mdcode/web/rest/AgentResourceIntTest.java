package pl.mdcode.web.rest;

import pl.mdcode.WhisperApp;

import pl.mdcode.domain.Agent;
import pl.mdcode.repository.AgentRepository;
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
import java.math.BigDecimal;
import java.util.List;

import static pl.mdcode.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import pl.mdcode.domain.enumeration.LEGAL_PERSONALITY;
import pl.mdcode.domain.enumeration.AGENT_STATUS;
/**
 * Test class for the AgentResource REST controller.
 *
 * @see AgentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WhisperApp.class)
public class AgentResourceIntTest {

    private static final String DEFAULT_SHORT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_SHORT_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_FULL_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_FULL_DESCRIPTION = "BBBBBBBBBB";

    private static final LEGAL_PERSONALITY DEFAULT_LEGAL_PERSONALITY = LEGAL_PERSONALITY.COMPANY;
    private static final LEGAL_PERSONALITY UPDATED_LEGAL_PERSONALITY = LEGAL_PERSONALITY.PERSON;

    private static final AGENT_STATUS DEFAULT_AGENT_STATUS = AGENT_STATUS.ACTIVE;
    private static final AGENT_STATUS UPDATED_AGENT_STATUS = AGENT_STATUS.INACTIVE;

    private static final BigDecimal DEFAULT_PREFERRED_RATE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PREFERRED_RATE = new BigDecimal(2);

    @Autowired
    private AgentRepository agentRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAgentMockMvc;

    private Agent agent;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AgentResource agentResource = new AgentResource(agentRepository);
        this.restAgentMockMvc = MockMvcBuilders.standaloneSetup(agentResource)
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
    public static Agent createEntity(EntityManager em) {
        Agent agent = new Agent()
            .shortDescription(DEFAULT_SHORT_DESCRIPTION)
            .fullDescription(DEFAULT_FULL_DESCRIPTION)
            .legalPersonality(DEFAULT_LEGAL_PERSONALITY)
            .agentStatus(DEFAULT_AGENT_STATUS)
            .preferredRate(DEFAULT_PREFERRED_RATE);
        return agent;
    }

    @Before
    public void initTest() {
        agent = createEntity(em);
    }

    @Test
    @Transactional
    public void createAgent() throws Exception {
        int databaseSizeBeforeCreate = agentRepository.findAll().size();

        // Create the Agent
        restAgentMockMvc.perform(post("/api/agents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agent)))
            .andExpect(status().isCreated());

        // Validate the Agent in the database
        List<Agent> agentList = agentRepository.findAll();
        assertThat(agentList).hasSize(databaseSizeBeforeCreate + 1);
        Agent testAgent = agentList.get(agentList.size() - 1);
        assertThat(testAgent.getShortDescription()).isEqualTo(DEFAULT_SHORT_DESCRIPTION);
        assertThat(testAgent.getFullDescription()).isEqualTo(DEFAULT_FULL_DESCRIPTION);
        assertThat(testAgent.getLegalPersonality()).isEqualTo(DEFAULT_LEGAL_PERSONALITY);
        assertThat(testAgent.getAgentStatus()).isEqualTo(DEFAULT_AGENT_STATUS);
        assertThat(testAgent.getPreferredRate()).isEqualTo(DEFAULT_PREFERRED_RATE);
    }

    @Test
    @Transactional
    public void createAgentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = agentRepository.findAll().size();

        // Create the Agent with an existing ID
        agent.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAgentMockMvc.perform(post("/api/agents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agent)))
            .andExpect(status().isBadRequest());

        // Validate the Agent in the database
        List<Agent> agentList = agentRepository.findAll();
        assertThat(agentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkShortDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = agentRepository.findAll().size();
        // set the field null
        agent.setShortDescription(null);

        // Create the Agent, which fails.

        restAgentMockMvc.perform(post("/api/agents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agent)))
            .andExpect(status().isBadRequest());

        List<Agent> agentList = agentRepository.findAll();
        assertThat(agentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFullDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = agentRepository.findAll().size();
        // set the field null
        agent.setFullDescription(null);

        // Create the Agent, which fails.

        restAgentMockMvc.perform(post("/api/agents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agent)))
            .andExpect(status().isBadRequest());

        List<Agent> agentList = agentRepository.findAll();
        assertThat(agentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPreferredRateIsRequired() throws Exception {
        int databaseSizeBeforeTest = agentRepository.findAll().size();
        // set the field null
        agent.setPreferredRate(null);

        // Create the Agent, which fails.

        restAgentMockMvc.perform(post("/api/agents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agent)))
            .andExpect(status().isBadRequest());

        List<Agent> agentList = agentRepository.findAll();
        assertThat(agentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAgents() throws Exception {
        // Initialize the database
        agentRepository.saveAndFlush(agent);

        // Get all the agentList
        restAgentMockMvc.perform(get("/api/agents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(agent.getId().intValue())))
            .andExpect(jsonPath("$.[*].shortDescription").value(hasItem(DEFAULT_SHORT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].fullDescription").value(hasItem(DEFAULT_FULL_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].legalPersonality").value(hasItem(DEFAULT_LEGAL_PERSONALITY.toString())))
            .andExpect(jsonPath("$.[*].agentStatus").value(hasItem(DEFAULT_AGENT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].preferredRate").value(hasItem(DEFAULT_PREFERRED_RATE.intValue())));
    }

    @Test
    @Transactional
    public void getAgent() throws Exception {
        // Initialize the database
        agentRepository.saveAndFlush(agent);

        // Get the agent
        restAgentMockMvc.perform(get("/api/agents/{id}", agent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(agent.getId().intValue()))
            .andExpect(jsonPath("$.shortDescription").value(DEFAULT_SHORT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.fullDescription").value(DEFAULT_FULL_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.legalPersonality").value(DEFAULT_LEGAL_PERSONALITY.toString()))
            .andExpect(jsonPath("$.agentStatus").value(DEFAULT_AGENT_STATUS.toString()))
            .andExpect(jsonPath("$.preferredRate").value(DEFAULT_PREFERRED_RATE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAgent() throws Exception {
        // Get the agent
        restAgentMockMvc.perform(get("/api/agents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAgent() throws Exception {
        // Initialize the database
        agentRepository.saveAndFlush(agent);
        int databaseSizeBeforeUpdate = agentRepository.findAll().size();

        // Update the agent
        Agent updatedAgent = agentRepository.findOne(agent.getId());
        updatedAgent
            .shortDescription(UPDATED_SHORT_DESCRIPTION)
            .fullDescription(UPDATED_FULL_DESCRIPTION)
            .legalPersonality(UPDATED_LEGAL_PERSONALITY)
            .agentStatus(UPDATED_AGENT_STATUS)
            .preferredRate(UPDATED_PREFERRED_RATE);

        restAgentMockMvc.perform(put("/api/agents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAgent)))
            .andExpect(status().isOk());

        // Validate the Agent in the database
        List<Agent> agentList = agentRepository.findAll();
        assertThat(agentList).hasSize(databaseSizeBeforeUpdate);
        Agent testAgent = agentList.get(agentList.size() - 1);
        assertThat(testAgent.getShortDescription()).isEqualTo(UPDATED_SHORT_DESCRIPTION);
        assertThat(testAgent.getFullDescription()).isEqualTo(UPDATED_FULL_DESCRIPTION);
        assertThat(testAgent.getLegalPersonality()).isEqualTo(UPDATED_LEGAL_PERSONALITY);
        assertThat(testAgent.getAgentStatus()).isEqualTo(UPDATED_AGENT_STATUS);
        assertThat(testAgent.getPreferredRate()).isEqualTo(UPDATED_PREFERRED_RATE);
    }

    @Test
    @Transactional
    public void updateNonExistingAgent() throws Exception {
        int databaseSizeBeforeUpdate = agentRepository.findAll().size();

        // Create the Agent

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAgentMockMvc.perform(put("/api/agents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agent)))
            .andExpect(status().isCreated());

        // Validate the Agent in the database
        List<Agent> agentList = agentRepository.findAll();
        assertThat(agentList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAgent() throws Exception {
        // Initialize the database
        agentRepository.saveAndFlush(agent);
        int databaseSizeBeforeDelete = agentRepository.findAll().size();

        // Get the agent
        restAgentMockMvc.perform(delete("/api/agents/{id}", agent.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Agent> agentList = agentRepository.findAll();
        assertThat(agentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Agent.class);
        Agent agent1 = new Agent();
        agent1.setId(1L);
        Agent agent2 = new Agent();
        agent2.setId(agent1.getId());
        assertThat(agent1).isEqualTo(agent2);
        agent2.setId(2L);
        assertThat(agent1).isNotEqualTo(agent2);
        agent1.setId(null);
        assertThat(agent1).isNotEqualTo(agent2);
    }
}
