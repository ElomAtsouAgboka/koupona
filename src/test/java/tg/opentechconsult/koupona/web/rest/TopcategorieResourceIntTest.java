package tg.opentechconsult.koupona.web.rest;

import tg.opentechconsult.koupona.KouponaApp;

import tg.opentechconsult.koupona.domain.Topcategorie;
import tg.opentechconsult.koupona.repository.TopcategorieRepository;
import tg.opentechconsult.koupona.repository.search.TopcategorieSearchRepository;
import tg.opentechconsult.koupona.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
 * Test class for the TopcategorieResource REST controller.
 *
 * @see TopcategorieResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KouponaApp.class)
public class TopcategorieResourceIntTest {

    private static final String DEFAULT_NOM_TOP_CATEGORIE = "AAAAAAAAAA";
    private static final String UPDATED_NOM_TOP_CATEGORIE = "BBBBBBBBBB";

    @Autowired
    private TopcategorieRepository topcategorieRepository;

    /**
     * This repository is mocked in the tg.opentechconsult.koupona.repository.search test package.
     *
     * @see tg.opentechconsult.koupona.repository.search.TopcategorieSearchRepositoryMockConfiguration
     */
    @Autowired
    private TopcategorieSearchRepository mockTopcategorieSearchRepository;

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

    private MockMvc restTopcategorieMockMvc;

    private Topcategorie topcategorie;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TopcategorieResource topcategorieResource = new TopcategorieResource(topcategorieRepository, mockTopcategorieSearchRepository);
        this.restTopcategorieMockMvc = MockMvcBuilders.standaloneSetup(topcategorieResource)
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
    public static Topcategorie createEntity(EntityManager em) {
        Topcategorie topcategorie = new Topcategorie()
            .nomTopCategorie(DEFAULT_NOM_TOP_CATEGORIE);
        return topcategorie;
    }

    @Before
    public void initTest() {
        topcategorie = createEntity(em);
    }

