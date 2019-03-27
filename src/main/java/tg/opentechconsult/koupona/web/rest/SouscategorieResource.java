package tg.opentechconsult.koupona.web.rest;
import tg.opentechconsult.koupona.domain.Souscategorie;
import tg.opentechconsult.koupona.repository.SouscategorieRepository;
import tg.opentechconsult.koupona.repository.search.SouscategorieSearchRepository;
import tg.opentechconsult.koupona.web.rest.errors.BadRequestAlertException;
import tg.opentechconsult.koupona.web.rest.util.HeaderUtil;
import tg.opentechconsult.koupona.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Souscategorie.
 */
@RestController
@RequestMapping("/api")
public class SouscategorieResource {

    private final Logger log = LoggerFactory.getLogger(SouscategorieResource.class);

    private static final String ENTITY_NAME = "souscategorie";

    private final SouscategorieRepository souscategorieRepository;

    private final SouscategorieSearchRepository souscategorieSearchRepository;

    public SouscategorieResource(SouscategorieRepository souscategorieRepository, SouscategorieSearchRepository souscategorieSearchRepository) {
        this.souscategorieRepository = souscategorieRepository;
        this.souscategorieSearchRepository = souscategorieSearchRepository;
    }

    /**
     * POST  /souscategories : Create a new souscategorie.
     *
     * @param souscategorie the souscategorie to create
     * @return the ResponseEntity with status 201 (Created) and with body the new souscategorie, or with status 400 (Bad Request) if the souscategorie has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/souscategories")
    public ResponseEntity<Souscategorie> createSouscategorie(@Valid @RequestBody Souscategorie souscategorie) throws URISyntaxException {
        log.debug("REST request to save Souscategorie : {}", souscategorie);
        if (souscategorie.getId() != null) {
            throw new BadRequestAlertException("A new souscategorie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Souscategorie result = souscategorieRepository.save(souscategorie);
        souscategorieSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/souscategories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /souscategories : Updates an existing souscategorie.
     *
     * @param souscategorie the souscategorie to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated souscategorie,
     * or with status 400 (Bad Request) if the souscategorie is not valid,
     * or with status 500 (Internal Server Error) if the souscategorie couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/souscategories")
    public ResponseEntity<Souscategorie> updateSouscategorie(@Valid @RequestBody Souscategorie souscategorie) throws URISyntaxException {
        log.debug("REST request to update Souscategorie : {}", souscategorie);
        if (souscategorie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Souscategorie result = souscategorieRepository.save(souscategorie);
        souscategorieSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, souscategorie.getId().toString()))
            .body(result);
    }

    /**
     * GET  /souscategories : get all the souscategories.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of souscategories in body
     */
    @GetMapping("/souscategories")
    public ResponseEntity<List<Souscategorie>> getAllSouscategories(Pageable pageable) {
        log.debug("REST request to get a page of Souscategories");
        Page<Souscategorie> page = souscategorieRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/souscategories");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /souscategories/:id : get the "id" souscategorie.
     *
     * @param id the id of the souscategorie to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the souscategorie, or with status 404 (Not Found)
     */
    @GetMapping("/souscategories/{id}")
    public ResponseEntity<Souscategorie> getSouscategorie(@PathVariable Long id) {
        log.debug("REST request to get Souscategorie : {}", id);
        Optional<Souscategorie> souscategorie = souscategorieRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(souscategorie);
    }

    /**
     * DELETE  /souscategories/:id : delete the "id" souscategorie.
     *
     * @param id the id of the souscategorie to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/souscategories/{id}")
    public ResponseEntity<Void> deleteSouscategorie(@PathVariable Long id) {
        log.debug("REST request to delete Souscategorie : {}", id);
        souscategorieRepository.deleteById(id);
        souscategorieSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/souscategories?query=:query : search for the souscategorie corresponding
     * to the query.
     *
     * @param query the query of the souscategorie search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/souscategories")
    public ResponseEntity<List<Souscategorie>> searchSouscategories(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Souscategories for query {}", query);
        Page<Souscategorie> page = souscategorieSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/souscategories");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
