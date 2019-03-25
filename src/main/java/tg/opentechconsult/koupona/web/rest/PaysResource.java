package tg.opentechconsult.koupona.web.rest;
import tg.opentechconsult.koupona.domain.Pays;
import tg.opentechconsult.koupona.repository.PaysRepository;
import tg.opentechconsult.koupona.repository.search.PaysSearchRepository;
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
 * REST controller for managing Pays.
 */
@RestController
@RequestMapping("/api")
public class PaysResource {

    private final Logger log = LoggerFactory.getLogger(PaysResource.class);

    private static final String ENTITY_NAME = "pays";

    private final PaysRepository paysRepository;

    private final PaysSearchRepository paysSearchRepository;

    public PaysResource(PaysRepository paysRepository, PaysSearchRepository paysSearchRepository) {
        this.paysRepository = paysRepository;
        this.paysSearchRepository = paysSearchRepository;
    }

    /**
     * POST  /pays : Create a new pays.
     *
     * @param pays the pays to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pays, or with status 400 (Bad Request) if the pays has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pays")
    public ResponseEntity<Pays> createPays(@Valid @RequestBody Pays pays) throws URISyntaxException {
        log.debug("REST request to save Pays : {}", pays);
        if (pays.getId() != null) {
            throw new BadRequestAlertException("A new pays cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Pays result = paysRepository.save(pays);
        paysSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/pays/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pays : Updates an existing pays.
     *
     * @param pays the pays to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pays,
     * or with status 400 (Bad Request) if the pays is not valid,
     * or with status 500 (Internal Server Error) if the pays couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pays")
    public ResponseEntity<Pays> updatePays(@Valid @RequestBody Pays pays) throws URISyntaxException {
        log.debug("REST request to update Pays : {}", pays);
        if (pays.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Pays result = paysRepository.save(pays);
        paysSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pays.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pays : get all the pays.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of pays in body
     */
    @GetMapping("/pays")
    public ResponseEntity<List<Pays>> getAllPays(Pageable pageable) {
        log.debug("REST request to get a page of Pays");
        Page<Pays> page = paysRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pays");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /pays/:id : get the "id" pays.
     *
     * @param id the id of the pays to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pays, or with status 404 (Not Found)
     */
    @GetMapping("/pays/{id}")
    public ResponseEntity<Pays> getPays(@PathVariable Long id) {
        log.debug("REST request to get Pays : {}", id);
        Optional<Pays> pays = paysRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(pays);
    }

    /**
     * DELETE  /pays/:id : delete the "id" pays.
     *
     * @param id the id of the pays to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pays/{id}")
    public ResponseEntity<Void> deletePays(@PathVariable Long id) {
        log.debug("REST request to delete Pays : {}", id);
        paysRepository.deleteById(id);
        paysSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/pays?query=:query : search for the pays corresponding
     * to the query.
     *
     * @param query the query of the pays search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/pays")
    public ResponseEntity<List<Pays>> searchPays(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Pays for query {}", query);
        Page<Pays> page = paysSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/pays");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
