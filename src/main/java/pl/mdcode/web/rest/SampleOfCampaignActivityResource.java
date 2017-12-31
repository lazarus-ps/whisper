package pl.mdcode.web.rest;

import com.codahale.metrics.annotation.Timed;
import pl.mdcode.domain.SampleOfCampaignActivity;

import pl.mdcode.repository.SampleOfCampaignActivityRepository;
import pl.mdcode.web.rest.errors.BadRequestAlertException;
import pl.mdcode.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing SampleOfCampaignActivity.
 */
@RestController
@RequestMapping("/api")
public class SampleOfCampaignActivityResource {

    private final Logger log = LoggerFactory.getLogger(SampleOfCampaignActivityResource.class);

    private static final String ENTITY_NAME = "sampleOfCampaignActivity";

    private final SampleOfCampaignActivityRepository sampleOfCampaignActivityRepository;

    public SampleOfCampaignActivityResource(SampleOfCampaignActivityRepository sampleOfCampaignActivityRepository) {
        this.sampleOfCampaignActivityRepository = sampleOfCampaignActivityRepository;
    }

    /**
     * POST  /sample-of-campaign-activities : Create a new sampleOfCampaignActivity.
     *
     * @param sampleOfCampaignActivity the sampleOfCampaignActivity to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sampleOfCampaignActivity, or with status 400 (Bad Request) if the sampleOfCampaignActivity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sample-of-campaign-activities")
    @Timed
    public ResponseEntity<SampleOfCampaignActivity> createSampleOfCampaignActivity(@Valid @RequestBody SampleOfCampaignActivity sampleOfCampaignActivity) throws URISyntaxException {
        log.debug("REST request to save SampleOfCampaignActivity : {}", sampleOfCampaignActivity);
        if (sampleOfCampaignActivity.getId() != null) {
            throw new BadRequestAlertException("A new sampleOfCampaignActivity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SampleOfCampaignActivity result = sampleOfCampaignActivityRepository.save(sampleOfCampaignActivity);
        return ResponseEntity.created(new URI("/api/sample-of-campaign-activities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sample-of-campaign-activities : Updates an existing sampleOfCampaignActivity.
     *
     * @param sampleOfCampaignActivity the sampleOfCampaignActivity to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sampleOfCampaignActivity,
     * or with status 400 (Bad Request) if the sampleOfCampaignActivity is not valid,
     * or with status 500 (Internal Server Error) if the sampleOfCampaignActivity couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sample-of-campaign-activities")
    @Timed
    public ResponseEntity<SampleOfCampaignActivity> updateSampleOfCampaignActivity(@Valid @RequestBody SampleOfCampaignActivity sampleOfCampaignActivity) throws URISyntaxException {
        log.debug("REST request to update SampleOfCampaignActivity : {}", sampleOfCampaignActivity);
        if (sampleOfCampaignActivity.getId() == null) {
            return createSampleOfCampaignActivity(sampleOfCampaignActivity);
        }
        SampleOfCampaignActivity result = sampleOfCampaignActivityRepository.save(sampleOfCampaignActivity);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, sampleOfCampaignActivity.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sample-of-campaign-activities : get all the sampleOfCampaignActivities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of sampleOfCampaignActivities in body
     */
    @GetMapping("/sample-of-campaign-activities")
    @Timed
    public List<SampleOfCampaignActivity> getAllSampleOfCampaignActivities() {
        log.debug("REST request to get all SampleOfCampaignActivities");
        return sampleOfCampaignActivityRepository.findAll();
        }

    /**
     * GET  /sample-of-campaign-activities/:id : get the "id" sampleOfCampaignActivity.
     *
     * @param id the id of the sampleOfCampaignActivity to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sampleOfCampaignActivity, or with status 404 (Not Found)
     */
    @GetMapping("/sample-of-campaign-activities/{id}")
    @Timed
    public ResponseEntity<SampleOfCampaignActivity> getSampleOfCampaignActivity(@PathVariable Long id) {
        log.debug("REST request to get SampleOfCampaignActivity : {}", id);
        SampleOfCampaignActivity sampleOfCampaignActivity = sampleOfCampaignActivityRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(sampleOfCampaignActivity));
    }

    /**
     * DELETE  /sample-of-campaign-activities/:id : delete the "id" sampleOfCampaignActivity.
     *
     * @param id the id of the sampleOfCampaignActivity to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sample-of-campaign-activities/{id}")
    @Timed
    public ResponseEntity<Void> deleteSampleOfCampaignActivity(@PathVariable Long id) {
        log.debug("REST request to delete SampleOfCampaignActivity : {}", id);
        sampleOfCampaignActivityRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
