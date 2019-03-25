package tg.opentechconsult.koupona.repository.search;

import tg.opentechconsult.koupona.domain.Ville;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Ville entity.
 */
public interface VilleSearchRepository extends ElasticsearchRepository<Ville, Long> {
}
