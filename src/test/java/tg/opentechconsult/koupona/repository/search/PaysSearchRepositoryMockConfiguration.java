package tg.opentechconsult.koupona.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of PaysSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class PaysSearchRepositoryMockConfiguration {

    @MockBean
    private PaysSearchRepository mockPaysSearchRepository;

}
