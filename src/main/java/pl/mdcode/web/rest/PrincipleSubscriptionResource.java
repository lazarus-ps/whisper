package pl.mdcode.web.rest;

import com.codahale.metrics.annotation.Timed;
import pl.mdcode.domain.PrincipleSubscription;

import pl.mdcode.repository.PrincipleSubscriptionRepository;
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
 * REST controller for managing PrincipleSubscription.
 */
@RestController
@RequestMapping("/api")
public class PrincipleSubscriptionResource {

    private final Logger log = LoggerFactory.getLogger(PrincipleSubscriptionResource.class);

    private static final String ENTITY_NAME = "principleSubscription";

    private final PrincipleSubscriptionRepository principleSubscriptionRepository;

    public PrincipleSubscriptionResource(PrincipleSubscriptionRepository principleSubscriptionRepository) {
        this.principleSubscriptionRepository = principleSubscriptionRepository;
    }

    /**
     * POST  /principle-subscriptions : Create a new principleSubscription.
     *
     * @param principleSubscription the principleSubscription to create
     * @return the ResponseEntity with status 201 (Created) and with body the new principleSubscription, or with status 400 (Bad Request) if the principleSubscription has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/principle-subscriptions")
    @Timed
    public ResponseEntity<PrincipleSubscription> createPrincipleSubscription(@Valid @RequestBody PrincipleSubscription principleSubscription) throws URISyntaxException {
        log.debug("REST request to save PrincipleSubscription : {}", principleSubscription);
        if (principleSubscription.getId() != null) {
            throw new BadRequestAlertException("A new principleSubscription cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PrincipleSubscription result = principleSubscriptionRepository.save(principleSubscription);
        return ResponseEntity.created(new URI("/api/principle-subscriptions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /principle-subscriptions : Updates an existing principleSubscription.
     *
     * @param principleSubscription the principleSubscription to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated principleSubscription,
     * or with status 400 (Bad Request) if the principleSubscription is not valid,
     * or with status 500 (Internal Server Error) if the principleSubscription couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/principle-subscriptions")
    @Timed
    public ResponseEntity<PrincipleSubscription> updatePrincipleSubscription(@Valid @RequestBody PrincipleSubscription principleSubscription) throws URISyntaxException {
        log.debug("REST request to update PrincipleSubscription : {}", principleSubscription);
        if (principleSubscription.getId() == null) {
            return createPrincipleSubscription(principleSubscription);
        }
        PrincipleSubscription result = principleSubscriptionRepository.save(principleSubscription);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, principleSubscription.getId().toString()))
            .body(result);
    }

    /**
     * GET  /principle-subscriptions : get all the principleSubscriptions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of principleSubscriptions in body
     */
    @GetMapping("/principle-subscriptions")
    @Timed
    public List<PrincipleSubscription> getAllPrincipleSubscriptions() {
        log.debug("REST request to get all PrincipleSubscriptions");
        return principleSubscriptionRepository.findAll();
        }

    /**
     * GET  /principle-subscriptions/:id : get the "id" principleSubscription.
     *
     * @param id the id of the principleSubscription to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the principleSubscription, or with status 404 (Not Found)
     */
    @GetMapping("/principle-subscriptions/{id}")
    @Timed
    public ResponseEntity<PrincipleSubscription> getPrincipleSubscription(@PathVariable Long id) {
        log.debug("REST request to get PrincipleSubscription : {}", id);
        PrincipleSubscription principleSubscription = principleSubscriptionRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(principleSubscription));
    }

    /**
     * DELETE  /principle-subscriptions/:id : delete the "id" principleSubscription.
     *
     * @param id the id of the principleSubscription to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/principle-subscriptions/{id}")
    @Timed
    public ResponseEntity<Void> deletePrincipleSubscription(@PathVariable Long id) {
        log.debug("REST request to delete PrincipleSubscription : {}", id);
        principleSubscriptionRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
