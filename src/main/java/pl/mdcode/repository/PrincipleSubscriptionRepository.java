package pl.mdcode.repository;

import pl.mdcode.domain.PrincipleSubscription;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PrincipleSubscription entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PrincipleSubscriptionRepository extends JpaRepository<PrincipleSubscription, Long> {

}
