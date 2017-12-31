package pl.mdcode.web.rest;

import pl.mdcode.WhisperApp;

import pl.mdcode.domain.SampleOfCampaignActivity;
import pl.mdcode.repository.SampleOfCampaignActivityRepository;
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
 * Test class for the SampleOfCampaignActivityResource REST controller.
 *
 * @see SampleOfCampaignActivityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WhisperApp.class)
public class SampleOfCampaignActivityResourceIntTest {

    private static final String DEFAULT_ACTIVITY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVITY_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_LIST_OF_POSTS = 1;
    private static final Integer UPDATED_LIST_OF_POSTS = 2;

    @Autowired
    private SampleOfCampaignActivityRepository sampleOfCampaignActivityRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSampleOfCampaignActivityMockMvc;

    private SampleOfCampaignActivity sampleOfCampaignActivity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SampleOfCampaignActivityResource sampleOfCampaignActivityResource = new SampleOfCampaignActivityResource(sampleOfCampaignActivityRepository);
        this.restSampleOfCampaignActivityMockMvc = MockMvcBuilders.standaloneSetup(sampleOfCampaignActivityResource)
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
    public static SampleOfCampaignActivity createEntity(EntityManager em) {
        SampleOfCampaignActivity sampleOfCampaignActivity = new SampleOfCampaignActivity()
            .activityName(DEFAULT_ACTIVITY_NAME)
            .listOfPosts(DEFAULT_LIST_OF_POSTS);
        return sampleOfCampaignActivity;
    }

    @Before
    public void initTest() {
        sampleOfCampaignActivity = createEntity(em);
    }

