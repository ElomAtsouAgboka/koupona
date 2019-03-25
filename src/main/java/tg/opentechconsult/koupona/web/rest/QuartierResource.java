package tg.opentechconsult.koupona.web.rest;
import tg.opentechconsult.koupona.domain.Quartier;
import tg.opentechconsult.koupona.repository.QuartierRepository;
import tg.opentechconsult.koupona.repository.search.QuartierSearchRepository;
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

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Quartier.
 */
@RestController
@RequestMapping("/api")
public class QuartierResource {

    private final Logger log = LoggerFactory.getLogger(QuartierResource.class);

    private static final String ENTITY_NAME = "quartier";

    private final QuartierRepository quartierRepository;

    private final QuartierSearchRepository quartierSearchRepository;

    public QuartierResource(QuartierRepository quartierRepository, QuartierSearchRepository quartierSearchRepository) {
        this.quartierRepository = quartierRepository;
        this.quartierSearchRepository = quartierSearchRepository;
    }

    /**
     * POST  /quartiers : Create a new quartier.
     *
     * @param quartier the quartier to create
     * @return the ResponseEntity with status 201 (Created) and with body the new quartier, or with status 400 (Bad Request) if the quartier has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/quartiers")
    public ResponseEntity<Quartier> createQuartier(@RequestBody Quartier quartier) throws URISyntaxException {
        log.debug("REST request to save Quartier : {}", quartier);
        if (quartier.getId() != null) {
            throw new BadRequestAlertException("A new quartier cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Quartier result = quartierRepository.save(quartier);
        quartierSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/quartiers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /quartiers : Updates an existing quartier.
     *
     * @param quartier the quartier to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated quartier,
     * or with status 400 (Bad Request) if the quartier is not valid,
     * or with status 500 (Internal Server Error) if the quartier couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/quartiers")
    public ResponseEntity<Quartier> updateQuartier(@RequestBody Quartier quartier) throws URISyntaxException {
        log.debug("REST request to update Quartier : {}", quartier);
        if (quartier.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Quartier result = quartierRepository.save(quartier);
        quartierSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, quartier.getId().toString()))
            .body(result);
    }

    /**
     * GET  /quartiers : get all the quartiers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of quartiers in body
     */
    @GetMapping("/quartiers")
    public ResponseEntity<List<Quartier>> getAllQuartiers(Pageable pageable) {
        log.debug("REST request to get a page of Quartiers");
        Page<Quartier> page = quartierRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/quartiers");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /quartiers/:id : get the "id" quartier.
     *
     * @param id the id of the quartier to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the quartier, or with status 404 (Not Found)
     */
    @GetMapping("/quartiers/{id}")
    public ResponseEntity<Quartier> getQuartier(@PathVariable Long id) {
        log.debug("REST request to get Quartier : {}", id);
        Optional<Quartier> quartier = quartierRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(quartier);
    }

    /**
     * DELETE  /quartiers/:id : delete the "id" quartier.
     *
     * @param id the id of the quartier to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/quartiers/{id}")
    public ResponseEntity<Void> deleteQuartier(@PathVariable Long id) {
        log.debug("REST request to delete Quartier : {}", id);
        quartierRepository.deleteById(id);
        quartierSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/quartiers?query=:query : search for the quartier corresponding
     * to the query.
     *
     * @param query the query of the quartier search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/quartiers")
    public ResponseEntity<List<Quartier>> searchQuartiers(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Quartiers for query {}", query);
        Page<Quartier> page = quartierSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/quartiers");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
