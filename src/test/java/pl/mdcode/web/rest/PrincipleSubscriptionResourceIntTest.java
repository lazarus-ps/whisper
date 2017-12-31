package pl.mdcode.web.rest;

import pl.mdcode.WhisperApp;

import pl.mdcode.domain.PrincipleSubscription;
import pl.mdcode.repository.PrincipleSubscriptionRepository;
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

import pl.mdcode.domain.enumeration.PAYMENT_STATUS;
/**
 * Test class for the PrincipleSubscriptionResource REST controller.
 *
 * @see PrincipleSubscriptionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WhisperApp.class)
public class PrincipleSubscriptionResourceIntTest {

    private static final String DEFAULT_PAYMENT_TOKEN = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_TOKEN = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final PAYMENT_STATUS DEFAULT_PAYMENT_STATUS = PAYMENT_STATUS.DONE;
    private static final PAYMENT_STATUS UPDATED_PAYMENT_STATUS = PAYMENT_STATUS.NOTDONE;

    @Autowired
    private PrincipleSubscriptionRepository principleSubscriptionRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPrincipleSubscriptionMockMvc;

    private PrincipleSubscription principleSubscription;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PrincipleSubscriptionResource principleSubscriptionResource = new PrincipleSubscriptionResource(principleSubscriptionRepository);
        this.restPrincipleSubscriptionMockMvc = MockMvcBuilders.standaloneSetup(principleSubscriptionResource)
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
    public static PrincipleSubscription createEntity(EntityManager em) {
        PrincipleSubscription principleSubscription = new PrincipleSubscription()
            .paymentToken(DEFAULT_PAYMENT_TOKEN)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .paymentStatus(DEFAULT_PAYMENT_STATUS);
        return principleSubscription;
    }

    @Before
    public void initTest() {
        principleSubscription = createEntity(em);
    }

    @Test
    @Transactional
    public void createPrincipleSubscription() throws Exception {
        int databaseSizeBeforeCreate = principleSubscriptionRepository.findAll().size();

        // Create the PrincipleSubscription
        restPrincipleSubscriptionMockMvc.perform(post("/api/principle-subscriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(principleSubscription)))
            .andExpect(status().isCreated());

        // Validate the PrincipleSubscription in the database
        List<PrincipleSubscription> principleSubscriptionList = principleSubscriptionRepository.findAll();
        assertThat(principleSubscriptionList).hasSize(databaseSizeBeforeCreate + 1);
        PrincipleSubscription testPrincipleSubscription = principleSubscriptionList.get(principleSubscriptionList.size() - 1);
        assertThat(testPrincipleSubscription.getPaymentToken()).isEqualTo(DEFAULT_PAYMENT_TOKEN);
        assertThat(testPrincipleSubscription.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testPrincipleSubscription.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testPrincipleSubscription.getPaymentStatus()).isEqualTo(DEFAULT_PAYMENT_STATUS);
    }

    @Test
    @Transactional
    public void createPrincipleSubscriptionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = principleSubscriptionRepository.findAll().size();

        // Create the PrincipleSubscription with an existing ID
        principleSubscription.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrincipleSubscriptionMockMvc.perform(post("/api/principle-subscriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(principleSubscription)))
            .andExpect(status().isBadRequest());

        // Validate the PrincipleSubscription in the database
        List<PrincipleSubscription> principleSubscriptionList = principleSubscriptionRepository.findAll();
        assertThat(principleSubscriptionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkPaymentTokenIsRequired() throws Exception {
        int databaseSizeBeforeTest = principleSubscriptionRepository.findAll().size();
        // set the field null
        principleSubscription.setPaymentToken(null);

        // Create the PrincipleSubscription, which fails.

        restPrincipleSubscriptionMockMvc.perform(post("/api/principle-subscriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(principleSubscription)))
            .andExpect(status().isBadRequest());

        List<PrincipleSubscription> principleSubscriptionList = principleSubscriptionRepository.findAll();
        assertThat(principleSubscriptionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = principleSubscriptionRepository.findAll().size();
        // set the field null
        principleSubscription.setStartDate(null);

        // Create the PrincipleSubscription, which fails.

        restPrincipleSubscriptionMockMvc.perform(post("/api/principle-subscriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(principleSubscription)))
            .andExpect(status().isBadRequest());

        List<PrincipleSubscription> principleSubscriptionList = principleSubscriptionRepository.findAll();
        assertThat(principleSubscriptionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = principleSubscriptionRepository.findAll().size();
        // set the field null
        principleSubscription.setEndDate(null);

        // Create the PrincipleSubscription, which fails.

        restPrincipleSubscriptionMockMvc.perform(post("/api/principle-subscriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(principleSubscription)))
            .andExpect(status().isBadRequest());

        List<PrincipleSubscription> principleSubscriptionList = principleSubscriptionRepository.findAll();
        assertThat(principleSubscriptionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPaymentStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = principleSubscriptionRepository.findAll().size();
        // set the field null
        principleSubscription.setPaymentStatus(null);

        // Create the PrincipleSubscription, which fails.

        restPrincipleSubscriptionMockMvc.perform(post("/api/principle-subscriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(principleSubscription)))
            .andExpect(status().isBadRequest());

        List<PrincipleSubscription> principleSubscriptionList = principleSubscriptionRepository.findAll();
        assertThat(principleSubscriptionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPrincipleSubscriptions() throws Exception {
        // Initialize the database
        principleSubscriptionRepository.saveAndFlush(principleSubscription);

        // Get all the principleSubscriptionList
        restPrincipleSubscriptionMockMvc.perform(get("/api/principle-subscriptions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(principleSubscription.getId().intValue())))
            .andExpect(jsonPath("$.[*].paymentToken").value(hasItem(DEFAULT_PAYMENT_TOKEN.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].paymentStatus").value(hasItem(DEFAULT_PAYMENT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getPrincipleSubscription() throws Exception {
        // Initialize the database
        principleSubscriptionRepository.saveAndFlush(principleSubscription);

        // Get the principleSubscription
        restPrincipleSubscriptionMockMvc.perform(get("/api/principle-subscriptions/{id}", principleSubscription.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(principleSubscription.getId().intValue()))
            .andExpect(jsonPath("$.paymentToken").value(DEFAULT_PAYMENT_TOKEN.toString()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.paymentStatus").value(DEFAULT_PAYMENT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPrincipleSubscription() throws Exception {
        // Get the principleSubscription
        restPrincipleSubscriptionMockMvc.perform(get("/api/principle-subscriptions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePrincipleSubscription() throws Exception {
        // Initialize the database
        principleSubscriptionRepository.saveAndFlush(principleSubscription);
        int databaseSizeBeforeUpdate = principleSubscriptionRepository.findAll().size();

        // Update the principleSubscription
        PrincipleSubscription updatedPrincipleSubscription = principleSubscriptionRepository.findOne(principleSubscription.getId());
        updatedPrincipleSubscription
            .paymentToken(UPDATED_PAYMENT_TOKEN)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .paymentStatus(UPDATED_PAYMENT_STATUS);

        restPrincipleSubscriptionMockMvc.perform(put("/api/principle-subscriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPrincipleSubscription)))
            .andExpect(status().isOk());

        // Validate the PrincipleSubscription in the database
        List<PrincipleSubscription> principleSubscriptionList = principleSubscriptionRepository.findAll();
        assertThat(principleSubscriptionList).hasSize(databaseSizeBeforeUpdate);
        PrincipleSubscription testPrincipleSubscription = principleSubscriptionList.get(principleSubscriptionList.size() - 1);
        assertThat(testPrincipleSubscription.getPaymentToken()).isEqualTo(UPDATED_PAYMENT_TOKEN);
        assertThat(testPrincipleSubscription.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testPrincipleSubscription.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testPrincipleSubscription.getPaymentStatus()).isEqualTo(UPDATED_PAYMENT_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingPrincipleSubscription() throws Exception {
        int databaseSizeBeforeUpdate = principleSubscriptionRepository.findAll().size();

        // Create the PrincipleSubscription

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPrincipleSubscriptionMockMvc.perform(put("/api/principle-subscriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(principleSubscription)))
            .andExpect(status().isCreated());

        // Validate the PrincipleSubscription in the database
        List<PrincipleSubscription> principleSubscriptionList = principleSubscriptionRepository.findAll();
        assertThat(principleSubscriptionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePrincipleSubscription() throws Exception {
        // Initialize the database
        principleSubscriptionRepository.saveAndFlush(principleSubscription);
        int databaseSizeBeforeDelete = principleSubscriptionRepository.findAll().size();

        // Get the principleSubscription
        restPrincipleSubscriptionMockMvc.perform(delete("/api/principle-subscriptions/{id}", principleSubscription.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PrincipleSubscription> principleSubscriptionList = principleSubscriptionRepository.findAll();
        assertThat(principleSubscriptionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PrincipleSubscription.class);
        PrincipleSubscription principleSubscription1 = new PrincipleSubscription();
        principleSubscription1.setId(1L);
        PrincipleSubscription principleSubscription2 = new PrincipleSubscription();
        principleSubscription2.setId(principleSubscription1.getId());
        assertThat(principleSubscription1).isEqualTo(principleSubscription2);
        principleSubscription2.setId(2L);
        assertThat(principleSubscription1).isNotEqualTo(principleSubscription2);
        principleSubscription1.setId(null);
        assertThat(principleSubscription1).isNotEqualTo(principleSubscription2);
    }
}
