package tg.opentechconsult.koupona.web.rest;

import tg.opentechconsult.koupona.KouponaApp;

import tg.opentechconsult.koupona.domain.Sousmenu;
import tg.opentechconsult.koupona.repository.SousmenuRepository;
import tg.opentechconsult.koupona.repository.search.SousmenuSearchRepository;
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
 * Test class for the SousmenuResource REST controller.
 *
 * @see SousmenuResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KouponaApp.class)
public class SousmenuResourceIntTest {

    private static final String DEFAULT_SOUS_MENU_ITEM = "AAAAAAAAAA";
    private static final String UPDATED_SOUS_MENU_ITEM = "BBBBBBBBBB";

    private static final String DEFAULT_SOUS_MENU_ITEM_IMG = "AAAAAAAAAA";
    private static final String UPDATED_SOUS_MENU_ITEM_IMG = "BBBBBBBBBB";

    @Autowired
    private SousmenuRepository sousmenuRepository;

    /**
     * This repository is mocked in the tg.opentechconsult.koupona.repository.search test package.
     *
     * @see tg.opentechconsult.koupona.repository.search.SousmenuSearchRepositoryMockConfiguration
     */
    @Autowired
    private SousmenuSearchRepository mockSousmenuSearchRepository;

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

    private MockMvc restSousmenuMockMvc;

    private Sousmenu sousmenu;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SousmenuResource sousmenuResource = new SousmenuResource(sousmenuRepository, mockSousmenuSearchRepository);
        this.restSousmenuMockMvc = MockMvcBuilders.standaloneSetup(sousmenuResource)
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
    public static Sousmenu createEntity(EntityManager em) {
        Sousmenu sousmenu = new Sousmenu()
            .sousMenuItem(DEFAULT_SOUS_MENU_ITEM)
            .sousMenuItemImg(DEFAULT_SOUS_MENU_ITEM_IMG);
        return sousmenu;
    }

    @Before
    public void initTest() {
        sousmenu = createEntity(em);
    }

