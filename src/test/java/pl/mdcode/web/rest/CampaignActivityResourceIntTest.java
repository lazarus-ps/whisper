package pl.mdcode.web.rest;

import pl.mdcode.WhisperApp;

import pl.mdcode.domain.CampaignActivity;
import pl.mdcode.domain.Campaign;
import pl.mdcode.repository.CampaignActivityRepository;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static pl.mdcode.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import pl.mdcode.domain.enumeration.CAMPAING_ACTIVITY_STATUS;
import pl.mdcode.domain.enumeration.ACTIVITY_TYPE;
/**
 * Test class for the CampaignActivityResource REST controller.
 *
 * @see CampaignActivityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WhisperApp.class)
public class CampaignActivityResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final CAMPAING_ACTIVITY_STATUS DEFAULT_CAMPAIGN_ACTIVITY = CAMPAING_ACTIVITY_STATUS.NONE;
    private static final CAMPAING_ACTIVITY_STATUS UPDATED_CAMPAIGN_ACTIVITY = CAMPAING_ACTIVITY_STATUS.POSITIVE;

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_LINK_TO_ACTIVITY = "AAAAAAAAAA";
    private static final String UPDATED_LINK_TO_ACTIVITY = "BBBBBBBBBB";

    private static final String DEFAULT_NICK_IN_ACTIVITY = "AAAAAAAAAA";
    private static final String UPDATED_NICK_IN_ACTIVITY = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMBER_OF_LINKS_TO_CAMPAIGN_PAGES = 1;
    private static final Integer UPDATED_NUMBER_OF_LINKS_TO_CAMPAIGN_PAGES = 2;

    private static final ACTIVITY_TYPE DEFAULT_ACTIVITY_TYPE = ACTIVITY_TYPE.PR;
    private static final ACTIVITY_TYPE UPDATED_ACTIVITY_TYPE = ACTIVITY_TYPE.NEUTRAL;

    @Autowired
    private CampaignActivityRepository campaignActivityRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCampaignActivityMockMvc;

    private CampaignActivity campaignActivity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CampaignActivityResource campaignActivityResource = new CampaignActivityResource(campaignActivityRepository);
        this.restCampaignActivityMockMvc = MockMvcBuilders.standaloneSetup(campaignActivityResource)
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
    public static CampaignActivity createEntity(EntityManager em) {
        CampaignActivity campaignActivity = new CampaignActivity()
            .name(DEFAULT_NAME)
            .url(DEFAULT_URL)
            .creationDate(DEFAULT_CREATION_DATE)
            .campaignActivity(DEFAULT_CAMPAIGN_ACTIVITY)
            .text(DEFAULT_TEXT)
            .linkToActivity(DEFAULT_LINK_TO_ACTIVITY)
            .nickINActivity(DEFAULT_NICK_IN_ACTIVITY)
            .numberOfLinksToCampaignPages(DEFAULT_NUMBER_OF_LINKS_TO_CAMPAIGN_PAGES)
            .activityType(DEFAULT_ACTIVITY_TYPE);
        // Add required entity
        Campaign campaign = CampaignResourceIntTest.createEntity(em);
        em.persist(campaign);
        em.flush();
        campaignActivity.setCampaign(campaign);
        return campaignActivity;
    }

    @Before
    public void initTest() {
        campaignActivity = createEntity(em);
    }

    @Test
    @Transactional
    public void createCampaignActivity() throws Exception {
        int databaseSizeBeforeCreate = campaignActivityRepository.findAll().size();

        // Create the CampaignActivity
        restCampaignActivityMockMvc.perform(post("/api/campaign-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(campaignActivity)))
            .andExpect(status().isCreated());

        // Validate the CampaignActivity in the database
        List<CampaignActivity> campaignActivityList = campaignActivityRepository.findAll();
        assertThat(campaignActivityList).hasSize(databaseSizeBeforeCreate + 1);
        CampaignActivity testCampaignActivity = campaignActivityList.get(campaignActivityList.size() - 1);
        assertThat(testCampaignActivity.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCampaignActivity.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testCampaignActivity.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testCampaignActivity.getCampaignActivity()).isEqualTo(DEFAULT_CAMPAIGN_ACTIVITY);
        assertThat(testCampaignActivity.getText()).isEqualTo(DEFAULT_TEXT);
        assertThat(testCampaignActivity.getLinkToActivity()).isEqualTo(DEFAULT_LINK_TO_ACTIVITY);
        assertThat(testCampaignActivity.getNickINActivity()).isEqualTo(DEFAULT_NICK_IN_ACTIVITY);
        assertThat(testCampaignActivity.getNumberOfLinksToCampaignPages()).isEqualTo(DEFAULT_NUMBER_OF_LINKS_TO_CAMPAIGN_PAGES);
        assertThat(testCampaignActivity.getActivityType()).isEqualTo(DEFAULT_ACTIVITY_TYPE);
    }

    @Test
    @Transactional
    public void createCampaignActivityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = campaignActivityRepository.findAll().size();

        // Create the CampaignActivity with an existing ID
        campaignActivity.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCampaignActivityMockMvc.perform(post("/api/campaign-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(campaignActivity)))
            .andExpect(status().isBadRequest());

        // Validate the CampaignActivity in the database
        List<CampaignActivity> campaignActivityList = campaignActivityRepository.findAll();
        assertThat(campaignActivityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = campaignActivityRepository.findAll().size();
        // set the field null
        campaignActivity.setName(null);

        // Create the CampaignActivity, which fails.

        restCampaignActivityMockMvc.perform(post("/api/campaign-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(campaignActivity)))
            .andExpect(status().isBadRequest());

        List<CampaignActivity> campaignActivityList = campaignActivityRepository.findAll();
        assertThat(campaignActivityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = campaignActivityRepository.findAll().size();
        // set the field null
        campaignActivity.setUrl(null);

        // Create the CampaignActivity, which fails.

        restCampaignActivityMockMvc.perform(post("/api/campaign-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(campaignActivity)))
            .andExpect(status().isBadRequest());

        List<CampaignActivity> campaignActivityList = campaignActivityRepository.findAll();
        assertThat(campaignActivityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = campaignActivityRepository.findAll().size();
        // set the field null
        campaignActivity.setCreationDate(null);

        // Create the CampaignActivity, which fails.

        restCampaignActivityMockMvc.perform(post("/api/campaign-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(campaignActivity)))
            .andExpect(status().isBadRequest());

        List<CampaignActivity> campaignActivityList = campaignActivityRepository.findAll();
        assertThat(campaignActivityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCampaignActivityIsRequired() throws Exception {
        int databaseSizeBeforeTest = campaignActivityRepository.findAll().size();
        // set the field null
        campaignActivity.setCampaignActivity(null);

        // Create the CampaignActivity, which fails.

        restCampaignActivityMockMvc.perform(post("/api/campaign-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(campaignActivity)))
            .andExpect(status().isBadRequest());

        List<CampaignActivity> campaignActivityList = campaignActivityRepository.findAll();
        assertThat(campaignActivityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTextIsRequired() throws Exception {
        int databaseSizeBeforeTest = campaignActivityRepository.findAll().size();
        // set the field null
        campaignActivity.setText(null);

        // Create the CampaignActivity, which fails.

        restCampaignActivityMockMvc.perform(post("/api/campaign-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(campaignActivity)))
            .andExpect(status().isBadRequest());

        List<CampaignActivity> campaignActivityList = campaignActivityRepository.findAll();
        assertThat(campaignActivityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLinkToActivityIsRequired() throws Exception {
        int databaseSizeBeforeTest = campaignActivityRepository.findAll().size();
        // set the field null
        campaignActivity.setLinkToActivity(null);

        // Create the CampaignActivity, which fails.

        restCampaignActivityMockMvc.perform(post("/api/campaign-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(campaignActivity)))
            .andExpect(status().isBadRequest());

        List<CampaignActivity> campaignActivityList = campaignActivityRepository.findAll();
        assertThat(campaignActivityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNickINActivityIsRequired() throws Exception {
        int databaseSizeBeforeTest = campaignActivityRepository.findAll().size();
        // set the field null
        campaignActivity.setNickINActivity(null);

        // Create the CampaignActivity, which fails.

        restCampaignActivityMockMvc.perform(post("/api/campaign-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(campaignActivity)))
            .andExpect(status().isBadRequest());

        List<CampaignActivity> campaignActivityList = campaignActivityRepository.findAll();
        assertThat(campaignActivityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumberOfLinksToCampaignPagesIsRequired() throws Exception {
        int databaseSizeBeforeTest = campaignActivityRepository.findAll().size();
        // set the field null
        campaignActivity.setNumberOfLinksToCampaignPages(null);

        // Create the CampaignActivity, which fails.

        restCampaignActivityMockMvc.perform(post("/api/campaign-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(campaignActivity)))
            .andExpect(status().isBadRequest());

        List<CampaignActivity> campaignActivityList = campaignActivityRepository.findAll();
        assertThat(campaignActivityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActivityTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = campaignActivityRepository.findAll().size();
        // set the field null
        campaignActivity.setActivityType(null);

        // Create the CampaignActivity, which fails.

        restCampaignActivityMockMvc.perform(post("/api/campaign-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(campaignActivity)))
            .andExpect(status().isBadRequest());

        List<CampaignActivity> campaignActivityList = campaignActivityRepository.findAll();
        assertThat(campaignActivityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCampaignActivities() throws Exception {
        // Initialize the database
        campaignActivityRepository.saveAndFlush(campaignActivity);

        // Get all the campaignActivityList
        restCampaignActivityMockMvc.perform(get("/api/campaign-activities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(campaignActivity.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].campaignActivity").value(hasItem(DEFAULT_CAMPAIGN_ACTIVITY.toString())))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT.toString())))
            .andExpect(jsonPath("$.[*].linkToActivity").value(hasItem(DEFAULT_LINK_TO_ACTIVITY.toString())))
            .andExpect(jsonPath("$.[*].nickINActivity").value(hasItem(DEFAULT_NICK_IN_ACTIVITY.toString())))
            .andExpect(jsonPath("$.[*].numberOfLinksToCampaignPages").value(hasItem(DEFAULT_NUMBER_OF_LINKS_TO_CAMPAIGN_PAGES)))
            .andExpect(jsonPath("$.[*].activityType").value(hasItem(DEFAULT_ACTIVITY_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getCampaignActivity() throws Exception {
        // Initialize the database
        campaignActivityRepository.saveAndFlush(campaignActivity);

        // Get the campaignActivity
        restCampaignActivityMockMvc.perform(get("/api/campaign-activities/{id}", campaignActivity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(campaignActivity.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.campaignActivity").value(DEFAULT_CAMPAIGN_ACTIVITY.toString()))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT.toString()))
            .andExpect(jsonPath("$.linkToActivity").value(DEFAULT_LINK_TO_ACTIVITY.toString()))
            .andExpect(jsonPath("$.nickINActivity").value(DEFAULT_NICK_IN_ACTIVITY.toString()))
            .andExpect(jsonPath("$.numberOfLinksToCampaignPages").value(DEFAULT_NUMBER_OF_LINKS_TO_CAMPAIGN_PAGES))
            .andExpect(jsonPath("$.activityType").value(DEFAULT_ACTIVITY_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCampaignActivity() throws Exception {
        // Get the campaignActivity
        restCampaignActivityMockMvc.perform(get("/api/campaign-activities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCampaignActivity() throws Exception {
        // Initialize the database
        campaignActivityRepository.saveAndFlush(campaignActivity);
        int databaseSizeBeforeUpdate = campaignActivityRepository.findAll().size();

        // Update the campaignActivity
        CampaignActivity updatedCampaignActivity = campaignActivityRepository.findOne(campaignActivity.getId());
        updatedCampaignActivity
            .name(UPDATED_NAME)
            .url(UPDATED_URL)
            .creationDate(UPDATED_CREATION_DATE)
            .campaignActivity(UPDATED_CAMPAIGN_ACTIVITY)
            .text(UPDATED_TEXT)
            .linkToActivity(UPDATED_LINK_TO_ACTIVITY)
            .nickINActivity(UPDATED_NICK_IN_ACTIVITY)
            .numberOfLinksToCampaignPages(UPDATED_NUMBER_OF_LINKS_TO_CAMPAIGN_PAGES)
            .activityType(UPDATED_ACTIVITY_TYPE);

        restCampaignActivityMockMvc.perform(put("/api/campaign-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCampaignActivity)))
            .andExpect(status().isOk());

        // Validate the CampaignActivity in the database
        List<CampaignActivity> campaignActivityList = campaignActivityRepository.findAll();
        assertThat(campaignActivityList).hasSize(databaseSizeBeforeUpdate);
        CampaignActivity testCampaignActivity = campaignActivityList.get(campaignActivityList.size() - 1);
        assertThat(testCampaignActivity.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCampaignActivity.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testCampaignActivity.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testCampaignActivity.getCampaignActivity()).isEqualTo(UPDATED_CAMPAIGN_ACTIVITY);
        assertThat(testCampaignActivity.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testCampaignActivity.getLinkToActivity()).isEqualTo(UPDATED_LINK_TO_ACTIVITY);
        assertThat(testCampaignActivity.getNickINActivity()).isEqualTo(UPDATED_NICK_IN_ACTIVITY);
        assertThat(testCampaignActivity.getNumberOfLinksToCampaignPages()).isEqualTo(UPDATED_NUMBER_OF_LINKS_TO_CAMPAIGN_PAGES);
        assertThat(testCampaignActivity.getActivityType()).isEqualTo(UPDATED_ACTIVITY_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingCampaignActivity() throws Exception {
        int databaseSizeBeforeUpdate = campaignActivityRepository.findAll().size();

        // Create the CampaignActivity

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCampaignActivityMockMvc.perform(put("/api/campaign-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(campaignActivity)))
            .andExpect(status().isCreated());

        // Validate the CampaignActivity in the database
        List<CampaignActivity> campaignActivityList = campaignActivityRepository.findAll();
        assertThat(campaignActivityList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCampaignActivity() throws Exception {
        // Initialize the database
        campaignActivityRepository.saveAndFlush(campaignActivity);
        int databaseSizeBeforeDelete = campaignActivityRepository.findAll().size();

        // Get the campaignActivity
        restCampaignActivityMockMvc.perform(delete("/api/campaign-activities/{id}", campaignActivity.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CampaignActivity> campaignActivityList = campaignActivityRepository.findAll();
        assertThat(campaignActivityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CampaignActivity.class);
        CampaignActivity campaignActivity1 = new CampaignActivity();
        campaignActivity1.setId(1L);
        CampaignActivity campaignActivity2 = new CampaignActivity();
        campaignActivity2.setId(campaignActivity1.getId());
        assertThat(campaignActivity1).isEqualTo(campaignActivity2);
        campaignActivity2.setId(2L);
        assertThat(campaignActivity1).isNotEqualTo(campaignActivity2);
        campaignActivity1.setId(null);
        assertThat(campaignActivity1).isNotEqualTo(campaignActivity2);
    }
}
