package pl.mdcode.web.rest;

import com.codahale.metrics.annotation.Timed;
import pl.mdcode.domain.SampleOfAgentActivity;

import pl.mdcode.repository.SampleOfAgentActivityRepository;
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
 * REST controller for managing SampleOfAgentActivity.
 */
@RestController
@RequestMapping("/api")
public class SampleOfAgentActivityResource {

    private final Logger log = LoggerFactory.getLogger(SampleOfAgentActivityResource.class);

    private static final String ENTITY_NAME = "sampleOfAgentActivity";

    private final SampleOfAgentActivityRepository sampleOfAgentActivityRepository;

    public SampleOfAgentActivityResource(SampleOfAgentActivityRepository sampleOfAgentActivityRepository) {
        this.sampleOfAgentActivityRepository = sampleOfAgentActivityRepository;
    }

    /**
     * POST  /sample-of-agent-activities : Create a new sampleOfAgentActivity.
     *
     * @param sampleOfAgentActivity the sampleOfAgentActivity to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sampleOfAgentActivity, or with status 400 (Bad Request) if the sampleOfAgentActivity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sample-of-agent-activities")
    @Timed
    public ResponseEntity<SampleOfAgentActivity> createSampleOfAgentActivity(@Valid @RequestBody SampleOfAgentActivity sampleOfAgentActivity) throws URISyntaxException {
        log.debug("REST request to save SampleOfAgentActivity : {}", sampleOfAgentActivity);
        if (sampleOfAgentActivity.getId() != null) {
            throw new BadRequestAlertException("A new sampleOfAgentActivity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SampleOfAgentActivity result = sampleOfAgentActivityRepository.save(sampleOfAgentActivity);
        return ResponseEntity.created(new URI("/api/sample-of-agent-activities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sample-of-agent-activities : Updates an existing sampleOfAgentActivity.
     *
     * @param sampleOfAgentActivity the sampleOfAgentActivity to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sampleOfAgentActivity,
     * or with status 400 (Bad Request) if the sampleOfAgentActivity is not valid,
     * or with status 500 (Internal Server Error) if the sampleOfAgentActivity couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sample-of-agent-activities")
    @Timed
    public ResponseEntity<SampleOfAgentActivity> updateSampleOfAgentActivity(@Valid @RequestBody SampleOfAgentActivity sampleOfAgentActivity) throws URISyntaxException {
        log.debug("REST request to update SampleOfAgentActivity : {}", sampleOfAgentActivity);
        if (sampleOfAgentActivity.getId() == null) {
            return createSampleOfAgentActivity(sampleOfAgentActivity);
        }
        SampleOfAgentActivity result = sampleOfAgentActivityRepository.save(sampleOfAgentActivity);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, sampleOfAgentActivity.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sample-of-agent-activities : get all the sampleOfAgentActivities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of sampleOfAgentActivities in body
     */
    @GetMapping("/sample-of-agent-activities")
    @Timed
    public List<SampleOfAgentActivity> getAllSampleOfAgentActivities() {
        log.debug("REST request to get all SampleOfAgentActivities");
        return sampleOfAgentActivityRepository.findAll();
        }

    /**
     * GET  /sample-of-agent-activities/:id : get the "id" sampleOfAgentActivity.
     *
     * @param id the id of the sampleOfAgentActivity to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sampleOfAgentActivity, or with status 404 (Not Found)
     */
    @GetMapping("/sample-of-agent-activities/{id}")
    @Timed
    public ResponseEntity<SampleOfAgentActivity> getSampleOfAgentActivity(@PathVariable Long id) {
        log.debug("REST request to get SampleOfAgentActivity : {}", id);
        SampleOfAgentActivity sampleOfAgentActivity = sampleOfAgentActivityRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(sampleOfAgentActivity));
    }

    /**
     * DELETE  /sample-of-agent-activities/:id : delete the "id" sampleOfAgentActivity.
     *
     * @param id the id of the sampleOfAgentActivity to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sample-of-agent-activities/{id}")
    @Timed
    public ResponseEntity<Void> deleteSampleOfAgentActivity(@PathVariable Long id) {
        log.debug("REST request to delete SampleOfAgentActivity : {}", id);
        sampleOfAgentActivityRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
