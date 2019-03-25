package tg.opentechconsult.koupona.repository;

import tg.opentechconsult.koupona.domain.Topcategorie;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Topcategorie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TopcategorieRepository extends JpaRepository<Topcategorie, Long> {

}
