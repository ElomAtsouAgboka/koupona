package tg.opentechconsult.koupona.web.rest;

import tg.opentechconsult.koupona.KouponaApp;

import tg.opentechconsult.koupona.domain.Souscategorie;
import tg.opentechconsult.koupona.repository.SouscategorieRepository;
import tg.opentechconsult.koupona.repository.search.SouscategorieSearchRepository;
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
 * Test class for the SouscategorieResource REST controller.
 *
 * @see SouscategorieResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KouponaApp.class)
public class SouscategorieResourceIntTest {

    private static final String DEFAULT_NOM_SOUS_CATEGORIE = "AAAAAAAAAA";
    private static final String UPDATED_NOM_SOUS_CATEGORIE = "BBBBBBBBBB";

    @Autowired
    private SouscategorieRepository souscategorieRepository;

    /**
     * This repository is mocked in the tg.opentechconsult.koupona.repository.search test package.
     *
     * @see tg.opentechconsult.koupona.repository.search.SouscategorieSearchRepositoryMockConfiguration
     */
    @Autowired
    private SouscategorieSearchRepository mockSouscategorieSearchRepository;

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

    private MockMvc restSouscategorieMockMvc;

    private Souscategorie souscategorie;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SouscategorieResource souscategorieResource = new SouscategorieResource(souscategorieRepository, mockSouscategorieSearchRepository);
        this.restSouscategorieMockMvc = MockMvcBuilders.standaloneSetup(souscategorieResource)
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
    public static Souscategorie createEntity(EntityManager em) {
        Souscategorie souscategorie = new Souscategorie()
            .nomSousCategorie(DEFAULT_NOM_SOUS_CATEGORIE);
        return souscategorie;
    }

    @Before
    public void initTest() {
        souscategorie = createEntity(em);
    }

