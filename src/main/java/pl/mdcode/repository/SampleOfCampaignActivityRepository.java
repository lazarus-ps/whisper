package pl.mdcode.repository;

import pl.mdcode.domain.SampleOfCampaignActivity;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the SampleOfCampaignActivity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SampleOfCampaignActivityRepository extends JpaRepository<SampleOfCampaignActivity, Long> {

}
