package pl.mdcode.web.rest;

import pl.mdcode.WhisperApp;

import pl.mdcode.domain.SampleOfAgentActivity;
import pl.mdcode.repository.SampleOfAgentActivityRepository;
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
 * Test class for the SampleOfAgentActivityResource REST controller.
 *
 * @see SampleOfAgentActivityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WhisperApp.class)
public class SampleOfAgentActivityResourceIntTest {

    private static final String DEFAULT_ACTIVITY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVITY_NAME = "BBBBBBBBBB";

    @Autowired
    private SampleOfAgentActivityRepository sampleOfAgentActivityRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSampleOfAgentActivityMockMvc;

    private SampleOfAgentActivity sampleOfAgentActivity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SampleOfAgentActivityResource sampleOfAgentActivityResource = new SampleOfAgentActivityResource(sampleOfAgentActivityRepository);
        this.restSampleOfAgentActivityMockMvc = MockMvcBuilders.standaloneSetup(sampleOfAgentActivityResource)
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
    public static SampleOfAgentActivity createEntity(EntityManager em) {
        SampleOfAgentActivity sampleOfAgentActivity = new SampleOfAgentActivity()
            .activityName(DEFAULT_ACTIVITY_NAME);
        return sampleOfAgentActivity;
    }

    @Before
    public void initTest() {
        sampleOfAgentActivity = createEntity(em);
    }

