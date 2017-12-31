package pl.mdcode.web.rest;

import com.codahale.metrics.annotation.Timed;
import pl.mdcode.domain.Principle;

import pl.mdcode.repository.PrincipleRepository;
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
 * REST controller for managing Principle.
 */
@RestController
@RequestMapping("/api")
public class PrincipleResource {

    private final Logger log = LoggerFactory.getLogger(PrincipleResource.class);

    private static final String ENTITY_NAME = "principle";

    private final PrincipleRepository principleRepository;

    public PrincipleResource(PrincipleRepository principleRepository) {
        this.principleRepository = principleRepository;
    }

    /**
     * POST  /principles : Create a new principle.
     *
     * @param principle the principle to create
     * @return the ResponseEntity with status 201 (Created) and with body the new principle, or with status 400 (Bad Request) if the principle has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/principles")
    @Timed
    public ResponseEntity<Principle> createPrinciple(@Valid @RequestBody Principle principle) throws URISyntaxException {
        log.debug("REST request to save Principle : {}", principle);
        if (principle.getId() != null) {
            throw new BadRequestAlertException("A new principle cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Principle result = principleRepository.save(principle);
        return ResponseEntity.created(new URI("/api/principles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /principles : Updates an existing principle.
     *
     * @param principle the principle to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated principle,
     * or with status 400 (Bad Request) if the principle is not valid,
     * or with status 500 (Internal Server Error) if the principle couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/principles")
    @Timed
    public ResponseEntity<Principle> updatePrinciple(@Valid @RequestBody Principle principle) throws URISyntaxException {
        log.debug("REST request to update Principle : {}", principle);
        if (principle.getId() == null) {
            return createPrinciple(principle);
        }
        Principle result = principleRepository.save(principle);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, principle.getId().toString()))
            .body(result);
    }

    /**
     * GET  /principles : get all the principles.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of principles in body
     */
    @GetMapping("/principles")
    @Timed
    public List<Principle> getAllPrinciples() {
        log.debug("REST request to get all Principles");
        return principleRepository.findAll();
        }

    /**
     * GET  /principles/:id : get the "id" principle.
     *
     * @param id the id of the principle to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the principle, or with status 404 (Not Found)
     */
    @GetMapping("/principles/{id}")
    @Timed
    public ResponseEntity<Principle> getPrinciple(@PathVariable Long id) {
        log.debug("REST request to get Principle : {}", id);
        Principle principle = principleRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(principle));
    }

    /**
     * DELETE  /principles/:id : delete the "id" principle.
     *
     * @param id the id of the principle to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/principles/{id}")
    @Timed
    public ResponseEntity<Void> deletePrinciple(@PathVariable Long id) {
        log.debug("REST request to delete Principle : {}", id);
        principleRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
