package tg.opentechconsult.koupona.repository;

import tg.opentechconsult.koupona.domain.Sousmenu;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Sousmenu entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SousmenuRepository extends JpaRepository<Sousmenu, Long> {

}
