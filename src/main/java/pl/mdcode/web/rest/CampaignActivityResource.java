package pl.mdcode.web.rest;

import com.codahale.metrics.annotation.Timed;
import pl.mdcode.domain.CampaignActivity;

import pl.mdcode.repository.CampaignActivityRepository;
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
 * REST controller for managing CampaignActivity.
 */
@RestController
@RequestMapping("/api")
public class CampaignActivityResource {

    private final Logger log = LoggerFactory.getLogger(CampaignActivityResource.class);

    private static final String ENTITY_NAME = "campaignActivity";

    private final CampaignActivityRepository campaignActivityRepository;

    public CampaignActivityResource(CampaignActivityRepository campaignActivityRepository) {
        this.campaignActivityRepository = campaignActivityRepository;
    }

    /**
     * POST  /campaign-activities : Create a new campaignActivity.
     *
     * @param campaignActivity the campaignActivity to create
     * @return the ResponseEntity with status 201 (Created) and with body the new campaignActivity, or with status 400 (Bad Request) if the campaignActivity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/campaign-activities")
    @Timed
    public ResponseEntity<CampaignActivity> createCampaignActivity(@Valid @RequestBody CampaignActivity campaignActivity) throws URISyntaxException {
        log.debug("REST request to save CampaignActivity : {}", campaignActivity);
        if (campaignActivity.getId() != null) {
            throw new BadRequestAlertException("A new campaignActivity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CampaignActivity result = campaignActivityRepository.save(campaignActivity);
        return ResponseEntity.created(new URI("/api/campaign-activities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /campaign-activities : Updates an existing campaignActivity.
     *
     * @param campaignActivity the campaignActivity to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated campaignActivity,
     * or with status 400 (Bad Request) if the campaignActivity is not valid,
     * or with status 500 (Internal Server Error) if the campaignActivity couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/campaign-activities")
    @Timed
    public ResponseEntity<CampaignActivity> updateCampaignActivity(@Valid @RequestBody CampaignActivity campaignActivity) throws URISyntaxException {
        log.debug("REST request to update CampaignActivity : {}", campaignActivity);
        if (campaignActivity.getId() == null) {
            return createCampaignActivity(campaignActivity);
        }
        CampaignActivity result = campaignActivityRepository.save(campaignActivity);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, campaignActivity.getId().toString()))
            .body(result);
    }

    /**
     * GET  /campaign-activities : get all the campaignActivities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of campaignActivities in body
     */
    @GetMapping("/campaign-activities")
    @Timed
    public List<CampaignActivity> getAllCampaignActivities() {
        log.debug("REST request to get all CampaignActivities");
        return campaignActivityRepository.findAllWithEagerRelationships();
        }

    /**
     * GET  /campaign-activities/:id : get the "id" campaignActivity.
     *
     * @param id the id of the campaignActivity to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the campaignActivity, or with status 404 (Not Found)
     */
    @GetMapping("/campaign-activities/{id}")
    @Timed
    public ResponseEntity<CampaignActivity> getCampaignActivity(@PathVariable Long id) {
        log.debug("REST request to get CampaignActivity : {}", id);
        CampaignActivity campaignActivity = campaignActivityRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(campaignActivity));
    }

    /**
     * DELETE  /campaign-activities/:id : delete the "id" campaignActivity.
     *
     * @param id the id of the campaignActivity to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/campaign-activities/{id}")
    @Timed
    public ResponseEntity<Void> deleteCampaignActivity(@PathVariable Long id) {
        log.debug("REST request to delete CampaignActivity : {}", id);
        campaignActivityRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
