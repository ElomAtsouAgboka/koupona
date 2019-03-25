package tg.opentechconsult.koupona.repository.search;

import tg.opentechconsult.koupona.domain.Topcategorie;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Topcategorie entity.
 */
public interface TopcategorieSearchRepository extends ElasticsearchRepository<Topcategorie, Long> {
}
