package pl.mdcode.repository;

import pl.mdcode.domain.Principle;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Principle entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PrincipleRepository extends JpaRepository<Principle, Long> {

}
