package com.ep.app.web.rest;

import com.ep.app.EquityPursuitApp;
import com.ep.app.domain.Scores;
import com.ep.app.domain.Games;
import com.ep.app.repository.ScoresRepository;
import com.ep.app.service.ScoresService;
import com.ep.app.service.dto.ScoresCriteria;
import com.ep.app.service.ScoresQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ScoresResource} REST controller.
 */
@SpringBootTest(classes = EquityPursuitApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class ScoresResourceIT {

    private static final Integer DEFAULT_SCORE = 1;
    private static final Integer UPDATED_SCORE = 2;
    private static final Integer SMALLER_SCORE = 1 - 1;

    @Autowired
    private ScoresRepository scoresRepository;

    @Autowired
    private ScoresService scoresService;

    @Autowired
    private ScoresQueryService scoresQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restScoresMockMvc;

    private Scores scores;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Scores createEntity(EntityManager em) {
        Scores scores = new Scores()
            .score(DEFAULT_SCORE);
        // Add required entity
        Games games;
        if (TestUtil.findAll(em, Games.class).isEmpty()) {
            games = GamesResourceIT.createEntity(em);
            em.persist(games);
            em.flush();
        } else {
            games = TestUtil.findAll(em, Games.class).get(0);
        }
        scores.setGames(games);
        return scores;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Scores createUpdatedEntity(EntityManager em) {
        Scores scores = new Scores()
            .score(UPDATED_SCORE);
        // Add required entity
        Games games;
        if (TestUtil.findAll(em, Games.class).isEmpty()) {
            games = GamesResourceIT.createUpdatedEntity(em);
            em.persist(games);
            em.flush();
        } else {
            games = TestUtil.findAll(em, Games.class).get(0);
        }
        scores.setGames(games);
        return scores;
    }

    @BeforeEach
    public void initTest() {
        scores = createEntity(em);
    }

    @Test
    @Transactional
    public void createScores() throws Exception {
        int databaseSizeBeforeCreate = scoresRepository.findAll().size();

        // Create the Scores
        restScoresMockMvc.perform(post("/api/scores")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(scores)))
            .andExpect(status().isCreated());

        // Validate the Scores in the database
        List<Scores> scoresList = scoresRepository.findAll();
        assertThat(scoresList).hasSize(databaseSizeBeforeCreate + 1);
        Scores testScores = scoresList.get(scoresList.size() - 1);
        assertThat(testScores.getScore()).isEqualTo(DEFAULT_SCORE);
    }

    @Test
    @Transactional
    public void createScoresWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = scoresRepository.findAll().size();

        // Create the Scores with an existing ID
        scores.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restScoresMockMvc.perform(post("/api/scores")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(scores)))
            .andExpect(status().isBadRequest());

        // Validate the Scores in the database
        List<Scores> scoresList = scoresRepository.findAll();
        assertThat(scoresList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllScores() throws Exception {
        // Initialize the database
        scoresRepository.saveAndFlush(scores);

        // Get all the scoresList
        restScoresMockMvc.perform(get("/api/scores?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(scores.getId().intValue())))
            .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE)));
    }
    
    @Test
    @Transactional
    public void getScores() throws Exception {
        // Initialize the database
        scoresRepository.saveAndFlush(scores);

        // Get the scores
        restScoresMockMvc.perform(get("/api/scores/{id}", scores.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(scores.getId().intValue()))
            .andExpect(jsonPath("$.score").value(DEFAULT_SCORE));
    }


    @Test
    @Transactional
    public void getScoresByIdFiltering() throws Exception {
        // Initialize the database
        scoresRepository.saveAndFlush(scores);

        Long id = scores.getId();

        defaultScoresShouldBeFound("id.equals=" + id);
        defaultScoresShouldNotBeFound("id.notEquals=" + id);

        defaultScoresShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultScoresShouldNotBeFound("id.greaterThan=" + id);

        defaultScoresShouldBeFound("id.lessThanOrEqual=" + id);
        defaultScoresShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllScoresByScoreIsEqualToSomething() throws Exception {
        // Initialize the database
        scoresRepository.saveAndFlush(scores);

        // Get all the scoresList where score equals to DEFAULT_SCORE
        defaultScoresShouldBeFound("score.equals=" + DEFAULT_SCORE);

        // Get all the scoresList where score equals to UPDATED_SCORE
        defaultScoresShouldNotBeFound("score.equals=" + UPDATED_SCORE);
    }

    @Test
    @Transactional
    public void getAllScoresByScoreIsNotEqualToSomething() throws Exception {
        // Initialize the database
        scoresRepository.saveAndFlush(scores);

        // Get all the scoresList where score not equals to DEFAULT_SCORE
        defaultScoresShouldNotBeFound("score.notEquals=" + DEFAULT_SCORE);

        // Get all the scoresList where score not equals to UPDATED_SCORE
        defaultScoresShouldBeFound("score.notEquals=" + UPDATED_SCORE);
    }

    @Test
    @Transactional
    public void getAllScoresByScoreIsInShouldWork() throws Exception {
        // Initialize the database
        scoresRepository.saveAndFlush(scores);

        // Get all the scoresList where score in DEFAULT_SCORE or UPDATED_SCORE
        defaultScoresShouldBeFound("score.in=" + DEFAULT_SCORE + "," + UPDATED_SCORE);

        // Get all the scoresList where score equals to UPDATED_SCORE
        defaultScoresShouldNotBeFound("score.in=" + UPDATED_SCORE);
    }

    @Test
    @Transactional
    public void getAllScoresByScoreIsNullOrNotNull() throws Exception {
        // Initialize the database
        scoresRepository.saveAndFlush(scores);

        // Get all the scoresList where score is not null
        defaultScoresShouldBeFound("score.specified=true");

        // Get all the scoresList where score is null
        defaultScoresShouldNotBeFound("score.specified=false");
    }

    @Test
    @Transactional
    public void getAllScoresByScoreIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        scoresRepository.saveAndFlush(scores);

        // Get all the scoresList where score is greater than or equal to DEFAULT_SCORE
        defaultScoresShouldBeFound("score.greaterThanOrEqual=" + DEFAULT_SCORE);

        // Get all the scoresList where score is greater than or equal to UPDATED_SCORE
        defaultScoresShouldNotBeFound("score.greaterThanOrEqual=" + UPDATED_SCORE);
    }

    @Test
    @Transactional
    public void getAllScoresByScoreIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        scoresRepository.saveAndFlush(scores);

        // Get all the scoresList where score is less than or equal to DEFAULT_SCORE
        defaultScoresShouldBeFound("score.lessThanOrEqual=" + DEFAULT_SCORE);

        // Get all the scoresList where score is less than or equal to SMALLER_SCORE
        defaultScoresShouldNotBeFound("score.lessThanOrEqual=" + SMALLER_SCORE);
    }

    @Test
    @Transactional
    public void getAllScoresByScoreIsLessThanSomething() throws Exception {
        // Initialize the database
        scoresRepository.saveAndFlush(scores);

        // Get all the scoresList where score is less than DEFAULT_SCORE
        defaultScoresShouldNotBeFound("score.lessThan=" + DEFAULT_SCORE);

        // Get all the scoresList where score is less than UPDATED_SCORE
        defaultScoresShouldBeFound("score.lessThan=" + UPDATED_SCORE);
    }

    @Test
    @Transactional
    public void getAllScoresByScoreIsGreaterThanSomething() throws Exception {
        // Initialize the database
        scoresRepository.saveAndFlush(scores);

        // Get all the scoresList where score is greater than DEFAULT_SCORE
        defaultScoresShouldNotBeFound("score.greaterThan=" + DEFAULT_SCORE);

        // Get all the scoresList where score is greater than SMALLER_SCORE
        defaultScoresShouldBeFound("score.greaterThan=" + SMALLER_SCORE);
    }


    @Test
    @Transactional
    public void getAllScoresByGamesIsEqualToSomething() throws Exception {
        // Get already existing entity
        Games games = scores.getGames();
        scoresRepository.saveAndFlush(scores);
        Long gamesId = games.getId();

        // Get all the scoresList where games equals to gamesId
        defaultScoresShouldBeFound("gamesId.equals=" + gamesId);

        // Get all the scoresList where games equals to gamesId + 1
        defaultScoresShouldNotBeFound("gamesId.equals=" + (gamesId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultScoresShouldBeFound(String filter) throws Exception {
        restScoresMockMvc.perform(get("/api/scores?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(scores.getId().intValue())))
            .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE)));

        // Check, that the count call also returns 1
        restScoresMockMvc.perform(get("/api/scores/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultScoresShouldNotBeFound(String filter) throws Exception {
        restScoresMockMvc.perform(get("/api/scores?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restScoresMockMvc.perform(get("/api/scores/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingScores() throws Exception {
        // Get the scores
        restScoresMockMvc.perform(get("/api/scores/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateScores() throws Exception {
        // Initialize the database
        scoresService.save(scores);

        int databaseSizeBeforeUpdate = scoresRepository.findAll().size();

        // Update the scores
        Scores updatedScores = scoresRepository.findById(scores.getId()).get();
        // Disconnect from session so that the updates on updatedScores are not directly saved in db
        em.detach(updatedScores);
        updatedScores
            .score(UPDATED_SCORE);

        restScoresMockMvc.perform(put("/api/scores")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedScores)))
            .andExpect(status().isOk());

        // Validate the Scores in the database
        List<Scores> scoresList = scoresRepository.findAll();
        assertThat(scoresList).hasSize(databaseSizeBeforeUpdate);
        Scores testScores = scoresList.get(scoresList.size() - 1);
        assertThat(testScores.getScore()).isEqualTo(UPDATED_SCORE);
    }

    @Test
    @Transactional
    public void updateNonExistingScores() throws Exception {
        int databaseSizeBeforeUpdate = scoresRepository.findAll().size();

        // Create the Scores

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restScoresMockMvc.perform(put("/api/scores")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(scores)))
            .andExpect(status().isBadRequest());

        // Validate the Scores in the database
        List<Scores> scoresList = scoresRepository.findAll();
        assertThat(scoresList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteScores() throws Exception {
        // Initialize the database
        scoresService.save(scores);

        int databaseSizeBeforeDelete = scoresRepository.findAll().size();

        // Delete the scores
        restScoresMockMvc.perform(delete("/api/scores/{id}", scores.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Scores> scoresList = scoresRepository.findAll();
        assertThat(scoresList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
