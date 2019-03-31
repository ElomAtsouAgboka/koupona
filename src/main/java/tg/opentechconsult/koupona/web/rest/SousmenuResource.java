package tg.opentechconsult.koupona.web.rest;
import tg.opentechconsult.koupona.domain.Sousmenu;
import tg.opentechconsult.koupona.repository.SousmenuRepository;
import tg.opentechconsult.koupona.repository.search.SousmenuSearchRepository;
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
 * REST controller for managing Sousmenu.
 */
@RestController
@RequestMapping("/api")
public class SousmenuResource {

    private final Logger log = LoggerFactory.getLogger(SousmenuResource.class);

    private static final String ENTITY_NAME = "sousmenu";

    private final SousmenuRepository sousmenuRepository;

    private final SousmenuSearchRepository sousmenuSearchRepository;

    public SousmenuResource(SousmenuRepository sousmenuRepository, SousmenuSearchRepository sousmenuSearchRepository) {
        this.sousmenuRepository = sousmenuRepository;
        this.sousmenuSearchRepository = sousmenuSearchRepository;
    }

    /**
     * POST  /sousmenus : Create a new sousmenu.
     *
     * @param sousmenu the sousmenu to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sousmenu, or with status 400 (Bad Request) if the sousmenu has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sousmenus")
    public ResponseEntity<Sousmenu> createSousmenu(@Valid @RequestBody Sousmenu sousmenu) throws URISyntaxException {
        log.debug("REST request to save Sousmenu : {}", sousmenu);
        if (sousmenu.getId() != null) {
            throw new BadRequestAlertException("A new sousmenu cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Sousmenu result = sousmenuRepository.save(sousmenu);
        sousmenuSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/sousmenus/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sousmenus : Updates an existing sousmenu.
     *
     * @param sousmenu the sousmenu to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sousmenu,
     * or with status 400 (Bad Request) if the sousmenu is not valid,
     * or with status 500 (Internal Server Error) if the sousmenu couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sousmenus")
    public ResponseEntity<Sousmenu> updateSousmenu(@Valid @RequestBody Sousmenu sousmenu) throws URISyntaxException {
        log.debug("REST request to update Sousmenu : {}", sousmenu);
        if (sousmenu.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Sousmenu result = sousmenuRepository.save(sousmenu);
        sousmenuSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, sousmenu.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sousmenus : get all the sousmenus.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sousmenus in body
     */
    @GetMapping("/sousmenus")
    public ResponseEntity<List<Sousmenu>> getAllSousmenus(Pageable pageable) {
        log.debug("REST request to get a page of Sousmenus");
        Page<Sousmenu> page = sousmenuRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sousmenus");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /sousmenus/:id : get the "id" sousmenu.
     *
     * @param id the id of the sousmenu to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sousmenu, or with status 404 (Not Found)
     */
    @GetMapping("/sousmenus/{id}")
    public ResponseEntity<Sousmenu> getSousmenu(@PathVariable Long id) {
        log.debug("REST request to get Sousmenu : {}", id);
        Optional<Sousmenu> sousmenu = sousmenuRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(sousmenu);
    }

    /**
     * DELETE  /sousmenus/:id : delete the "id" sousmenu.
     *
     * @param id the id of the sousmenu to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sousmenus/{id}")
    public ResponseEntity<Void> deleteSousmenu(@PathVariable Long id) {
        log.debug("REST request to delete Sousmenu : {}", id);
        sousmenuRepository.deleteById(id);
        sousmenuSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/sousmenus?query=:query : search for the sousmenu corresponding
     * to the query.
     *
     * @param query the query of the sousmenu search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/sousmenus")
    public ResponseEntity<List<Sousmenu>> searchSousmenus(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Sousmenus for query {}", query);
        Page<Sousmenu> page = sousmenuSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/sousmenus");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
