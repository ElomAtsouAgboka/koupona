package tg.opentechconsult.koupona.repository.search;

import tg.opentechconsult.koupona.domain.Pays;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Pays entity.
 */
public interface PaysSearchRepository extends ElasticsearchRepository<Pays, Long> {
}
