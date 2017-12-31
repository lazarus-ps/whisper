package pl.mdcode.web.rest;

import pl.mdcode.WhisperApp;

import pl.mdcode.domain.Principle;
import pl.mdcode.repository.PrincipleRepository;
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

import pl.mdcode.domain.enumeration.PRINCIPLE_STATUS;
/**
 * Test class for the PrincipleResource REST controller.
 *
 * @see PrincipleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WhisperApp.class)
public class PrincipleResourceIntTest {

    private static final String DEFAULT_NIP = "AAAAAAAAAA";
    private static final String UPDATED_NIP = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_NAME = "BBBBBBBBBB";

    private static final PRINCIPLE_STATUS DEFAULT_PRINCIPLE_STATUS = PRINCIPLE_STATUS.ACTIVE;
    private static final PRINCIPLE_STATUS UPDATED_PRINCIPLE_STATUS = PRINCIPLE_STATUS.INACTIVE;

    @Autowired
    private PrincipleRepository principleRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPrincipleMockMvc;

    private Principle principle;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PrincipleResource principleResource = new PrincipleResource(principleRepository);
        this.restPrincipleMockMvc = MockMvcBuilders.standaloneSetup(principleResource)
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
    public static Principle createEntity(EntityManager em) {
        Principle principle = new Principle()
            .nip(DEFAULT_NIP)
            .companyName(DEFAULT_COMPANY_NAME)
            .principleStatus(DEFAULT_PRINCIPLE_STATUS);
        return principle;
    }

    @Before
    public void initTest() {
        principle = createEntity(em);
    }

    @Test
    @Transactional
    public void createPrinciple() throws Exception {
        int databaseSizeBeforeCreate = principleRepository.findAll().size();

        // Create the Principle
        restPrincipleMockMvc.perform(post("/api/principles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(principle)))
            .andExpect(status().isCreated());

        // Validate the Principle in the database
        List<Principle> principleList = principleRepository.findAll();
        assertThat(principleList).hasSize(databaseSizeBeforeCreate + 1);
        Principle testPrinciple = principleList.get(principleList.size() - 1);
        assertThat(testPrinciple.getNip()).isEqualTo(DEFAULT_NIP);
        assertThat(testPrinciple.getCompanyName()).isEqualTo(DEFAULT_COMPANY_NAME);
        assertThat(testPrinciple.getPrincipleStatus()).isEqualTo(DEFAULT_PRINCIPLE_STATUS);
    }

    @Test
    @Transactional
    public void createPrincipleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = principleRepository.findAll().size();

        // Create the Principle with an existing ID
        principle.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrincipleMockMvc.perform(post("/api/principles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(principle)))
            .andExpect(status().isBadRequest());

        // Validate the Principle in the database
        List<Principle> principleList = principleRepository.findAll();
        assertThat(principleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPrinciples() throws Exception {
        // Initialize the database
        principleRepository.saveAndFlush(principle);

        // Get all the principleList
        restPrincipleMockMvc.perform(get("/api/principles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(principle.getId().intValue())))
            .andExpect(jsonPath("$.[*].nip").value(hasItem(DEFAULT_NIP.toString())))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME.toString())))
            .andExpect(jsonPath("$.[*].principleStatus").value(hasItem(DEFAULT_PRINCIPLE_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getPrinciple() throws Exception {
        // Initialize the database
        principleRepository.saveAndFlush(principle);

        // Get the principle
        restPrincipleMockMvc.perform(get("/api/principles/{id}", principle.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(principle.getId().intValue()))
            .andExpect(jsonPath("$.nip").value(DEFAULT_NIP.toString()))
            .andExpect(jsonPath("$.companyName").value(DEFAULT_COMPANY_NAME.toString()))
            .andExpect(jsonPath("$.principleStatus").value(DEFAULT_PRINCIPLE_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPrinciple() throws Exception {
        // Get the principle
        restPrincipleMockMvc.perform(get("/api/principles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePrinciple() throws Exception {
        // Initialize the database
        principleRepository.saveAndFlush(principle);
        int databaseSizeBeforeUpdate = principleRepository.findAll().size();

        // Update the principle
        Principle updatedPrinciple = principleRepository.findOne(principle.getId());
        updatedPrinciple
            .nip(UPDATED_NIP)
            .companyName(UPDATED_COMPANY_NAME)
            .principleStatus(UPDATED_PRINCIPLE_STATUS);

        restPrincipleMockMvc.perform(put("/api/principles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPrinciple)))
            .andExpect(status().isOk());

        // Validate the Principle in the database
        List<Principle> principleList = principleRepository.findAll();
        assertThat(principleList).hasSize(databaseSizeBeforeUpdate);
        Principle testPrinciple = principleList.get(principleList.size() - 1);
        assertThat(testPrinciple.getNip()).isEqualTo(UPDATED_NIP);
        assertThat(testPrinciple.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testPrinciple.getPrincipleStatus()).isEqualTo(UPDATED_PRINCIPLE_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingPrinciple() throws Exception {
        int databaseSizeBeforeUpdate = principleRepository.findAll().size();

        // Create the Principle

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPrincipleMockMvc.perform(put("/api/principles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(principle)))
            .andExpect(status().isCreated());

        // Validate the Principle in the database
        List<Principle> principleList = principleRepository.findAll();
        assertThat(principleList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePrinciple() throws Exception {
        // Initialize the database
        principleRepository.saveAndFlush(principle);
        int databaseSizeBeforeDelete = principleRepository.findAll().size();

        // Get the principle
        restPrincipleMockMvc.perform(delete("/api/principles/{id}", principle.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Principle> principleList = principleRepository.findAll();
        assertThat(principleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Principle.class);
        Principle principle1 = new Principle();
        principle1.setId(1L);
        Principle principle2 = new Principle();
        principle2.setId(principle1.getId());
        assertThat(principle1).isEqualTo(principle2);
        principle2.setId(2L);
        assertThat(principle1).isNotEqualTo(principle2);
        principle1.setId(null);
        assertThat(principle1).isNotEqualTo(principle2);
    }
}
