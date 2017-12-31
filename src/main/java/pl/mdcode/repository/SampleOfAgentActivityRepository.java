package pl.mdcode.repository;

import pl.mdcode.domain.SampleOfAgentActivity;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the SampleOfAgentActivity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SampleOfAgentActivityRepository extends JpaRepository<SampleOfAgentActivity, Long> {

}
