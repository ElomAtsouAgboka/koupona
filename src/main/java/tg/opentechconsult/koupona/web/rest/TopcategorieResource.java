package tg.opentechconsult.koupona.web.rest;
import tg.opentechconsult.koupona.domain.Topcategorie;
import tg.opentechconsult.koupona.repository.TopcategorieRepository;
import tg.opentechconsult.koupona.repository.search.TopcategorieSearchRepository;
import tg.opentechconsult.koupona.web.rest.errors.BadRequestAlertException;
import tg.opentechconsult.koupona.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * REST controller for managing Topcategorie.
 */
@RestController
@RequestMapping("/api")
public class TopcategorieResource {

    private final Logger log = LoggerFactory.getLogger(TopcategorieResource.class);

    private static final String ENTITY_NAME = "topcategorie";

    private final TopcategorieRepository topcategorieRepository;

    private final TopcategorieSearchRepository topcategorieSearchRepository;

    public TopcategorieResource(TopcategorieRepository topcategorieRepository, TopcategorieSearchRepository topcategorieSearchRepository) {
        this.topcategorieRepository = topcategorieRepository;
        this.topcategorieSearchRepository = topcategorieSearchRepository;
    }

    /**
     * POST  /topcategories : Create a new topcategorie.
     *
     * @param topcategorie the topcategorie to create
     * @return the ResponseEntity with status 201 (Created) and with body the new topcategorie, or with status 400 (Bad Request) if the topcategorie has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/topcategories")
    public ResponseEntity<Topcategorie> createTopcategorie(@Valid @RequestBody Topcategorie topcategorie) throws URISyntaxException {
        log.debug("REST request to save Topcategorie : {}", topcategorie);
        if (topcategorie.getId() != null) {
            throw new BadRequestAlertException("A new topcategorie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Topcategorie result = topcategorieRepository.save(topcategorie);
        topcategorieSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/topcategories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /topcategories : Updates an existing topcategorie.
     *
     * @param topcategorie the topcategorie to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated topcategorie,
     * or with status 400 (Bad Request) if the topcategorie is not valid,
     * or with status 500 (Internal Server Error) if the topcategorie couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/topcategories")
    public ResponseEntity<Topcategorie> updateTopcategorie(@Valid @RequestBody Topcategorie topcategorie) throws URISyntaxException {
        log.debug("REST request to update Topcategorie : {}", topcategorie);
        if (topcategorie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Topcategorie result = topcategorieRepository.save(topcategorie);
        topcategorieSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, topcategorie.getId().toString()))
            .body(result);
    }

    /**
     * GET  /topcategories : get all the topcategories.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of topcategories in body
     */
    @GetMapping("/topcategories")
    public List<Topcategorie> getAllTopcategories() {
        log.debug("REST request to get all Topcategories");
        return topcategorieRepository.findAll();
    }

    /**
     * GET  /topcategories/:id : get the "id" topcategorie.
     *
     * @param id the id of the topcategorie to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the topcategorie, or with status 404 (Not Found)
     */
    @GetMapping("/topcategories/{id}")
    public ResponseEntity<Topcategorie> getTopcategorie(@PathVariable Long id) {
        log.debug("REST request to get Topcategorie : {}", id);
        Optional<Topcategorie> topcategorie = topcategorieRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(topcategorie);
    }

    /**
     * DELETE  /topcategories/:id : delete the "id" topcategorie.
     *
     * @param id the id of the topcategorie to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/topcategories/{id}")
    public ResponseEntity<Void> deleteTopcategorie(@PathVariable Long id) {
        log.debug("REST request to delete Topcategorie : {}", id);
        topcategorieRepository.deleteById(id);
        topcategorieSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/topcategories?query=:query : search for the topcategorie corresponding
     * to the query.
     *
     * @param query the query of the topcategorie search
     * @return the result of the search
     */
    @GetMapping("/_search/topcategories")
    public List<Topcategorie> searchTopcategories(@RequestParam String query) {
        log.debug("REST request to search Topcategories for query {}", query);
        return StreamSupport
            .stream(topcategorieSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
