package com.ep.app.web.rest;

import com.ep.app.domain.Hints;
import com.ep.app.service.HintsService;
import com.ep.app.web.rest.errors.BadRequestAlertException;
import com.ep.app.service.dto.HintsCriteria;
import com.ep.app.service.HintsQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.ep.app.domain.Hints}.
 */
@RestController
@RequestMapping("/api")
public class HintsResource {

    private final Logger log = LoggerFactory.getLogger(HintsResource.class);

    private static final String ENTITY_NAME = "hints";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HintsService hintsService;

    private final HintsQueryService hintsQueryService;

    public HintsResource(HintsService hintsService, HintsQueryService hintsQueryService) {
        this.hintsService = hintsService;
        this.hintsQueryService = hintsQueryService;
    }

    /**
     * {@code POST  /hints} : Create a new hints.
     *
     * @param hints the hints to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new hints, or with status {@code 400 (Bad Request)} if the hints has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/hints")
    public ResponseEntity<Hints> createHints(@Valid @RequestBody Hints hints) throws URISyntaxException {
        log.debug("REST request to save Hints : {}", hints);
        if (hints.getId() != null) {
            throw new BadRequestAlertException("A new hints cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Hints result = hintsService.save(hints);
        return ResponseEntity.created(new URI("/api/hints/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /hints} : Updates an existing hints.
     *
     * @param hints the hints to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hints,
     * or with status {@code 400 (Bad Request)} if the hints is not valid,
     * or with status {@code 500 (Internal Server Error)} if the hints couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/hints")
    public ResponseEntity<Hints> updateHints(@Valid @RequestBody Hints hints) throws URISyntaxException {
        log.debug("REST request to update Hints : {}", hints);
        if (hints.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Hints result = hintsService.save(hints);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, hints.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /hints} : get all the hints.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of hints in body.
     */
    @GetMapping("/hints")
    public ResponseEntity<List<Hints>> getAllHints(HintsCriteria criteria) {
        log.debug("REST request to get Hints by criteria: {}", criteria);
        List<Hints> entityList = hintsQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /hints/count} : count all the hints.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/hints/count")
    public ResponseEntity<Long> countHints(HintsCriteria criteria) {
        log.debug("REST request to count Hints by criteria: {}", criteria);
        return ResponseEntity.ok().body(hintsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /hints/:id} : get the "id" hints.
     *
     * @param id the id of the hints to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the hints, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/hints/{id}")
    public ResponseEntity<Hints> getHints(@PathVariable Long id) {
        log.debug("REST request to get Hints : {}", id);
        Optional<Hints> hints = hintsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(hints);
    }

    /**
     * {@code DELETE  /hints/:id} : delete the "id" hints.
     *
     * @param id the id of the hints to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/hints/{id}")
    public ResponseEntity<Void> deleteHints(@PathVariable Long id) {
        log.debug("REST request to delete Hints : {}", id);
        hintsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
