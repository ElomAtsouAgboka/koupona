package tg.opentechconsult.koupona.web.rest;
import tg.opentechconsult.koupona.domain.Ville;
import tg.opentechconsult.koupona.repository.VilleRepository;
import tg.opentechconsult.koupona.repository.search.VilleSearchRepository;
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
 * REST controller for managing Ville.
 */
@RestController
@RequestMapping("/api")
public class VilleResource {

    private final Logger log = LoggerFactory.getLogger(VilleResource.class);

    private static final String ENTITY_NAME = "ville";

    private final VilleRepository villeRepository;

    private final VilleSearchRepository villeSearchRepository;

    public VilleResource(VilleRepository villeRepository, VilleSearchRepository villeSearchRepository) {
        this.villeRepository = villeRepository;
        this.villeSearchRepository = villeSearchRepository;
    }

    /**
     * POST  /villes : Create a new ville.
     *
     * @param ville the ville to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ville, or with status 400 (Bad Request) if the ville has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/villes")
    public ResponseEntity<Ville> createVille(@Valid @RequestBody Ville ville) throws URISyntaxException {
        log.debug("REST request to save Ville : {}", ville);
        if (ville.getId() != null) {
            throw new BadRequestAlertException("A new ville cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Ville result = villeRepository.save(ville);
        villeSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/villes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /villes : Updates an existing ville.
     *
     * @param ville the ville to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ville,
     * or with status 400 (Bad Request) if the ville is not valid,
     * or with status 500 (Internal Server Error) if the ville couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/villes")
    public ResponseEntity<Ville> updateVille(@Valid @RequestBody Ville ville) throws URISyntaxException {
        log.debug("REST request to update Ville : {}", ville);
        if (ville.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Ville result = villeRepository.save(ville);
        villeSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ville.getId().toString()))
            .body(result);
    }

    /**
     * GET  /villes : get all the villes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of villes in body
     */
    @GetMapping("/villes")
    public ResponseEntity<List<Ville>> getAllVilles(Pageable pageable) {
        log.debug("REST request to get a page of Villes");
        Page<Ville> page = villeRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/villes");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /villes/:id : get the "id" ville.
     *
     * @param id the id of the ville to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ville, or with status 404 (Not Found)
     */
    @GetMapping("/villes/{id}")
    public ResponseEntity<Ville> getVille(@PathVariable Long id) {
        log.debug("REST request to get Ville : {}", id);
        Optional<Ville> ville = villeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(ville);
    }

    /**
     * DELETE  /villes/:id : delete the "id" ville.
     *
     * @param id the id of the ville to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/villes/{id}")
    public ResponseEntity<Void> deleteVille(@PathVariable Long id) {
        log.debug("REST request to delete Ville : {}", id);
        villeRepository.deleteById(id);
        villeSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/villes?query=:query : search for the ville corresponding
     * to the query.
     *
     * @param query the query of the ville search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/villes")
    public ResponseEntity<List<Ville>> searchVilles(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Villes for query {}", query);
        Page<Ville> page = villeSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/villes");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