    @Test
    @Transactional
    public void createSampleOfAgentActivity() throws Exception {
        int databaseSizeBeforeCreate = sampleOfAgentActivityRepository.findAll().size();

        // Create the SampleOfAgentActivity
        restSampleOfAgentActivityMockMvc.perform(post("/api/sample-of-agent-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sampleOfAgentActivity)))
            .andExpect(status().isCreated());

        // Validate the SampleOfAgentActivity in the database
        List<SampleOfAgentActivity> sampleOfAgentActivityList = sampleOfAgentActivityRepository.findAll();
        assertThat(sampleOfAgentActivityList).hasSize(databaseSizeBeforeCreate + 1);
        SampleOfAgentActivity testSampleOfAgentActivity = sampleOfAgentActivityList.get(sampleOfAgentActivityList.size() - 1);
        assertThat(testSampleOfAgentActivity.getActivityName()).isEqualTo(DEFAULT_ACTIVITY_NAME);
    }

    @Test
    @Transactional
    public void createSampleOfAgentActivityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sampleOfAgentActivityRepository.findAll().size();

        // Create the SampleOfAgentActivity with an existing ID
        sampleOfAgentActivity.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSampleOfAgentActivityMockMvc.perform(post("/api/sample-of-agent-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sampleOfAgentActivity)))
            .andExpect(status().isBadRequest());

        // Validate the SampleOfAgentActivity in the database
        List<SampleOfAgentActivity> sampleOfAgentActivityList = sampleOfAgentActivityRepository.findAll();
        assertThat(sampleOfAgentActivityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkActivityNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = sampleOfAgentActivityRepository.findAll().size();
        // set the field null
        sampleOfAgentActivity.setActivityName(null);

        // Create the SampleOfAgentActivity, which fails.

        restSampleOfAgentActivityMockMvc.perform(post("/api/sample-of-agent-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sampleOfAgentActivity)))
            .andExpect(status().isBadRequest());

        List<SampleOfAgentActivity> sampleOfAgentActivityList = sampleOfAgentActivityRepository.findAll();
        assertThat(sampleOfAgentActivityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSampleOfAgentActivities() throws Exception {
        // Initialize the database
        sampleOfAgentActivityRepository.saveAndFlush(sampleOfAgentActivity);

        // Get all the sampleOfAgentActivityList
        restSampleOfAgentActivityMockMvc.perform(get("/api/sample-of-agent-activities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sampleOfAgentActivity.getId().intValue())))
            .andExpect(jsonPath("$.[*].activityName").value(hasItem(DEFAULT_ACTIVITY_NAME.toString())));
    }

    @Test
    @Transactional
    public void getSampleOfAgentActivity() throws Exception {
        // Initialize the database
        sampleOfAgentActivityRepository.saveAndFlush(sampleOfAgentActivity);

        // Get the sampleOfAgentActivity
        restSampleOfAgentActivityMockMvc.perform(get("/api/sample-of-agent-activities/{id}", sampleOfAgentActivity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sampleOfAgentActivity.getId().intValue()))
            .andExpect(jsonPath("$.activityName").value(DEFAULT_ACTIVITY_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSampleOfAgentActivity() throws Exception {
        // Get the sampleOfAgentActivity
        restSampleOfAgentActivityMockMvc.perform(get("/api/sample-of-agent-activities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSampleOfAgentActivity() throws Exception {
        // Initialize the database
        sampleOfAgentActivityRepository.saveAndFlush(sampleOfAgentActivity);
        int databaseSizeBeforeUpdate = sampleOfAgentActivityRepository.findAll().size();

        // Update the sampleOfAgentActivity
        SampleOfAgentActivity updatedSampleOfAgentActivity = sampleOfAgentActivityRepository.findOne(sampleOfAgentActivity.getId());
        updatedSampleOfAgentActivity
            .activityName(UPDATED_ACTIVITY_NAME);

        restSampleOfAgentActivityMockMvc.perform(put("/api/sample-of-agent-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSampleOfAgentActivity)))
            .andExpect(status().isOk());

        // Validate the SampleOfAgentActivity in the database
        List<SampleOfAgentActivity> sampleOfAgentActivityList = sampleOfAgentActivityRepository.findAll();
        assertThat(sampleOfAgentActivityList).hasSize(databaseSizeBeforeUpdate);
        SampleOfAgentActivity testSampleOfAgentActivity = sampleOfAgentActivityList.get(sampleOfAgentActivityList.size() - 1);
        assertThat(testSampleOfAgentActivity.getActivityName()).isEqualTo(UPDATED_ACTIVITY_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingSampleOfAgentActivity() throws Exception {
        int databaseSizeBeforeUpdate = sampleOfAgentActivityRepository.findAll().size();

        // Create the SampleOfAgentActivity

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSampleOfAgentActivityMockMvc.perform(put("/api/sample-of-agent-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sampleOfAgentActivity)))
            .andExpect(status().isCreated());

        // Validate the SampleOfAgentActivity in the database
        List<SampleOfAgentActivity> sampleOfAgentActivityList = sampleOfAgentActivityRepository.findAll();
        assertThat(sampleOfAgentActivityList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSampleOfAgentActivity() throws Exception {
        // Initialize the database
        sampleOfAgentActivityRepository.saveAndFlush(sampleOfAgentActivity);
        int databaseSizeBeforeDelete = sampleOfAgentActivityRepository.findAll().size();

        // Get the sampleOfAgentActivity
        restSampleOfAgentActivityMockMvc.perform(delete("/api/sample-of-agent-activities/{id}", sampleOfAgentActivity.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SampleOfAgentActivity> sampleOfAgentActivityList = sampleOfAgentActivityRepository.findAll();
        assertThat(sampleOfAgentActivityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SampleOfAgentActivity.class);
        SampleOfAgentActivity sampleOfAgentActivity1 = new SampleOfAgentActivity();
        sampleOfAgentActivity1.setId(1L);
        SampleOfAgentActivity sampleOfAgentActivity2 = new SampleOfAgentActivity();
        sampleOfAgentActivity2.setId(sampleOfAgentActivity1.getId());
        assertThat(sampleOfAgentActivity1).isEqualTo(sampleOfAgentActivity2);
        sampleOfAgentActivity2.setId(2L);
        assertThat(sampleOfAgentActivity1).isNotEqualTo(sampleOfAgentActivity2);
        sampleOfAgentActivity1.setId(null);
        assertThat(sampleOfAgentActivity1).isNotEqualTo(sampleOfAgentActivity2);
    }
}