    @Test
    @Transactional
    public void createSouscategorie() throws Exception {
        int databaseSizeBeforeCreate = souscategorieRepository.findAll().size();

        // Create the Souscategorie
        restSouscategorieMockMvc.perform(post("/api/souscategories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(souscategorie)))
            .andExpect(status().isCreated());

        // Validate the Souscategorie in the database
        List<Souscategorie> souscategorieList = souscategorieRepository.findAll();
        assertThat(souscategorieList).hasSize(databaseSizeBeforeCreate + 1);
        Souscategorie testSouscategorie = souscategorieList.get(souscategorieList.size() - 1);
        assertThat(testSouscategorie.getNomSousCategorie()).isEqualTo(DEFAULT_NOM_SOUS_CATEGORIE);

        // Validate the Souscategorie in Elasticsearch
        verify(mockSouscategorieSearchRepository, times(1)).save(testSouscategorie);
    }

    @Test
    @Transactional
    public void createSouscategorieWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = souscategorieRepository.findAll().size();

        // Create the Souscategorie with an existing ID
        souscategorie.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSouscategorieMockMvc.perform(post("/api/souscategories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(souscategorie)))
            .andExpect(status().isBadRequest());

        // Validate the Souscategorie in the database
        List<Souscategorie> souscategorieList = souscategorieRepository.findAll();
        assertThat(souscategorieList).hasSize(databaseSizeBeforeCreate);

        // Validate the Souscategorie in Elasticsearch
        verify(mockSouscategorieSearchRepository, times(0)).save(souscategorie);
    }

    @Test
    @Transactional
    public void checkNomSousCategorieIsRequired() throws Exception {
        int databaseSizeBeforeTest = souscategorieRepository.findAll().size();
        // set the field null
        souscategorie.setNomSousCategorie(null);

        // Create the Souscategorie, which fails.

        restSouscategorieMockMvc.perform(post("/api/souscategories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(souscategorie)))
            .andExpect(status().isBadRequest());

        List<Souscategorie> souscategorieList = souscategorieRepository.findAll();
        assertThat(souscategorieList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSouscategories() throws Exception {
        // Initialize the database
        souscategorieRepository.saveAndFlush(souscategorie);

        // Get all the souscategorieList
        restSouscategorieMockMvc.perform(get("/api/souscategories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(souscategorie.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomSousCategorie").value(hasItem(DEFAULT_NOM_SOUS_CATEGORIE.toString())));
    }
    
    @Test
    @Transactional
    public void getSouscategorie() throws Exception {
        // Initialize the database
        souscategorieRepository.saveAndFlush(souscategorie);

        // Get the souscategorie
        restSouscategorieMockMvc.perform(get("/api/souscategories/{id}", souscategorie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(souscategorie.getId().intValue()))
            .andExpect(jsonPath("$.nomSousCategorie").value(DEFAULT_NOM_SOUS_CATEGORIE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSouscategorie() throws Exception {
        // Get the souscategorie
        restSouscategorieMockMvc.perform(get("/api/souscategories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSouscategorie() throws Exception {
        // Initialize the database
        souscategorieRepository.saveAndFlush(souscategorie);

        int databaseSizeBeforeUpdate = souscategorieRepository.findAll().size();

        // Update the souscategorie
        Souscategorie updatedSouscategorie = souscategorieRepository.findById(souscategorie.getId()).get();
        // Disconnect from session so that the updates on updatedSouscategorie are not directly saved in db
        em.detach(updatedSouscategorie);
        updatedSouscategorie
            .nomSousCategorie(UPDATED_NOM_SOUS_CATEGORIE);

        restSouscategorieMockMvc.perform(put("/api/souscategories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSouscategorie)))
            .andExpect(status().isOk());

        // Validate the Souscategorie in the database
        List<Souscategorie> souscategorieList = souscategorieRepository.findAll();
        assertThat(souscategorieList).hasSize(databaseSizeBeforeUpdate);
        Souscategorie testSouscategorie = souscategorieList.get(souscategorieList.size() - 1);
        assertThat(testSouscategorie.getNomSousCategorie()).isEqualTo(UPDATED_NOM_SOUS_CATEGORIE);

        // Validate the Souscategorie in Elasticsearch
        verify(mockSouscategorieSearchRepository, times(1)).save(testSouscategorie);
    }

    @Test
    @Transactional
    public void updateNonExistingSouscategorie() throws Exception {
        int databaseSizeBeforeUpdate = souscategorieRepository.findAll().size();

        // Create the Souscategorie

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSouscategorieMockMvc.perform(put("/api/souscategories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(souscategorie)))
            .andExpect(status().isBadRequest());

        // Validate the Souscategorie in the database
        List<Souscategorie> souscategorieList = souscategorieRepository.findAll();
        assertThat(souscategorieList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Souscategorie in Elasticsearch
        verify(mockSouscategorieSearchRepository, times(0)).save(souscategorie);
    }

    @Test
    @Transactional
    public void deleteSouscategorie() throws Exception {
        // Initialize the database
        souscategorieRepository.saveAndFlush(souscategorie);

        int databaseSizeBeforeDelete = souscategorieRepository.findAll().size();

        // Delete the souscategorie
        restSouscategorieMockMvc.perform(delete("/api/souscategories/{id}", souscategorie.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Souscategorie> souscategorieList = souscategorieRepository.findAll();
        assertThat(souscategorieList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Souscategorie in Elasticsearch
        verify(mockSouscategorieSearchRepository, times(1)).deleteById(souscategorie.getId());
    }

    @Test
    @Transactional
    public void searchSouscategorie() throws Exception {
        // Initialize the database
        souscategorieRepository.saveAndFlush(souscategorie);
        when(mockSouscategorieSearchRepository.search(queryStringQuery("id:" + souscategorie.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(souscategorie), PageRequest.of(0, 1), 1));
        // Search the souscategorie
        restSouscategorieMockMvc.perform(get("/api/_search/souscategories?query=id:" + souscategorie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(souscategorie.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomSousCategorie").value(hasItem(DEFAULT_NOM_SOUS_CATEGORIE)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Souscategorie.class);
        Souscategorie souscategorie1 = new Souscategorie();
        souscategorie1.setId(1L);
        Souscategorie souscategorie2 = new Souscategorie();
        souscategorie2.setId(souscategorie1.getId());
        assertThat(souscategorie1).isEqualTo(souscategorie2);
        souscategorie2.setId(2L);
        assertThat(souscategorie1).isNotEqualTo(souscategorie2);
        souscategorie1.setId(null);
        assertThat(souscategorie1).isNotEqualTo(souscategorie2);
    }
}
