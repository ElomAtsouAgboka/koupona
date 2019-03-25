package tg.opentechconsult.koupona.repository;

import tg.opentechconsult.koupona.domain.Quartier;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Quartier entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuartierRepository extends JpaRepository<Quartier, Long> {

}