    @Test
    @Transactional
    public void createTopcategorie() throws Exception {
        int databaseSizeBeforeCreate = topcategorieRepository.findAll().size();

        // Create the Topcategorie
        restTopcategorieMockMvc.perform(post("/api/topcategories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(topcategorie)))
            .andExpect(status().isCreated());

        // Validate the Topcategorie in the database
        List<Topcategorie> topcategorieList = topcategorieRepository.findAll();
        assertThat(topcategorieList).hasSize(databaseSizeBeforeCreate + 1);
        Topcategorie testTopcategorie = topcategorieList.get(topcategorieList.size() - 1);
        assertThat(testTopcategorie.getNomTopCategorie()).isEqualTo(DEFAULT_NOM_TOP_CATEGORIE);

        // Validate the Topcategorie in Elasticsearch
        verify(mockTopcategorieSearchRepository, times(1)).save(testTopcategorie);
    }

    @Test
    @Transactional
    public void createTopcategorieWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = topcategorieRepository.findAll().size();

        // Create the Topcategorie with an existing ID
        topcategorie.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTopcategorieMockMvc.perform(post("/api/topcategories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(topcategorie)))
            .andExpect(status().isBadRequest());

        // Validate the Topcategorie in the database
        List<Topcategorie> topcategorieList = topcategorieRepository.findAll();
        assertThat(topcategorieList).hasSize(databaseSizeBeforeCreate);

        // Validate the Topcategorie in Elasticsearch
        verify(mockTopcategorieSearchRepository, times(0)).save(topcategorie);
    }

    @Test
    @Transactional
    public void checkNomTopCategorieIsRequired() throws Exception {
        int databaseSizeBeforeTest = topcategorieRepository.findAll().size();
        // set the field null
        topcategorie.setNomTopCategorie(null);

        // Create the Topcategorie, which fails.

        restTopcategorieMockMvc.perform(post("/api/topcategories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(topcategorie)))
            .andExpect(status().isBadRequest());

        List<Topcategorie> topcategorieList = topcategorieRepository.findAll();
        assertThat(topcategorieList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTopcategories() throws Exception {
        // Initialize the database
        topcategorieRepository.saveAndFlush(topcategorie);

        // Get all the topcategorieList
        restTopcategorieMockMvc.perform(get("/api/topcategories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(topcategorie.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomTopCategorie").value(hasItem(DEFAULT_NOM_TOP_CATEGORIE.toString())));
    }
    
    @Test
    @Transactional
    public void getTopcategorie() throws Exception {
        // Initialize the database
        topcategorieRepository.saveAndFlush(topcategorie);

        // Get the topcategorie
        restTopcategorieMockMvc.perform(get("/api/topcategories/{id}", topcategorie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(topcategorie.getId().intValue()))
            .andExpect(jsonPath("$.nomTopCategorie").value(DEFAULT_NOM_TOP_CATEGORIE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTopcategorie() throws Exception {
        // Get the topcategorie
        restTopcategorieMockMvc.perform(get("/api/topcategories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTopcategorie() throws Exception {
        // Initialize the database
        topcategorieRepository.saveAndFlush(topcategorie);

        int databaseSizeBeforeUpdate = topcategorieRepository.findAll().size();

        // Update the topcategorie
        Topcategorie updatedTopcategorie = topcategorieRepository.findById(topcategorie.getId()).get();
        // Disconnect from session so that the updates on updatedTopcategorie are not directly saved in db
        em.detach(updatedTopcategorie);
        updatedTopcategorie
            .nomTopCategorie(UPDATED_NOM_TOP_CATEGORIE);

        restTopcategorieMockMvc.perform(put("/api/topcategories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTopcategorie)))
            .andExpect(status().isOk());

        // Validate the Topcategorie in the database
        List<Topcategorie> topcategorieList = topcategorieRepository.findAll();
        assertThat(topcategorieList).hasSize(databaseSizeBeforeUpdate);
        Topcategorie testTopcategorie = topcategorieList.get(topcategorieList.size() - 1);
        assertThat(testTopcategorie.getNomTopCategorie()).isEqualTo(UPDATED_NOM_TOP_CATEGORIE);

        // Validate the Topcategorie in Elasticsearch
        verify(mockTopcategorieSearchRepository, times(1)).save(testTopcategorie);
    }

    @Test
    @Transactional
    public void updateNonExistingTopcategorie() throws Exception {
        int databaseSizeBeforeUpdate = topcategorieRepository.findAll().size();

        // Create the Topcategorie

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTopcategorieMockMvc.perform(put("/api/topcategories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(topcategorie)))
            .andExpect(status().isBadRequest());

        // Validate the Topcategorie in the database
        List<Topcategorie> topcategorieList = topcategorieRepository.findAll();
        assertThat(topcategorieList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Topcategorie in Elasticsearch
        verify(mockTopcategorieSearchRepository, times(0)).save(topcategorie);
    }

    @Test
    @Transactional
    public void deleteTopcategorie() throws Exception {
        // Initialize the database
        topcategorieRepository.saveAndFlush(topcategorie);

        int databaseSizeBeforeDelete = topcategorieRepository.findAll().size();

        // Delete the topcategorie
        restTopcategorieMockMvc.perform(delete("/api/topcategories/{id}", topcategorie.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Topcategorie> topcategorieList = topcategorieRepository.findAll();
        assertThat(topcategorieList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Topcategorie in Elasticsearch
        verify(mockTopcategorieSearchRepository, times(1)).deleteById(topcategorie.getId());
    }

    @Test
    @Transactional
    public void searchTopcategorie() throws Exception {
        // Initialize the database
        topcategorieRepository.saveAndFlush(topcategorie);
        when(mockTopcategorieSearchRepository.search(queryStringQuery("id:" + topcategorie.getId())))
            .thenReturn(Collections.singletonList(topcategorie));
        // Search the topcategorie
        restTopcategorieMockMvc.perform(get("/api/_search/topcategories?query=id:" + topcategorie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(topcategorie.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomTopCategorie").value(hasItem(DEFAULT_NOM_TOP_CATEGORIE)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Topcategorie.class);
        Topcategorie topcategorie1 = new Topcategorie();
        topcategorie1.setId(1L);
        Topcategorie topcategorie2 = new Topcategorie();
        topcategorie2.setId(topcategorie1.getId());
        assertThat(topcategorie1).isEqualTo(topcategorie2);
        topcategorie2.setId(2L);
        assertThat(topcategorie1).isNotEqualTo(topcategorie2);
        topcategorie1.setId(null);
        assertThat(topcategorie1).isNotEqualTo(topcategorie2);
    }
}
