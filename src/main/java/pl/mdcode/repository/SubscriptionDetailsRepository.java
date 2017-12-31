package pl.mdcode.repository;

import pl.mdcode.domain.SubscriptionDetails;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the SubscriptionDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SubscriptionDetailsRepository extends JpaRepository<SubscriptionDetails, Long> {

}
