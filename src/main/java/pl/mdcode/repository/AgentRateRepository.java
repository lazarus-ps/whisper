package pl.mdcode.repository;

import pl.mdcode.domain.AgentRate;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the AgentRate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AgentRateRepository extends JpaRepository<AgentRate, Long> {

}
