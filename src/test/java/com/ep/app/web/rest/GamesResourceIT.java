package com.ep.app.web.rest;

import com.ep.app.EquityPursuitApp;
import com.ep.app.domain.Games;
import com.ep.app.domain.User;
import com.ep.app.repository.GamesRepository;
import com.ep.app.service.GamesService;
import com.ep.app.service.dto.GamesCriteria;
import com.ep.app.service.GamesQueryService;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.ep.app.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link GamesResource} REST controller.
 */
@SpringBootTest(classes = EquityPursuitApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class GamesResourceIT {

    private static final String DEFAULT_PLAYTIME = "AAAAAAAAAA";
    private static final String UPDATED_PLAYTIME = "BBBBBBBBBB";

    private static final String DEFAULT_LISTING_LIST = "AAAAAAAAAA";
    private static final String UPDATED_LISTING_LIST = "BBBBBBBBBB";

    private static final Integer DEFAULT_SCORE = 1;
    private static final Integer UPDATED_SCORE = 2;
    private static final Integer SMALLER_SCORE = 1 - 1;

    private static final ZonedDateTime DEFAULT_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    @Autowired
    private GamesRepository gamesRepository;

    @Autowired
    private GamesService gamesService;

    @Autowired
    private GamesQueryService gamesQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGamesMockMvc;

    private Games games;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Games createEntity(EntityManager em) {
        Games games = new Games()
            .playtime(DEFAULT_PLAYTIME)
            .listingList(DEFAULT_LISTING_LIST)
            .score(DEFAULT_SCORE)
            .time(DEFAULT_TIME);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        games.setUser(user);
        return games;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Games createUpdatedEntity(EntityManager em) {
        Games games = new Games()
            .playtime(UPDATED_PLAYTIME)
            .listingList(UPDATED_LISTING_LIST)
            .score(UPDATED_SCORE)
            .time(UPDATED_TIME);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        games.setUser(user);
        return games;
    }

    @BeforeEach
    public void initTest() {
        games = createEntity(em);
    }

    @Test
    @Transactional
    public void createGames() throws Exception {
        int databaseSizeBeforeCreate = gamesRepository.findAll().size();

        // Create the Games
        restGamesMockMvc.perform(post("/api/games")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(games)))
            .andExpect(status().isCreated());

        // Validate the Games in the database
        List<Games> gamesList = gamesRepository.findAll();
        assertThat(gamesList).hasSize(databaseSizeBeforeCreate + 1);
        Games testGames = gamesList.get(gamesList.size() - 1);
        assertThat(testGames.getPlaytime()).isEqualTo(DEFAULT_PLAYTIME);
        assertThat(testGames.getListingList()).isEqualTo(DEFAULT_LISTING_LIST);
        assertThat(testGames.getScore()).isEqualTo(DEFAULT_SCORE);
        assertThat(testGames.getTime()).isEqualTo(DEFAULT_TIME);
    }

    @Test
    @Transactional
    public void createGamesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = gamesRepository.findAll().size();

        // Create the Games with an existing ID
        games.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGamesMockMvc.perform(post("/api/games")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(games)))
            .andExpect(status().isBadRequest());

        // Validate the Games in the database
        List<Games> gamesList = gamesRepository.findAll();
        assertThat(gamesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkPlaytimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = gamesRepository.findAll().size();
        // set the field null
        games.setPlaytime(null);

        // Create the Games, which fails.

        restGamesMockMvc.perform(post("/api/games")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(games)))
            .andExpect(status().isBadRequest());

        List<Games> gamesList = gamesRepository.findAll();
        assertThat(gamesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkListingListIsRequired() throws Exception {
        int databaseSizeBeforeTest = gamesRepository.findAll().size();
        // set the field null
        games.setListingList(null);

        // Create the Games, which fails.

        restGamesMockMvc.perform(post("/api/games")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(games)))
            .andExpect(status().isBadRequest());

        List<Games> gamesList = gamesRepository.findAll();
        assertThat(gamesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkScoreIsRequired() throws Exception {
        int databaseSizeBeforeTest = gamesRepository.findAll().size();
        // set the field null
        games.setScore(null);

        // Create the Games, which fails.

        restGamesMockMvc.perform(post("/api/games")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(games)))
            .andExpect(status().isBadRequest());

        List<Games> gamesList = gamesRepository.findAll();
        assertThat(gamesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = gamesRepository.findAll().size();
        // set the field null
        games.setTime(null);

        // Create the Games, which fails.

        restGamesMockMvc.perform(post("/api/games")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(games)))
            .andExpect(status().isBadRequest());

        List<Games> gamesList = gamesRepository.findAll();
        assertThat(gamesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGames() throws Exception {
        // Initialize the database
        gamesRepository.saveAndFlush(games);

        // Get all the gamesList
        restGamesMockMvc.perform(get("/api/games?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(games.getId().intValue())))
            .andExpect(jsonPath("$.[*].playtime").value(hasItem(DEFAULT_PLAYTIME)))
            .andExpect(jsonPath("$.[*].listingList").value(hasItem(DEFAULT_LISTING_LIST)))
            .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE)))
            .andExpect(jsonPath("$.[*].time").value(hasItem(sameInstant(DEFAULT_TIME))));
    }
    
    @Test
    @Transactional
    public void getGames() throws Exception {
        // Initialize the database
        gamesRepository.saveAndFlush(games);

        // Get the games
        restGamesMockMvc.perform(get("/api/games/{id}", games.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(games.getId().intValue()))
            .andExpect(jsonPath("$.playtime").value(DEFAULT_PLAYTIME))
            .andExpect(jsonPath("$.listingList").value(DEFAULT_LISTING_LIST))
            .andExpect(jsonPath("$.score").value(DEFAULT_SCORE))
            .andExpect(jsonPath("$.time").value(sameInstant(DEFAULT_TIME)));
    }


    @Test
    @Transactional
    public void getGamesByIdFiltering() throws Exception {
        // Initialize the database
        gamesRepository.saveAndFlush(games);

        Long id = games.getId();

        defaultGamesShouldBeFound("id.equals=" + id);
        defaultGamesShouldNotBeFound("id.notEquals=" + id);

        defaultGamesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultGamesShouldNotBeFound("id.greaterThan=" + id);

        defaultGamesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultGamesShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllGamesByPlaytimeIsEqualToSomething() throws Exception {
        // Initialize the database
        gamesRepository.saveAndFlush(games);

        // Get all the gamesList where playtime equals to DEFAULT_PLAYTIME
        defaultGamesShouldBeFound("playtime.equals=" + DEFAULT_PLAYTIME);

        // Get all the gamesList where playtime equals to UPDATED_PLAYTIME
        defaultGamesShouldNotBeFound("playtime.equals=" + UPDATED_PLAYTIME);
    }

    @Test
    @Transactional
    public void getAllGamesByPlaytimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        gamesRepository.saveAndFlush(games);

        // Get all the gamesList where playtime not equals to DEFAULT_PLAYTIME
        defaultGamesShouldNotBeFound("playtime.notEquals=" + DEFAULT_PLAYTIME);

        // Get all the gamesList where playtime not equals to UPDATED_PLAYTIME
        defaultGamesShouldBeFound("playtime.notEquals=" + UPDATED_PLAYTIME);
    }

    @Test
    @Transactional
    public void getAllGamesByPlaytimeIsInShouldWork() throws Exception {
        // Initialize the database
        gamesRepository.saveAndFlush(games);

        // Get all the gamesList where playtime in DEFAULT_PLAYTIME or UPDATED_PLAYTIME
        defaultGamesShouldBeFound("playtime.in=" + DEFAULT_PLAYTIME + "," + UPDATED_PLAYTIME);

        // Get all the gamesList where playtime equals to UPDATED_PLAYTIME
        defaultGamesShouldNotBeFound("playtime.in=" + UPDATED_PLAYTIME);
    }

    @Test
    @Transactional
    public void getAllGamesByPlaytimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        gamesRepository.saveAndFlush(games);

        // Get all the gamesList where playtime is not null
        defaultGamesShouldBeFound("playtime.specified=true");

        // Get all the gamesList where playtime is null
        defaultGamesShouldNotBeFound("playtime.specified=false");
    }
                @Test
    @Transactional
    public void getAllGamesByPlaytimeContainsSomething() throws Exception {
        // Initialize the database
        gamesRepository.saveAndFlush(games);

        // Get all the gamesList where playtime contains DEFAULT_PLAYTIME
        defaultGamesShouldBeFound("playtime.contains=" + DEFAULT_PLAYTIME);

        // Get all the gamesList where playtime contains UPDATED_PLAYTIME
        defaultGamesShouldNotBeFound("playtime.contains=" + UPDATED_PLAYTIME);
    }

    @Test
    @Transactional
    public void getAllGamesByPlaytimeNotContainsSomething() throws Exception {
        // Initialize the database
        gamesRepository.saveAndFlush(games);

        // Get all the gamesList where playtime does not contain DEFAULT_PLAYTIME
        defaultGamesShouldNotBeFound("playtime.doesNotContain=" + DEFAULT_PLAYTIME);

        // Get all the gamesList where playtime does not contain UPDATED_PLAYTIME
        defaultGamesShouldBeFound("playtime.doesNotContain=" + UPDATED_PLAYTIME);
    }


    @Test
    @Transactional
    public void getAllGamesByListingListIsEqualToSomething() throws Exception {
        // Initialize the database
        gamesRepository.saveAndFlush(games);

        // Get all the gamesList where listingList equals to DEFAULT_LISTING_LIST
        defaultGamesShouldBeFound("listingList.equals=" + DEFAULT_LISTING_LIST);

        // Get all the gamesList where listingList equals to UPDATED_LISTING_LIST
        defaultGamesShouldNotBeFound("listingList.equals=" + UPDATED_LISTING_LIST);
    }

    @Test
    @Transactional
    public void getAllGamesByListingListIsNotEqualToSomething() throws Exception {
        // Initialize the database
        gamesRepository.saveAndFlush(games);

        // Get all the gamesList where listingList not equals to DEFAULT_LISTING_LIST
        defaultGamesShouldNotBeFound("listingList.notEquals=" + DEFAULT_LISTING_LIST);

        // Get all the gamesList where listingList not equals to UPDATED_LISTING_LIST
        defaultGamesShouldBeFound("listingList.notEquals=" + UPDATED_LISTING_LIST);
    }

    @Test
    @Transactional
    public void getAllGamesByListingListIsInShouldWork() throws Exception {
        // Initialize the database
        gamesRepository.saveAndFlush(games);

        // Get all the gamesList where listingList in DEFAULT_LISTING_LIST or UPDATED_LISTING_LIST
        defaultGamesShouldBeFound("listingList.in=" + DEFAULT_LISTING_LIST + "," + UPDATED_LISTING_LIST);

        // Get all the gamesList where listingList equals to UPDATED_LISTING_LIST
        defaultGamesShouldNotBeFound("listingList.in=" + UPDATED_LISTING_LIST);
    }

    @Test
    @Transactional
    public void getAllGamesByListingListIsNullOrNotNull() throws Exception {
        // Initialize the database
        gamesRepository.saveAndFlush(games);

        // Get all the gamesList where listingList is not null
        defaultGamesShouldBeFound("listingList.specified=true");

        // Get all the gamesList where listingList is null
        defaultGamesShouldNotBeFound("listingList.specified=false");
    }
                @Test
    @Transactional
    public void getAllGamesByListingListContainsSomething() throws Exception {
        // Initialize the database
        gamesRepository.saveAndFlush(games);

        // Get all the gamesList where listingList contains DEFAULT_LISTING_LIST
        defaultGamesShouldBeFound("listingList.contains=" + DEFAULT_LISTING_LIST);

        // Get all the gamesList where listingList contains UPDATED_LISTING_LIST
        defaultGamesShouldNotBeFound("listingList.contains=" + UPDATED_LISTING_LIST);
    }

    @Test
    @Transactional
    public void getAllGamesByListingListNotContainsSomething() throws Exception {
        // Initialize the database
        gamesRepository.saveAndFlush(games);

        // Get all the gamesList where listingList does not contain DEFAULT_LISTING_LIST
        defaultGamesShouldNotBeFound("listingList.doesNotContain=" + DEFAULT_LISTING_LIST);

        // Get all the gamesList where listingList does not contain UPDATED_LISTING_LIST
        defaultGamesShouldBeFound("listingList.doesNotContain=" + UPDATED_LISTING_LIST);
    }


    @Test
    @Transactional
    public void getAllGamesByScoreIsEqualToSomething() throws Exception {
        // Initialize the database
        gamesRepository.saveAndFlush(games);

        // Get all the gamesList where score equals to DEFAULT_SCORE
        defaultGamesShouldBeFound("score.equals=" + DEFAULT_SCORE);

        // Get all the gamesList where score equals to UPDATED_SCORE
        defaultGamesShouldNotBeFound("score.equals=" + UPDATED_SCORE);
    }

    @Test
    @Transactional
    public void getAllGamesByScoreIsNotEqualToSomething() throws Exception {
        // Initialize the database
        gamesRepository.saveAndFlush(games);

        // Get all the gamesList where score not equals to DEFAULT_SCORE
        defaultGamesShouldNotBeFound("score.notEquals=" + DEFAULT_SCORE);

        // Get all the gamesList where score not equals to UPDATED_SCORE
        defaultGamesShouldBeFound("score.notEquals=" + UPDATED_SCORE);
    }

    @Test
    @Transactional
    public void getAllGamesByScoreIsInShouldWork() throws Exception {
        // Initialize the database
        gamesRepository.saveAndFlush(games);

        // Get all the gamesList where score in DEFAULT_SCORE or UPDATED_SCORE
        defaultGamesShouldBeFound("score.in=" + DEFAULT_SCORE + "," + UPDATED_SCORE);

        // Get all the gamesList where score equals to UPDATED_SCORE
        defaultGamesShouldNotBeFound("score.in=" + UPDATED_SCORE);
    }

    @Test
    @Transactional
    public void getAllGamesByScoreIsNullOrNotNull() throws Exception {
        // Initialize the database
        gamesRepository.saveAndFlush(games);

        // Get all the gamesList where score is not null
        defaultGamesShouldBeFound("score.specified=true");

        // Get all the gamesList where score is null
        defaultGamesShouldNotBeFound("score.specified=false");
    }

    @Test
    @Transactional
    public void getAllGamesByScoreIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gamesRepository.saveAndFlush(games);

        // Get all the gamesList where score is greater than or equal to DEFAULT_SCORE
        defaultGamesShouldBeFound("score.greaterThanOrEqual=" + DEFAULT_SCORE);

        // Get all the gamesList where score is greater than or equal to UPDATED_SCORE
        defaultGamesShouldNotBeFound("score.greaterThanOrEqual=" + UPDATED_SCORE);
    }

    @Test
    @Transactional
    public void getAllGamesByScoreIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gamesRepository.saveAndFlush(games);

        // Get all the gamesList where score is less than or equal to DEFAULT_SCORE
        defaultGamesShouldBeFound("score.lessThanOrEqual=" + DEFAULT_SCORE);

        // Get all the gamesList where score is less than or equal to SMALLER_SCORE
        defaultGamesShouldNotBeFound("score.lessThanOrEqual=" + SMALLER_SCORE);
    }

    @Test
    @Transactional
    public void getAllGamesByScoreIsLessThanSomething() throws Exception {
        // Initialize the database
        gamesRepository.saveAndFlush(games);

        // Get all the gamesList where score is less than DEFAULT_SCORE
        defaultGamesShouldNotBeFound("score.lessThan=" + DEFAULT_SCORE);

        // Get all the gamesList where score is less than UPDATED_SCORE
        defaultGamesShouldBeFound("score.lessThan=" + UPDATED_SCORE);
    }

    @Test
    @Transactional
    public void getAllGamesByScoreIsGreaterThanSomething() throws Exception {
        // Initialize the database
        gamesRepository.saveAndFlush(games);

        // Get all the gamesList where score is greater than DEFAULT_SCORE
        defaultGamesShouldNotBeFound("score.greaterThan=" + DEFAULT_SCORE);

        // Get all the gamesList where score is greater than SMALLER_SCORE
        defaultGamesShouldBeFound("score.greaterThan=" + SMALLER_SCORE);
    }


    @Test
    @Transactional
    public void getAllGamesByTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        gamesRepository.saveAndFlush(games);

        // Get all the gamesList where time equals to DEFAULT_TIME
        defaultGamesShouldBeFound("time.equals=" + DEFAULT_TIME);

        // Get all the gamesList where time equals to UPDATED_TIME
        defaultGamesShouldNotBeFound("time.equals=" + UPDATED_TIME);
    }

    @Test
    @Transactional
    public void getAllGamesByTimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        gamesRepository.saveAndFlush(games);

        // Get all the gamesList where time not equals to DEFAULT_TIME
        defaultGamesShouldNotBeFound("time.notEquals=" + DEFAULT_TIME);

        // Get all the gamesList where time not equals to UPDATED_TIME
        defaultGamesShouldBeFound("time.notEquals=" + UPDATED_TIME);
    }

    @Test
    @Transactional
    public void getAllGamesByTimeIsInShouldWork() throws Exception {
        // Initialize the database
        gamesRepository.saveAndFlush(games);

        // Get all the gamesList where time in DEFAULT_TIME or UPDATED_TIME
        defaultGamesShouldBeFound("time.in=" + DEFAULT_TIME + "," + UPDATED_TIME);

        // Get all the gamesList where time equals to UPDATED_TIME
        defaultGamesShouldNotBeFound("time.in=" + UPDATED_TIME);
    }

    @Test
    @Transactional
    public void getAllGamesByTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        gamesRepository.saveAndFlush(games);

        // Get all the gamesList where time is not null
        defaultGamesShouldBeFound("time.specified=true");

        // Get all the gamesList where time is null
        defaultGamesShouldNotBeFound("time.specified=false");
    }

    @Test
    @Transactional
    public void getAllGamesByTimeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gamesRepository.saveAndFlush(games);

        // Get all the gamesList where time is greater than or equal to DEFAULT_TIME
        defaultGamesShouldBeFound("time.greaterThanOrEqual=" + DEFAULT_TIME);

        // Get all the gamesList where time is greater than or equal to UPDATED_TIME
        defaultGamesShouldNotBeFound("time.greaterThanOrEqual=" + UPDATED_TIME);
    }

    @Test
    @Transactional
    public void getAllGamesByTimeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gamesRepository.saveAndFlush(games);

        // Get all the gamesList where time is less than or equal to DEFAULT_TIME
        defaultGamesShouldBeFound("time.lessThanOrEqual=" + DEFAULT_TIME);

        // Get all the gamesList where time is less than or equal to SMALLER_TIME
        defaultGamesShouldNotBeFound("time.lessThanOrEqual=" + SMALLER_TIME);
    }

    @Test
    @Transactional
    public void getAllGamesByTimeIsLessThanSomething() throws Exception {
        // Initialize the database
        gamesRepository.saveAndFlush(games);

        // Get all the gamesList where time is less than DEFAULT_TIME
        defaultGamesShouldNotBeFound("time.lessThan=" + DEFAULT_TIME);

        // Get all the gamesList where time is less than UPDATED_TIME
        defaultGamesShouldBeFound("time.lessThan=" + UPDATED_TIME);
    }

    @Test
    @Transactional
    public void getAllGamesByTimeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        gamesRepository.saveAndFlush(games);

        // Get all the gamesList where time is greater than DEFAULT_TIME
        defaultGamesShouldNotBeFound("time.greaterThan=" + DEFAULT_TIME);

        // Get all the gamesList where time is greater than SMALLER_TIME
        defaultGamesShouldBeFound("time.greaterThan=" + SMALLER_TIME);
    }


    @Test
    @Transactional
    public void getAllGamesByUserIsEqualToSomething() throws Exception {
        // Get already existing entity
        User user = games.getUser();
        gamesRepository.saveAndFlush(games);
        Long userId = user.getId();

        // Get all the gamesList where user equals to userId
        defaultGamesShouldBeFound("userId.equals=" + userId);

        // Get all the gamesList where user equals to userId + 1
        defaultGamesShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultGamesShouldBeFound(String filter) throws Exception {
        restGamesMockMvc.perform(get("/api/games?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(games.getId().intValue())))
            .andExpect(jsonPath("$.[*].playtime").value(hasItem(DEFAULT_PLAYTIME)))
            .andExpect(jsonPath("$.[*].listingList").value(hasItem(DEFAULT_LISTING_LIST)))
            .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE)))
            .andExpect(jsonPath("$.[*].time").value(hasItem(sameInstant(DEFAULT_TIME))));

        // Check, that the count call also returns 1
        restGamesMockMvc.perform(get("/api/games/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultGamesShouldNotBeFound(String filter) throws Exception {
        restGamesMockMvc.perform(get("/api/games?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restGamesMockMvc.perform(get("/api/games/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingGames() throws Exception {
        // Get the games
        restGamesMockMvc.perform(get("/api/games/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGames() throws Exception {
        // Initialize the database
        gamesService.save(games);

        int databaseSizeBeforeUpdate = gamesRepository.findAll().size();

        // Update the games
        Games updatedGames = gamesRepository.findById(games.getId()).get();
        // Disconnect from session so that the updates on updatedGames are not directly saved in db
        em.detach(updatedGames);
        updatedGames
            .playtime(UPDATED_PLAYTIME)
            .listingList(UPDATED_LISTING_LIST)
            .score(UPDATED_SCORE)
            .time(UPDATED_TIME);

        restGamesMockMvc.perform(put("/api/games")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedGames)))
            .andExpect(status().isOk());

        // Validate the Games in the database
        List<Games> gamesList = gamesRepository.findAll();
        assertThat(gamesList).hasSize(databaseSizeBeforeUpdate);
        Games testGames = gamesList.get(gamesList.size() - 1);
        assertThat(testGames.getPlaytime()).isEqualTo(UPDATED_PLAYTIME);
        assertThat(testGames.getListingList()).isEqualTo(UPDATED_LISTING_LIST);
        assertThat(testGames.getScore()).isEqualTo(UPDATED_SCORE);
        assertThat(testGames.getTime()).isEqualTo(UPDATED_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingGames() throws Exception {
        int databaseSizeBeforeUpdate = gamesRepository.findAll().size();

        // Create the Games

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGamesMockMvc.perform(put("/api/games")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(games)))
            .andExpect(status().isBadRequest());

        // Validate the Games in the database
        List<Games> gamesList = gamesRepository.findAll();
        assertThat(gamesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGames() throws Exception {
        // Initialize the database
        gamesService.save(games);

        int databaseSizeBeforeDelete = gamesRepository.findAll().size();

        // Delete the games
        restGamesMockMvc.perform(delete("/api/games/{id}", games.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Games> gamesList = gamesRepository.findAll();
        assertThat(gamesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