    @Test
    @Transactional
    public void createSampleOfCampaignActivity() throws Exception {
        int databaseSizeBeforeCreate = sampleOfCampaignActivityRepository.findAll().size();

        // Create the SampleOfCampaignActivity
        restSampleOfCampaignActivityMockMvc.perform(post("/api/sample-of-campaign-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sampleOfCampaignActivity)))
            .andExpect(status().isCreated());

        // Validate the SampleOfCampaignActivity in the database
        List<SampleOfCampaignActivity> sampleOfCampaignActivityList = sampleOfCampaignActivityRepository.findAll();
        assertThat(sampleOfCampaignActivityList).hasSize(databaseSizeBeforeCreate + 1);
        SampleOfCampaignActivity testSampleOfCampaignActivity = sampleOfCampaignActivityList.get(sampleOfCampaignActivityList.size() - 1);
        assertThat(testSampleOfCampaignActivity.getActivityName()).isEqualTo(DEFAULT_ACTIVITY_NAME);
        assertThat(testSampleOfCampaignActivity.getListOfPosts()).isEqualTo(DEFAULT_LIST_OF_POSTS);
    }

    @Test
    @Transactional
    public void createSampleOfCampaignActivityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sampleOfCampaignActivityRepository.findAll().size();

        // Create the SampleOfCampaignActivity with an existing ID
        sampleOfCampaignActivity.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSampleOfCampaignActivityMockMvc.perform(post("/api/sample-of-campaign-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sampleOfCampaignActivity)))
            .andExpect(status().isBadRequest());

        // Validate the SampleOfCampaignActivity in the database
        List<SampleOfCampaignActivity> sampleOfCampaignActivityList = sampleOfCampaignActivityRepository.findAll();
        assertThat(sampleOfCampaignActivityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkActivityNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = sampleOfCampaignActivityRepository.findAll().size();
        // set the field null
        sampleOfCampaignActivity.setActivityName(null);

        // Create the SampleOfCampaignActivity, which fails.

        restSampleOfCampaignActivityMockMvc.perform(post("/api/sample-of-campaign-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sampleOfCampaignActivity)))
            .andExpect(status().isBadRequest());

        List<SampleOfCampaignActivity> sampleOfCampaignActivityList = sampleOfCampaignActivityRepository.findAll();
        assertThat(sampleOfCampaignActivityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSampleOfCampaignActivities() throws Exception {
        // Initialize the database
        sampleOfCampaignActivityRepository.saveAndFlush(sampleOfCampaignActivity);

        // Get all the sampleOfCampaignActivityList
        restSampleOfCampaignActivityMockMvc.perform(get("/api/sample-of-campaign-activities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sampleOfCampaignActivity.getId().intValue())))
            .andExpect(jsonPath("$.[*].activityName").value(hasItem(DEFAULT_ACTIVITY_NAME.toString())))
            .andExpect(jsonPath("$.[*].listOfPosts").value(hasItem(DEFAULT_LIST_OF_POSTS)));
    }

    @Test
    @Transactional
    public void getSampleOfCampaignActivity() throws Exception {
        // Initialize the database
        sampleOfCampaignActivityRepository.saveAndFlush(sampleOfCampaignActivity);

        // Get the sampleOfCampaignActivity
        restSampleOfCampaignActivityMockMvc.perform(get("/api/sample-of-campaign-activities/{id}", sampleOfCampaignActivity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sampleOfCampaignActivity.getId().intValue()))
            .andExpect(jsonPath("$.activityName").value(DEFAULT_ACTIVITY_NAME.toString()))
            .andExpect(jsonPath("$.listOfPosts").value(DEFAULT_LIST_OF_POSTS));
    }

    @Test
    @Transactional
    public void getNonExistingSampleOfCampaignActivity() throws Exception {
        // Get the sampleOfCampaignActivity
        restSampleOfCampaignActivityMockMvc.perform(get("/api/sample-of-campaign-activities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSampleOfCampaignActivity() throws Exception {
        // Initialize the database
        sampleOfCampaignActivityRepository.saveAndFlush(sampleOfCampaignActivity);
        int databaseSizeBeforeUpdate = sampleOfCampaignActivityRepository.findAll().size();

        // Update the sampleOfCampaignActivity
        SampleOfCampaignActivity updatedSampleOfCampaignActivity = sampleOfCampaignActivityRepository.findOne(sampleOfCampaignActivity.getId());
        updatedSampleOfCampaignActivity
            .activityName(UPDATED_ACTIVITY_NAME)
            .listOfPosts(UPDATED_LIST_OF_POSTS);

        restSampleOfCampaignActivityMockMvc.perform(put("/api/sample-of-campaign-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSampleOfCampaignActivity)))
            .andExpect(status().isOk());

        // Validate the SampleOfCampaignActivity in the database
        List<SampleOfCampaignActivity> sampleOfCampaignActivityList = sampleOfCampaignActivityRepository.findAll();
        assertThat(sampleOfCampaignActivityList).hasSize(databaseSizeBeforeUpdate);
        SampleOfCampaignActivity testSampleOfCampaignActivity = sampleOfCampaignActivityList.get(sampleOfCampaignActivityList.size() - 1);
        assertThat(testSampleOfCampaignActivity.getActivityName()).isEqualTo(UPDATED_ACTIVITY_NAME);
        assertThat(testSampleOfCampaignActivity.getListOfPosts()).isEqualTo(UPDATED_LIST_OF_POSTS);
    }

    @Test
    @Transactional
    public void updateNonExistingSampleOfCampaignActivity() throws Exception {
        int databaseSizeBeforeUpdate = sampleOfCampaignActivityRepository.findAll().size();

        // Create the SampleOfCampaignActivity

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSampleOfCampaignActivityMockMvc.perform(put("/api/sample-of-campaign-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sampleOfCampaignActivity)))
            .andExpect(status().isCreated());

        // Validate the SampleOfCampaignActivity in the database
        List<SampleOfCampaignActivity> sampleOfCampaignActivityList = sampleOfCampaignActivityRepository.findAll();
        assertThat(sampleOfCampaignActivityList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSampleOfCampaignActivity() throws Exception {
        // Initialize the database
        sampleOfCampaignActivityRepository.saveAndFlush(sampleOfCampaignActivity);
        int databaseSizeBeforeDelete = sampleOfCampaignActivityRepository.findAll().size();

        // Get the sampleOfCampaignActivity
        restSampleOfCampaignActivityMockMvc.perform(delete("/api/sample-of-campaign-activities/{id}", sampleOfCampaignActivity.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SampleOfCampaignActivity> sampleOfCampaignActivityList = sampleOfCampaignActivityRepository.findAll();
        assertThat(sampleOfCampaignActivityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SampleOfCampaignActivity.class);
        SampleOfCampaignActivity sampleOfCampaignActivity1 = new SampleOfCampaignActivity();
        sampleOfCampaignActivity1.setId(1L);
        SampleOfCampaignActivity sampleOfCampaignActivity2 = new SampleOfCampaignActivity();
        sampleOfCampaignActivity2.setId(sampleOfCampaignActivity1.getId());
        assertThat(sampleOfCampaignActivity1).isEqualTo(sampleOfCampaignActivity2);
        sampleOfCampaignActivity2.setId(2L);
        assertThat(sampleOfCampaignActivity1).isNotEqualTo(sampleOfCampaignActivity2);
        sampleOfCampaignActivity1.setId(null);
        assertThat(sampleOfCampaignActivity1).isNotEqualTo(sampleOfCampaignActivity2);
    }
}
