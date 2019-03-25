package tg.opentechconsult.koupona.web.rest;

import tg.opentechconsult.koupona.KouponaApp;

import tg.opentechconsult.koupona.domain.Pays;
import tg.opentechconsult.koupona.repository.PaysRepository;
import tg.opentechconsult.koupona.repository.search.PaysSearchRepository;
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
 * Test class for the PaysResource REST controller.
 *
 * @see PaysResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KouponaApp.class)
public class PaysResourceIntTest {

    private static final String DEFAULT_NOM_PAYS = "AAAAAAAAAA";
    private static final String UPDATED_NOM_PAYS = "BBBBBBBBBB";

    @Autowired
    private PaysRepository paysRepository;

    /**
     * This repository is mocked in the tg.opentechconsult.koupona.repository.search test package.
     *
     * @see tg.opentechconsult.koupona.repository.search.PaysSearchRepositoryMockConfiguration
     */
    @Autowired
    private PaysSearchRepository mockPaysSearchRepository;

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

    private MockMvc restPaysMockMvc;

    private Pays pays;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PaysResource paysResource = new PaysResource(paysRepository, mockPaysSearchRepository);
        this.restPaysMockMvc = MockMvcBuilders.standaloneSetup(paysResource)
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
    public static Pays createEntity(EntityManager em) {
        Pays pays = new Pays()
            .nomPays(DEFAULT_NOM_PAYS);
        return pays;
    }

    @Before
    public void initTest() {
        pays = createEntity(em);
    }

    @Test
    @Transactional
    public void createPays() throws Exception {
        int databaseSizeBeforeCreate = paysRepository.findAll().size();

        // Create the Pays
        restPaysMockMvc.perform(post("/api/pays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pays)))
            .andExpect(status().isCreated());

        // Validate the Pays in the database
        List<Pays> paysList = paysRepository.findAll();
        assertThat(paysList).hasSize(databaseSizeBeforeCreate + 1);
        Pays testPays = paysList.get(paysList.size() - 1);
        assertThat(testPays.getNomPays()).isEqualTo(DEFAULT_NOM_PAYS);

        // Validate the Pays in Elasticsearch
        verify(mockPaysSearchRepository, times(1)).save(testPays);
    }

    @Test
    @Transactional
    public void createPaysWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paysRepository.findAll().size();

        // Create the Pays with an existing ID
        pays.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaysMockMvc.perform(post("/api/pays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pays)))
            .andExpect(status().isBadRequest());

        // Validate the Pays in the database
        List<Pays> paysList = paysRepository.findAll();
        assertThat(paysList).hasSize(databaseSizeBeforeCreate);

        // Validate the Pays in Elasticsearch
        verify(mockPaysSearchRepository, times(0)).save(pays);
    }

    @Test
    @Transactional
    public void checkNomPaysIsRequired() throws Exception {
        int databaseSizeBeforeTest = paysRepository.findAll().size();
        // set the field null
        pays.setNomPays(null);

        // Create the Pays, which fails.

        restPaysMockMvc.perform(post("/api/pays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pays)))
            .andExpect(status().isBadRequest());

        List<Pays> paysList = paysRepository.findAll();
        assertThat(paysList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPays() throws Exception {
        // Initialize the database
        paysRepository.saveAndFlush(pays);

        // Get all the paysList
        restPaysMockMvc.perform(get("/api/pays?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pays.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomPays").value(hasItem(DEFAULT_NOM_PAYS.toString())));
    }
    
    @Test
    @Transactional
    public void getPays() throws Exception {
        // Initialize the database
        paysRepository.saveAndFlush(pays);

        // Get the pays
        restPaysMockMvc.perform(get("/api/pays/{id}", pays.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pays.getId().intValue()))
            .andExpect(jsonPath("$.nomPays").value(DEFAULT_NOM_PAYS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPays() throws Exception {
        // Get the pays
        restPaysMockMvc.perform(get("/api/pays/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePays() throws Exception {
        // Initialize the database
        paysRepository.saveAndFlush(pays);

        int databaseSizeBeforeUpdate = paysRepository.findAll().size();

        // Update the pays
        Pays updatedPays = paysRepository.findById(pays.getId()).get();
        // Disconnect from session so that the updates on updatedPays are not directly saved in db
        em.detach(updatedPays);
        updatedPays
            .nomPays(UPDATED_NOM_PAYS);

        restPaysMockMvc.perform(put("/api/pays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPays)))
            .andExpect(status().isOk());

        // Validate the Pays in the database
        List<Pays> paysList = paysRepository.findAll();
        assertThat(paysList).hasSize(databaseSizeBeforeUpdate);
        Pays testPays = paysList.get(paysList.size() - 1);
        assertThat(testPays.getNomPays()).isEqualTo(UPDATED_NOM_PAYS);

        // Validate the Pays in Elasticsearch
        verify(mockPaysSearchRepository, times(1)).save(testPays);
    }

    @Test
    @Transactional
    public void updateNonExistingPays() throws Exception {
        int databaseSizeBeforeUpdate = paysRepository.findAll().size();

        // Create the Pays

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaysMockMvc.perform(put("/api/pays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pays)))
            .andExpect(status().isBadRequest());

        // Validate the Pays in the database
        List<Pays> paysList = paysRepository.findAll();
        assertThat(paysList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Pays in Elasticsearch
        verify(mockPaysSearchRepository, times(0)).save(pays);
    }

    @Test
    @Transactional
    public void deletePays() throws Exception {
        // Initialize the database
        paysRepository.saveAndFlush(pays);

        int databaseSizeBeforeDelete = paysRepository.findAll().size();

        // Delete the pays
        restPaysMockMvc.perform(delete("/api/pays/{id}", pays.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Pays> paysList = paysRepository.findAll();
        assertThat(paysList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Pays in Elasticsearch
        verify(mockPaysSearchRepository, times(1)).deleteById(pays.getId());
    }

    @Test
    @Transactional
    public void searchPays() throws Exception {
        // Initialize the database
        paysRepository.saveAndFlush(pays);
        when(mockPaysSearchRepository.search(queryStringQuery("id:" + pays.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(pays), PageRequest.of(0, 1), 1));
        // Search the pays
        restPaysMockMvc.perform(get("/api/_search/pays?query=id:" + pays.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pays.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomPays").value(hasItem(DEFAULT_NOM_PAYS)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pays.class);
        Pays pays1 = new Pays();
        pays1.setId(1L);
        Pays pays2 = new Pays();
        pays2.setId(pays1.getId());
        assertThat(pays1).isEqualTo(pays2);
        pays2.setId(2L);
        assertThat(pays1).isNotEqualTo(pays2);
        pays1.setId(null);
        assertThat(pays1).isNotEqualTo(pays2);
    }
}
