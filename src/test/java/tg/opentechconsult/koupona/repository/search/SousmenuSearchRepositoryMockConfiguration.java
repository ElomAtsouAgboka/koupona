package tg.opentechconsult.koupona.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of SousmenuSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class SousmenuSearchRepositoryMockConfiguration {

    @MockBean
    private SousmenuSearchRepository mockSousmenuSearchRepository;

}
