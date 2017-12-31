package pl.mdcode.web.rest;

import com.codahale.metrics.annotation.Timed;
import pl.mdcode.domain.AgentRate;

import pl.mdcode.repository.AgentRateRepository;
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
 * REST controller for managing AgentRate.
 */
@RestController
@RequestMapping("/api")
public class AgentRateResource {

    private final Logger log = LoggerFactory.getLogger(AgentRateResource.class);

    private static final String ENTITY_NAME = "agentRate";

    private final AgentRateRepository agentRateRepository;

    public AgentRateResource(AgentRateRepository agentRateRepository) {
        this.agentRateRepository = agentRateRepository;
    }

    /**
     * POST  /agent-rates : Create a new agentRate.
     *
     * @param agentRate the agentRate to create
     * @return the ResponseEntity with status 201 (Created) and with body the new agentRate, or with status 400 (Bad Request) if the agentRate has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/agent-rates")
    @Timed
    public ResponseEntity<AgentRate> createAgentRate(@Valid @RequestBody AgentRate agentRate) throws URISyntaxException {
        log.debug("REST request to save AgentRate : {}", agentRate);
        if (agentRate.getId() != null) {
            throw new BadRequestAlertException("A new agentRate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AgentRate result = agentRateRepository.save(agentRate);
        return ResponseEntity.created(new URI("/api/agent-rates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /agent-rates : Updates an existing agentRate.
     *
     * @param agentRate the agentRate to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated agentRate,
     * or with status 400 (Bad Request) if the agentRate is not valid,
     * or with status 500 (Internal Server Error) if the agentRate couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/agent-rates")
    @Timed
    public ResponseEntity<AgentRate> updateAgentRate(@Valid @RequestBody AgentRate agentRate) throws URISyntaxException {
        log.debug("REST request to update AgentRate : {}", agentRate);
        if (agentRate.getId() == null) {
            return createAgentRate(agentRate);
        }
        AgentRate result = agentRateRepository.save(agentRate);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, agentRate.getId().toString()))
            .body(result);
    }

    /**
     * GET  /agent-rates : get all the agentRates.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of agentRates in body
     */
    @GetMapping("/agent-rates")
    @Timed
    public List<AgentRate> getAllAgentRates() {
        log.debug("REST request to get all AgentRates");
        return agentRateRepository.findAll();
        }

    /**
     * GET  /agent-rates/:id : get the "id" agentRate.
     *
     * @param id the id of the agentRate to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the agentRate, or with status 404 (Not Found)
     */
    @GetMapping("/agent-rates/{id}")
    @Timed
    public ResponseEntity<AgentRate> getAgentRate(@PathVariable Long id) {
        log.debug("REST request to get AgentRate : {}", id);
        AgentRate agentRate = agentRateRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(agentRate));
    }

    /**
     * DELETE  /agent-rates/:id : delete the "id" agentRate.
     *
     * @param id the id of the agentRate to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/agent-rates/{id}")
    @Timed
    public ResponseEntity<Void> deleteAgentRate(@PathVariable Long id) {
        log.debug("REST request to delete AgentRate : {}", id);
        agentRateRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
