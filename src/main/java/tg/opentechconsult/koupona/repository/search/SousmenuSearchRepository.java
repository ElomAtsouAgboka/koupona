package tg.opentechconsult.koupona.repository.search;

import tg.opentechconsult.koupona.domain.Sousmenu;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Sousmenu entity.
 */
public interface SousmenuSearchRepository extends ElasticsearchRepository<Sousmenu, Long> {
}
