package tg.opentechconsult.koupona.web.rest;

import tg.opentechconsult.koupona.KouponaApp;

import tg.opentechconsult.koupona.domain.Quartier;
import tg.opentechconsult.koupona.repository.QuartierRepository;
import tg.opentechconsult.koupona.repository.search.QuartierSearchRepository;
import tg.opentechconsult.koupona.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;


import static tg.opentechconsult.koupona.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the QuartierResource REST controller.
 *
 * @see QuartierResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KouponaApp.class)
public class QuartierResourceIntTest {

    private static final String DEFAULT_NOM_QUARTIER = "AAAAAAAAAA";
    private static final String UPDATED_NOM_QUARTIER = "BBBBBBBBBB";

    @Autowired
    private QuartierRepository quartierRepository;

    /**
     * This repository is mocked in the tg.opentechconsult.koupona.repository.search test package.
     *
     * @see tg.opentechconsult.koupona.repository.search.QuartierSearchRepositoryMockConfiguration
     */
    @Autowired
    private QuartierSearchRepository mockQuartierSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restQuartierMockMvc;

    private Quartier quartier;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final QuartierResource quartierResource = new QuartierResource(quartierRepository, mockQuartierSearchRepository);
        this.restQuartierMockMvc = MockMvcBuilders.standaloneSetup(quartierResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Quartier createEntity(EntityManager em) {
        Quartier quartier = new Quartier()
            .nomQuartier(DEFAULT_NOM_QUARTIER);
        return quartier;
    }

    @Before
    public void initTest() {
        quartier = createEntity(em);
    }

    @Test
    @Transactional
    public void createQuartier() throws Exception {
        int databaseSizeBeforeCreate = quartierRepository.findAll().size();

        // Create the Quartier
        restQuartierMockMvc.perform(post("/api/quartiers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(quartier)))
            .andExpect(status().isCreated());

        // Validate the Quartier in the database
        List<Quartier> quartierList = quartierRepository.findAll();
        assertThat(quartierList).hasSize(databaseSizeBeforeCreate + 1);
        Quartier testQuartier = quartierList.get(quartierList.size() - 1);
        assertThat(testQuartier.getNomQuartier()).isEqualTo(DEFAULT_NOM_QUARTIER);

        // Validate the Quartier in Elasticsearch
        verify(mockQuartierSearchRepository, times(1)).save(testQuartier);
    }

    @Test
    @Transactional
    public void createQuartierWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = quartierRepository.findAll().size();

        // Create the Quartier with an existing ID
        quartier.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuartierMockMvc.perform(post("/api/quartiers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(quartier)))
            .andExpect(status().isBadRequest());

        // Validate the Quartier in the database
        List<Quartier> quartierList = quartierRepository.findAll();
        assertThat(quartierList).hasSize(databaseSizeBeforeCreate);

        // Validate the Quartier in Elasticsearch
        verify(mockQuartierSearchRepository, times(0)).save(quartier);
    }

    @Test
    @Transactional
    public void getAllQuartiers() throws Exception {
        // Initialize the database
        quartierRepository.saveAndFlush(quartier);

        // Get all the quartierList
        restQuartierMockMvc.perform(get("/api/quartiers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(quartier.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomQuartier").value(hasItem(DEFAULT_NOM_QUARTIER.toString())));
    }
    
    @Test
    @Transactional
    public void getQuartier() throws Exception {
        // Initialize the database
        quartierRepository.saveAndFlush(quartier);

        // Get the quartier
        restQuartierMockMvc.perform(get("/api/quartiers/{id}", quartier.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(quartier.getId().intValue()))
            .andExpect(jsonPath("$.nomQuartier").value(DEFAULT_NOM_QUARTIER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingQuartier() throws Exception {
        // Get the quartier
        restQuartierMockMvc.perform(get("/api/quartiers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQuartier() throws Exception {
        // Initialize the database
        quartierRepository.saveAndFlush(quartier);

        int databaseSizeBeforeUpdate = quartierRepository.findAll().size();

        // Update the quartier
        Quartier updatedQuartier = quartierRepository.findById(quartier.getId()).get();
        // Disconnect from session so that the updates on updatedQuartier are not directly saved in db
        em.detach(updatedQuartier);
        updatedQuartier
            .nomQuartier(UPDATED_NOM_QUARTIER);

        restQuartierMockMvc.perform(put("/api/quartiers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedQuartier)))
            .andExpect(status().isOk());

        // Validate the Quartier in the database
        List<Quartier> quartierList = quartierRepository.findAll();
        assertThat(quartierList).hasSize(databaseSizeBeforeUpdate);
        Quartier testQuartier = quartierList.get(quartierList.size() - 1);
        assertThat(testQuartier.getNomQuartier()).isEqualTo(UPDATED_NOM_QUARTIER);

        // Validate the Quartier in Elasticsearch
        verify(mockQuartierSearchRepository, times(1)).save(testQuartier);
    }

    @Test
    @Transactional
    public void updateNonExistingQuartier() throws Exception {
        int databaseSizeBeforeUpdate = quartierRepository.findAll().size();

        // Create the Quartier

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuartierMockMvc.perform(put("/api/quartiers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(quartier)))
            .andExpect(status().isBadRequest());

        // Validate the Quartier in the database
        List<Quartier> quartierList = quartierRepository.findAll();
        assertThat(quartierList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Quartier in Elasticsearch
        verify(mockQuartierSearchRepository, times(0)).save(quartier);
    }

    @Test
    @Transactional
    public void deleteQuartier() throws Exception {
        // Initialize the database
        quartierRepository.saveAndFlush(quartier);

        int databaseSizeBeforeDelete = quartierRepository.findAll().size();

        // Delete the quartier
        restQuartierMockMvc.perform(delete("/api/quartiers/{id}", quartier.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Quartier> quartierList = quartierRepository.findAll();
        assertThat(quartierList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Quartier in Elasticsearch
        verify(mockQuartierSearchRepository, times(1)).deleteById(quartier.getId());
    }

    @Test
    @Transactional
    public void searchQuartier() throws Exception {
        // Initialize the database
        quartierRepository.saveAndFlush(quartier);
        when(mockQuartierSearchRepository.search(queryStringQuery("id:" + quartier.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(quartier), PageRequest.of(0, 1), 1));
        // Search the quartier
        restQuartierMockMvc.perform(get("/api/_search/quartiers?query=id:" + quartier.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(quartier.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomQuartier").value(hasItem(DEFAULT_NOM_QUARTIER)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Quartier.class);
        Quartier quartier1 = new Quartier();
        quartier1.setId(1L);
        Quartier quartier2 = new Quartier();
        quartier2.setId(quartier1.getId());
        assertThat(quartier1).isEqualTo(quartier2);
        quartier2.setId(2L);
        assertThat(quartier1).isNotEqualTo(quartier2);
        quartier1.setId(null);
        assertThat(quartier1).isNotEqualTo(quartier2);
    }
}
