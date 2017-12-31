package pl.mdcode.web.rest;

import com.codahale.metrics.annotation.Timed;
import pl.mdcode.domain.SubscriptionDetails;

import pl.mdcode.repository.SubscriptionDetailsRepository;
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
 * REST controller for managing SubscriptionDetails.
 */
@RestController
@RequestMapping("/api")
public class SubscriptionDetailsResource {

    private final Logger log = LoggerFactory.getLogger(SubscriptionDetailsResource.class);

    private static final String ENTITY_NAME = "subscriptionDetails";

    private final SubscriptionDetailsRepository subscriptionDetailsRepository;

    public SubscriptionDetailsResource(SubscriptionDetailsRepository subscriptionDetailsRepository) {
        this.subscriptionDetailsRepository = subscriptionDetailsRepository;
    }

    /**
     * POST  /subscription-details : Create a new subscriptionDetails.
     *
     * @param subscriptionDetails the subscriptionDetails to create
     * @return the ResponseEntity with status 201 (Created) and with body the new subscriptionDetails, or with status 400 (Bad Request) if the subscriptionDetails has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/subscription-details")
    @Timed
    public ResponseEntity<SubscriptionDetails> createSubscriptionDetails(@Valid @RequestBody SubscriptionDetails subscriptionDetails) throws URISyntaxException {
        log.debug("REST request to save SubscriptionDetails : {}", subscriptionDetails);
        if (subscriptionDetails.getId() != null) {
            throw new BadRequestAlertException("A new subscriptionDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SubscriptionDetails result = subscriptionDetailsRepository.save(subscriptionDetails);
        return ResponseEntity.created(new URI("/api/subscription-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /subscription-details : Updates an existing subscriptionDetails.
     *
     * @param subscriptionDetails the subscriptionDetails to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated subscriptionDetails,
     * or with status 400 (Bad Request) if the subscriptionDetails is not valid,
     * or with status 500 (Internal Server Error) if the subscriptionDetails couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/subscription-details")
    @Timed
    public ResponseEntity<SubscriptionDetails> updateSubscriptionDetails(@Valid @RequestBody SubscriptionDetails subscriptionDetails) throws URISyntaxException {
        log.debug("REST request to update SubscriptionDetails : {}", subscriptionDetails);
        if (subscriptionDetails.getId() == null) {
            return createSubscriptionDetails(subscriptionDetails);
        }
        SubscriptionDetails result = subscriptionDetailsRepository.save(subscriptionDetails);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, subscriptionDetails.getId().toString()))
            .body(result);
    }

    /**
     * GET  /subscription-details : get all the subscriptionDetails.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of subscriptionDetails in body
     */
    @GetMapping("/subscription-details")
    @Timed
    public List<SubscriptionDetails> getAllSubscriptionDetails() {
        log.debug("REST request to get all SubscriptionDetails");
        return subscriptionDetailsRepository.findAll();
        }

    /**
     * GET  /subscription-details/:id : get the "id" subscriptionDetails.
     *
     * @param id the id of the subscriptionDetails to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the subscriptionDetails, or with status 404 (Not Found)
     */
    @GetMapping("/subscription-details/{id}")
    @Timed
    public ResponseEntity<SubscriptionDetails> getSubscriptionDetails(@PathVariable Long id) {
        log.debug("REST request to get SubscriptionDetails : {}", id);
        SubscriptionDetails subscriptionDetails = subscriptionDetailsRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(subscriptionDetails));
    }

    /**
     * DELETE  /subscription-details/:id : delete the "id" subscriptionDetails.
     *
     * @param id the id of the subscriptionDetails to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/subscription-details/{id}")
    @Timed
    public ResponseEntity<Void> deleteSubscriptionDetails(@PathVariable Long id) {
        log.debug("REST request to delete SubscriptionDetails : {}", id);
        subscriptionDetailsRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