    @Test
    @Transactional
    public void createSousmenu() throws Exception {
        int databaseSizeBeforeCreate = sousmenuRepository.findAll().size();

        // Create the Sousmenu
        restSousmenuMockMvc.perform(post("/api/sousmenus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sousmenu)))
            .andExpect(status().isCreated());

        // Validate the Sousmenu in the database
        List<Sousmenu> sousmenuList = sousmenuRepository.findAll();
        assertThat(sousmenuList).hasSize(databaseSizeBeforeCreate + 1);
        Sousmenu testSousmenu = sousmenuList.get(sousmenuList.size() - 1);
        assertThat(testSousmenu.getSousMenuItem()).isEqualTo(DEFAULT_SOUS_MENU_ITEM);
        assertThat(testSousmenu.getSousMenuItemImg()).isEqualTo(DEFAULT_SOUS_MENU_ITEM_IMG);

        // Validate the Sousmenu in Elasticsearch
        verify(mockSousmenuSearchRepository, times(1)).save(testSousmenu);
    }

    @Test
    @Transactional
    public void createSousmenuWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sousmenuRepository.findAll().size();

        // Create the Sousmenu with an existing ID
        sousmenu.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSousmenuMockMvc.perform(post("/api/sousmenus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sousmenu)))
            .andExpect(status().isBadRequest());

        // Validate the Sousmenu in the database
        List<Sousmenu> sousmenuList = sousmenuRepository.findAll();
        assertThat(sousmenuList).hasSize(databaseSizeBeforeCreate);

        // Validate the Sousmenu in Elasticsearch
        verify(mockSousmenuSearchRepository, times(0)).save(sousmenu);
    }

    @Test
    @Transactional
    public void checkSousMenuItemIsRequired() throws Exception {
        int databaseSizeBeforeTest = sousmenuRepository.findAll().size();
        // set the field null
        sousmenu.setSousMenuItem(null);

        // Create the Sousmenu, which fails.

        restSousmenuMockMvc.perform(post("/api/sousmenus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sousmenu)))
            .andExpect(status().isBadRequest());

        List<Sousmenu> sousmenuList = sousmenuRepository.findAll();
        assertThat(sousmenuList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSousmenus() throws Exception {
        // Initialize the database
        sousmenuRepository.saveAndFlush(sousmenu);

        // Get all the sousmenuList
        restSousmenuMockMvc.perform(get("/api/sousmenus?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sousmenu.getId().intValue())))
            .andExpect(jsonPath("$.[*].sousMenuItem").value(hasItem(DEFAULT_SOUS_MENU_ITEM.toString())))
            .andExpect(jsonPath("$.[*].sousMenuItemImg").value(hasItem(DEFAULT_SOUS_MENU_ITEM_IMG.toString())));
    }
    
    @Test
    @Transactional
    public void getSousmenu() throws Exception {
        // Initialize the database
        sousmenuRepository.saveAndFlush(sousmenu);

        // Get the sousmenu
        restSousmenuMockMvc.perform(get("/api/sousmenus/{id}", sousmenu.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sousmenu.getId().intValue()))
            .andExpect(jsonPath("$.sousMenuItem").value(DEFAULT_SOUS_MENU_ITEM.toString()))
            .andExpect(jsonPath("$.sousMenuItemImg").value(DEFAULT_SOUS_MENU_ITEM_IMG.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSousmenu() throws Exception {
        // Get the sousmenu
        restSousmenuMockMvc.perform(get("/api/sousmenus/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSousmenu() throws Exception {
        // Initialize the database
        sousmenuRepository.saveAndFlush(sousmenu);

        int databaseSizeBeforeUpdate = sousmenuRepository.findAll().size();

        // Update the sousmenu
        Sousmenu updatedSousmenu = sousmenuRepository.findById(sousmenu.getId()).get();
        // Disconnect from session so that the updates on updatedSousmenu are not directly saved in db
        em.detach(updatedSousmenu);
        updatedSousmenu
            .sousMenuItem(UPDATED_SOUS_MENU_ITEM)
            .sousMenuItemImg(UPDATED_SOUS_MENU_ITEM_IMG);

        restSousmenuMockMvc.perform(put("/api/sousmenus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSousmenu)))
            .andExpect(status().isOk());

        // Validate the Sousmenu in the database
        List<Sousmenu> sousmenuList = sousmenuRepository.findAll();
        assertThat(sousmenuList).hasSize(databaseSizeBeforeUpdate);
        Sousmenu testSousmenu = sousmenuList.get(sousmenuList.size() - 1);
        assertThat(testSousmenu.getSousMenuItem()).isEqualTo(UPDATED_SOUS_MENU_ITEM);
        assertThat(testSousmenu.getSousMenuItemImg()).isEqualTo(UPDATED_SOUS_MENU_ITEM_IMG);

        // Validate the Sousmenu in Elasticsearch
        verify(mockSousmenuSearchRepository, times(1)).save(testSousmenu);
    }

    @Test
    @Transactional
    public void updateNonExistingSousmenu() throws Exception {
        int databaseSizeBeforeUpdate = sousmenuRepository.findAll().size();

        // Create the Sousmenu

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSousmenuMockMvc.perform(put("/api/sousmenus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sousmenu)))
            .andExpect(status().isBadRequest());

        // Validate the Sousmenu in the database
        List<Sousmenu> sousmenuList = sousmenuRepository.findAll();
        assertThat(sousmenuList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Sousmenu in Elasticsearch
        verify(mockSousmenuSearchRepository, times(0)).save(sousmenu);
    }

    @Test
    @Transactional
    public void deleteSousmenu() throws Exception {
        // Initialize the database
        sousmenuRepository.saveAndFlush(sousmenu);

        int databaseSizeBeforeDelete = sousmenuRepository.findAll().size();

        // Delete the sousmenu
        restSousmenuMockMvc.perform(delete("/api/sousmenus/{id}", sousmenu.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Sousmenu> sousmenuList = sousmenuRepository.findAll();
        assertThat(sousmenuList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Sousmenu in Elasticsearch
        verify(mockSousmenuSearchRepository, times(1)).deleteById(sousmenu.getId());
    }

    @Test
    @Transactional
    public void searchSousmenu() throws Exception {
        // Initialize the database
        sousmenuRepository.saveAndFlush(sousmenu);
        when(mockSousmenuSearchRepository.search(queryStringQuery("id:" + sousmenu.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(sousmenu), PageRequest.of(0, 1), 1));
        // Search the sousmenu
        restSousmenuMockMvc.perform(get("/api/_search/sousmenus?query=id:" + sousmenu.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sousmenu.getId().intValue())))
            .andExpect(jsonPath("$.[*].sousMenuItem").value(hasItem(DEFAULT_SOUS_MENU_ITEM)))
            .andExpect(jsonPath("$.[*].sousMenuItemImg").value(hasItem(DEFAULT_SOUS_MENU_ITEM_IMG)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sousmenu.class);
        Sousmenu sousmenu1 = new Sousmenu();
        sousmenu1.setId(1L);
        Sousmenu sousmenu2 = new Sousmenu();
        sousmenu2.setId(sousmenu1.getId());
        assertThat(sousmenu1).isEqualTo(sousmenu2);
        sousmenu2.setId(2L);
        assertThat(sousmenu1).isNotEqualTo(sousmenu2);
        sousmenu1.setId(null);
        assertThat(sousmenu1).isNotEqualTo(sousmenu2);
    }
}
