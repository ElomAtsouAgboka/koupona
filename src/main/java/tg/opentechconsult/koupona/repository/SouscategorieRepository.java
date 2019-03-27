package tg.opentechconsult.koupona.repository;

import tg.opentechconsult.koupona.domain.Souscategorie;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Souscategorie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SouscategorieRepository extends JpaRepository<Souscategorie, Long> {

}
