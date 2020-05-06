package com.ep.app.web.rest;

import com.ep.app.domain.Games;
import com.ep.app.service.GamesService;
import com.ep.app.web.rest.errors.BadRequestAlertException;
import com.ep.app.service.dto.GamesCriteria;
import com.ep.app.service.GamesQueryService;

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
 * REST controller for managing {@link com.ep.app.domain.Games}.
 */
@RestController
@RequestMapping("/api")
public class GamesResource {

    private final Logger log = LoggerFactory.getLogger(GamesResource.class);

    private static final String ENTITY_NAME = "games";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GamesService gamesService;

    private final GamesQueryService gamesQueryService;

    public GamesResource(GamesService gamesService, GamesQueryService gamesQueryService) {
        this.gamesService = gamesService;
        this.gamesQueryService = gamesQueryService;
    }

    /**
     * {@code POST  /games} : Create a new games.
     *
     * @param games the games to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new games, or with status {@code 400 (Bad Request)} if the games has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/games")
    public ResponseEntity<Games> createGames(@Valid @RequestBody Games games) throws URISyntaxException {
        log.debug("REST request to save Games : {}", games);
        if (games.getId() != null) {
            throw new BadRequestAlertException("A new games cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Games result = gamesService.save(games);
        return ResponseEntity.created(new URI("/api/games/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /games} : Updates an existing games.
     *
     * @param games the games to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated games,
     * or with status {@code 400 (Bad Request)} if the games is not valid,
     * or with status {@code 500 (Internal Server Error)} if the games couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/games")
    public ResponseEntity<Games> updateGames(@Valid @RequestBody Games games) throws URISyntaxException {
        log.debug("REST request to update Games : {}", games);
        if (games.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Games result = gamesService.save(games);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, games.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /games} : get all the games.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of games in body.
     */
    @GetMapping("/games")
    public ResponseEntity<List<Games>> getAllGames(GamesCriteria criteria) {
        log.debug("REST request to get Games by criteria: {}", criteria);
        List<Games> entityList = gamesQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /games/count} : count all the games.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/games/count")
    public ResponseEntity<Long> countGames(GamesCriteria criteria) {
        log.debug("REST request to count Games by criteria: {}", criteria);
        return ResponseEntity.ok().body(gamesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /games/:id} : get the "id" games.
     *
     * @param id the id of the games to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the games, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/games/{id}")
    public ResponseEntity<Games> getGames(@PathVariable Long id) {
        log.debug("REST request to get Games : {}", id);
        Optional<Games> games = gamesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(games);
    }

    /**
     * {@code DELETE  /games/:id} : delete the "id" games.
     *
     * @param id the id of the games to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/games/{id}")
    public ResponseEntity<Void> deleteGames(@PathVariable Long id) {
        log.debug("REST request to delete Games : {}", id);
        gamesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
