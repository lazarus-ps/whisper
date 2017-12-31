package pl.mdcode.web.rest;

import pl.mdcode.WhisperApp;

import pl.mdcode.domain.SubscriptionDetails;
import pl.mdcode.repository.SubscriptionDetailsRepository;
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
 * Test class for the SubscriptionDetailsResource REST controller.
 *
 * @see SubscriptionDetailsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WhisperApp.class)
public class SubscriptionDetailsResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCTIPRION = "AAAAAAAAAA";
    private static final String UPDATED_DESCTIPRION = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMBER_OF_CAMPAIGNS = 1;
    private static final Integer UPDATED_NUMBER_OF_CAMPAIGNS = 2;

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;

    @Autowired
    private SubscriptionDetailsRepository subscriptionDetailsRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSubscriptionDetailsMockMvc;

    private SubscriptionDetails subscriptionDetails;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SubscriptionDetailsResource subscriptionDetailsResource = new SubscriptionDetailsResource(subscriptionDetailsRepository);
        this.restSubscriptionDetailsMockMvc = MockMvcBuilders.standaloneSetup(subscriptionDetailsResource)
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
    public static SubscriptionDetails createEntity(EntityManager em) {
        SubscriptionDetails subscriptionDetails = new SubscriptionDetails()
            .name(DEFAULT_NAME)
            .desctiprion(DEFAULT_DESCTIPRION)
            .numberOfCampaigns(DEFAULT_NUMBER_OF_CAMPAIGNS)
            .price(DEFAULT_PRICE);
        return subscriptionDetails;
    }

    @Before
    public void initTest() {
        subscriptionDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createSubscriptionDetails() throws Exception {
        int databaseSizeBeforeCreate = subscriptionDetailsRepository.findAll().size();

        // Create the SubscriptionDetails
        restSubscriptionDetailsMockMvc.perform(post("/api/subscription-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subscriptionDetails)))
            .andExpect(status().isCreated());

        // Validate the SubscriptionDetails in the database
        List<SubscriptionDetails> subscriptionDetailsList = subscriptionDetailsRepository.findAll();
        assertThat(subscriptionDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        SubscriptionDetails testSubscriptionDetails = subscriptionDetailsList.get(subscriptionDetailsList.size() - 1);
        assertThat(testSubscriptionDetails.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSubscriptionDetails.getDesctiprion()).isEqualTo(DEFAULT_DESCTIPRION);
        assertThat(testSubscriptionDetails.getNumberOfCampaigns()).isEqualTo(DEFAULT_NUMBER_OF_CAMPAIGNS);
        assertThat(testSubscriptionDetails.getPrice()).isEqualTo(DEFAULT_PRICE);
    }

    @Test
    @Transactional
    public void createSubscriptionDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = subscriptionDetailsRepository.findAll().size();

        // Create the SubscriptionDetails with an existing ID
        subscriptionDetails.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSubscriptionDetailsMockMvc.perform(post("/api/subscription-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subscriptionDetails)))
            .andExpect(status().isBadRequest());

        // Validate the SubscriptionDetails in the database
        List<SubscriptionDetails> subscriptionDetailsList = subscriptionDetailsRepository.findAll();
        assertThat(subscriptionDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = subscriptionDetailsRepository.findAll().size();
        // set the field null
        subscriptionDetails.setName(null);

        // Create the SubscriptionDetails, which fails.

        restSubscriptionDetailsMockMvc.perform(post("/api/subscription-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subscriptionDetails)))
            .andExpect(status().isBadRequest());

        List<SubscriptionDetails> subscriptionDetailsList = subscriptionDetailsRepository.findAll();
        assertThat(subscriptionDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDesctiprionIsRequired() throws Exception {
        int databaseSizeBeforeTest = subscriptionDetailsRepository.findAll().size();
        // set the field null
        subscriptionDetails.setDesctiprion(null);

        // Create the SubscriptionDetails, which fails.

        restSubscriptionDetailsMockMvc.perform(post("/api/subscription-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subscriptionDetails)))
            .andExpect(status().isBadRequest());

        List<SubscriptionDetails> subscriptionDetailsList = subscriptionDetailsRepository.findAll();
        assertThat(subscriptionDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumberOfCampaignsIsRequired() throws Exception {
        int databaseSizeBeforeTest = subscriptionDetailsRepository.findAll().size();
        // set the field null
        subscriptionDetails.setNumberOfCampaigns(null);

        // Create the SubscriptionDetails, which fails.

        restSubscriptionDetailsMockMvc.perform(post("/api/subscription-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subscriptionDetails)))
            .andExpect(status().isBadRequest());

        List<SubscriptionDetails> subscriptionDetailsList = subscriptionDetailsRepository.findAll();
        assertThat(subscriptionDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = subscriptionDetailsRepository.findAll().size();
        // set the field null
        subscriptionDetails.setPrice(null);

        // Create the SubscriptionDetails, which fails.

        restSubscriptionDetailsMockMvc.perform(post("/api/subscription-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subscriptionDetails)))
            .andExpect(status().isBadRequest());

        List<SubscriptionDetails> subscriptionDetailsList = subscriptionDetailsRepository.findAll();
        assertThat(subscriptionDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSubscriptionDetails() throws Exception {
        // Initialize the database
        subscriptionDetailsRepository.saveAndFlush(subscriptionDetails);

        // Get all the subscriptionDetailsList
        restSubscriptionDetailsMockMvc.perform(get("/api/subscription-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subscriptionDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].desctiprion").value(hasItem(DEFAULT_DESCTIPRION.toString())))
            .andExpect(jsonPath("$.[*].numberOfCampaigns").value(hasItem(DEFAULT_NUMBER_OF_CAMPAIGNS)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())));
    }

    @Test
    @Transactional
    public void getSubscriptionDetails() throws Exception {
        // Initialize the database
        subscriptionDetailsRepository.saveAndFlush(subscriptionDetails);

        // Get the subscriptionDetails
        restSubscriptionDetailsMockMvc.perform(get("/api/subscription-details/{id}", subscriptionDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(subscriptionDetails.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.desctiprion").value(DEFAULT_DESCTIPRION.toString()))
            .andExpect(jsonPath("$.numberOfCampaigns").value(DEFAULT_NUMBER_OF_CAMPAIGNS))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSubscriptionDetails() throws Exception {
        // Get the subscriptionDetails
        restSubscriptionDetailsMockMvc.perform(get("/api/subscription-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSubscriptionDetails() throws Exception {
        // Initialize the database
        subscriptionDetailsRepository.saveAndFlush(subscriptionDetails);
        int databaseSizeBeforeUpdate = subscriptionDetailsRepository.findAll().size();

        // Update the subscriptionDetails
        SubscriptionDetails updatedSubscriptionDetails = subscriptionDetailsRepository.findOne(subscriptionDetails.getId());
        updatedSubscriptionDetails
            .name(UPDATED_NAME)
            .desctiprion(UPDATED_DESCTIPRION)
            .numberOfCampaigns(UPDATED_NUMBER_OF_CAMPAIGNS)
            .price(UPDATED_PRICE);

        restSubscriptionDetailsMockMvc.perform(put("/api/subscription-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSubscriptionDetails)))
            .andExpect(status().isOk());

        // Validate the SubscriptionDetails in the database
        List<SubscriptionDetails> subscriptionDetailsList = subscriptionDetailsRepository.findAll();
        assertThat(subscriptionDetailsList).hasSize(databaseSizeBeforeUpdate);
        SubscriptionDetails testSubscriptionDetails = subscriptionDetailsList.get(subscriptionDetailsList.size() - 1);
        assertThat(testSubscriptionDetails.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSubscriptionDetails.getDesctiprion()).isEqualTo(UPDATED_DESCTIPRION);
        assertThat(testSubscriptionDetails.getNumberOfCampaigns()).isEqualTo(UPDATED_NUMBER_OF_CAMPAIGNS);
        assertThat(testSubscriptionDetails.getPrice()).isEqualTo(UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void updateNonExistingSubscriptionDetails() throws Exception {
        int databaseSizeBeforeUpdate = subscriptionDetailsRepository.findAll().size();

        // Create the SubscriptionDetails

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSubscriptionDetailsMockMvc.perform(put("/api/subscription-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subscriptionDetails)))
            .andExpect(status().isCreated());

        // Validate the SubscriptionDetails in the database
        List<SubscriptionDetails> subscriptionDetailsList = subscriptionDetailsRepository.findAll();
        assertThat(subscriptionDetailsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSubscriptionDetails() throws Exception {
        // Initialize the database
        subscriptionDetailsRepository.saveAndFlush(subscriptionDetails);
        int databaseSizeBeforeDelete = subscriptionDetailsRepository.findAll().size();

        // Get the subscriptionDetails
        restSubscriptionDetailsMockMvc.perform(delete("/api/subscription-details/{id}", subscriptionDetails.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SubscriptionDetails> subscriptionDetailsList = subscriptionDetailsRepository.findAll();
        assertThat(subscriptionDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubscriptionDetails.class);
        SubscriptionDetails subscriptionDetails1 = new SubscriptionDetails();
        subscriptionDetails1.setId(1L);
        SubscriptionDetails subscriptionDetails2 = new SubscriptionDetails();
        subscriptionDetails2.setId(subscriptionDetails1.getId());
        assertThat(subscriptionDetails1).isEqualTo(subscriptionDetails2);
        subscriptionDetails2.setId(2L);
        assertThat(subscriptionDetails1).isNotEqualTo(subscriptionDetails2);
        subscriptionDetails1.setId(null);
        assertThat(subscriptionDetails1).isNotEqualTo(subscriptionDetails2);
    }
}
