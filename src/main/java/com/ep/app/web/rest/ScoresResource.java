package com.ep.app.web.rest;

import com.ep.app.domain.Scores;
import com.ep.app.service.ScoresService;
import com.ep.app.web.rest.errors.BadRequestAlertException;
import com.ep.app.service.dto.ScoresCriteria;
import com.ep.app.service.ScoresQueryService;

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
 * REST controller for managing {@link com.ep.app.domain.Scores}.
 */
@RestController
@RequestMapping("/api")
public class ScoresResource {

    private final Logger log = LoggerFactory.getLogger(ScoresResource.class);

    private static final String ENTITY_NAME = "scores";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ScoresService scoresService;

    private final ScoresQueryService scoresQueryService;

    public ScoresResource(ScoresService scoresService, ScoresQueryService scoresQueryService) {
        this.scoresService = scoresService;
        this.scoresQueryService = scoresQueryService;
    }

    /**
     * {@code POST  /scores} : Create a new scores.
     *
     * @param scores the scores to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new scores, or with status {@code 400 (Bad Request)} if the scores has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/scores")
    public ResponseEntity<Scores> createScores(@Valid @RequestBody Scores scores) throws URISyntaxException {
        log.debug("REST request to save Scores : {}", scores);
        if (scores.getId() != null) {
            throw new BadRequestAlertException("A new scores cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Scores result = scoresService.save(scores);
        return ResponseEntity.created(new URI("/api/scores/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /scores} : Updates an existing scores.
     *
     * @param scores the scores to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated scores,
     * or with status {@code 400 (Bad Request)} if the scores is not valid,
     * or with status {@code 500 (Internal Server Error)} if the scores couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/scores")
    public ResponseEntity<Scores> updateScores(@Valid @RequestBody Scores scores) throws URISyntaxException {
        log.debug("REST request to update Scores : {}", scores);
        if (scores.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Scores result = scoresService.save(scores);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, scores.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /scores} : get all the scores.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of scores in body.
     */
    @GetMapping("/scores")
    public ResponseEntity<List<Scores>> getAllScores(ScoresCriteria criteria) {
        log.debug("REST request to get Scores by criteria: {}", criteria);
        List<Scores> entityList = scoresQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /scores/count} : count all the scores.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/scores/count")
    public ResponseEntity<Long> countScores(ScoresCriteria criteria) {
        log.debug("REST request to count Scores by criteria: {}", criteria);
        return ResponseEntity.ok().body(scoresQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /scores/:id} : get the "id" scores.
     *
     * @param id the id of the scores to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the scores, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/scores/{id}")
    public ResponseEntity<Scores> getScores(@PathVariable Long id) {
        log.debug("REST request to get Scores : {}", id);
        Optional<Scores> scores = scoresService.findOne(id);
        return ResponseUtil.wrapOrNotFound(scores);
    }

    /**
     * {@code DELETE  /scores/:id} : delete the "id" scores.
     *
     * @param id the id of the scores to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/scores/{id}")
    public ResponseEntity<Void> deleteScores(@PathVariable Long id) {
        log.debug("REST request to delete Scores : {}", id);
        scoresService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
