package tg.opentechconsult.koupona.repository.search;

import tg.opentechconsult.koupona.domain.Souscategorie;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Souscategorie entity.
 */
public interface SouscategorieSearchRepository extends ElasticsearchRepository<Souscategorie, Long> {
}
