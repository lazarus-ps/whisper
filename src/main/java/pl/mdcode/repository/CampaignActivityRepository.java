package pl.mdcode.repository;

import pl.mdcode.domain.CampaignActivity;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the CampaignActivity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CampaignActivityRepository extends JpaRepository<CampaignActivity, Long> {
    @Query("select distinct campaign_activity from CampaignActivity campaign_activity left join fetch campaign_activity.agents")
    List<CampaignActivity> findAllWithEagerRelationships();

    @Query("select campaign_activity from CampaignActivity campaign_activity left join fetch campaign_activity.agents where campaign_activity.id =:id")
    CampaignActivity findOneWithEagerRelationships(@Param("id") Long id);

}
