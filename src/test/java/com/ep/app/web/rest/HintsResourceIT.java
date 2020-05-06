package com.ep.app.web.rest;

import com.ep.app.EquityPursuitApp;
import com.ep.app.domain.Hints;
import com.ep.app.domain.Listings;
import com.ep.app.repository.HintsRepository;
import com.ep.app.service.HintsService;
import com.ep.app.service.dto.HintsCriteria;
import com.ep.app.service.HintsQueryService;

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
 * Integration tests for the {@link HintsResource} REST controller.
 */
@SpringBootTest(classes = EquityPursuitApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class HintsResourceIT {

    private static final String DEFAULT_HINT = "AAAAAAAAAA";
    private static final String UPDATED_HINT = "BBBBBBBBBB";

    private static final Double DEFAULT_MODIFIER = 1D;
    private static final Double UPDATED_MODIFIER = 2D;
    private static final Double SMALLER_MODIFIER = 1D - 1D;

    @Autowired
    private HintsRepository hintsRepository;

    @Autowired
    private HintsService hintsService;

    @Autowired
    private HintsQueryService hintsQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHintsMockMvc;

    private Hints hints;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Hints createEntity(EntityManager em) {
        Hints hints = new Hints()
            .hint(DEFAULT_HINT)
            .modifier(DEFAULT_MODIFIER);
        // Add required entity
        Listings listings;
        if (TestUtil.findAll(em, Listings.class).isEmpty()) {
            listings = ListingsResourceIT.createEntity(em);
            em.persist(listings);
            em.flush();
        } else {
            listings = TestUtil.findAll(em, Listings.class).get(0);
        }
        hints.setListings(listings);
        return hints;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Hints createUpdatedEntity(EntityManager em) {
        Hints hints = new Hints()
            .hint(UPDATED_HINT)
            .modifier(UPDATED_MODIFIER);
        // Add required entity
        Listings listings;
        if (TestUtil.findAll(em, Listings.class).isEmpty()) {
            listings = ListingsResourceIT.createUpdatedEntity(em);
            em.persist(listings);
            em.flush();
        } else {
            listings = TestUtil.findAll(em, Listings.class).get(0);
        }
        hints.setListings(listings);
        return hints;
    }

    @BeforeEach
    public void initTest() {
        hints = createEntity(em);
    }

    @Test
    @Transactional
    public void createHints() throws Exception {
        int databaseSizeBeforeCreate = hintsRepository.findAll().size();

        // Create the Hints
        restHintsMockMvc.perform(post("/api/hints")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(hints)))
            .andExpect(status().isCreated());

        // Validate the Hints in the database
        List<Hints> hintsList = hintsRepository.findAll();
        assertThat(hintsList).hasSize(databaseSizeBeforeCreate + 1);
        Hints testHints = hintsList.get(hintsList.size() - 1);
        assertThat(testHints.getHint()).isEqualTo(DEFAULT_HINT);
        assertThat(testHints.getModifier()).isEqualTo(DEFAULT_MODIFIER);
    }

    @Test
    @Transactional
    public void createHintsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = hintsRepository.findAll().size();

        // Create the Hints with an existing ID
        hints.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHintsMockMvc.perform(post("/api/hints")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(hints)))
            .andExpect(status().isBadRequest());

        // Validate the Hints in the database
        List<Hints> hintsList = hintsRepository.findAll();
        assertThat(hintsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkHintIsRequired() throws Exception {
        int databaseSizeBeforeTest = hintsRepository.findAll().size();
        // set the field null
        hints.setHint(null);

        // Create the Hints, which fails.

        restHintsMockMvc.perform(post("/api/hints")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(hints)))
            .andExpect(status().isBadRequest());

        List<Hints> hintsList = hintsRepository.findAll();
        assertThat(hintsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModifierIsRequired() throws Exception {
        int databaseSizeBeforeTest = hintsRepository.findAll().size();
        // set the field null
        hints.setModifier(null);

        // Create the Hints, which fails.

        restHintsMockMvc.perform(post("/api/hints")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(hints)))
            .andExpect(status().isBadRequest());

        List<Hints> hintsList = hintsRepository.findAll();
        assertThat(hintsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHints() throws Exception {
        // Initialize the database
        hintsRepository.saveAndFlush(hints);

        // Get all the hintsList
        restHintsMockMvc.perform(get("/api/hints?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hints.getId().intValue())))
            .andExpect(jsonPath("$.[*].hint").value(hasItem(DEFAULT_HINT)))
            .andExpect(jsonPath("$.[*].modifier").value(hasItem(DEFAULT_MODIFIER.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getHints() throws Exception {
        // Initialize the database
        hintsRepository.saveAndFlush(hints);

        // Get the hints
        restHintsMockMvc.perform(get("/api/hints/{id}", hints.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(hints.getId().intValue()))
            .andExpect(jsonPath("$.hint").value(DEFAULT_HINT))
            .andExpect(jsonPath("$.modifier").value(DEFAULT_MODIFIER.doubleValue()));
    }


    @Test
    @Transactional
    public void getHintsByIdFiltering() throws Exception {
        // Initialize the database
        hintsRepository.saveAndFlush(hints);

        Long id = hints.getId();

        defaultHintsShouldBeFound("id.equals=" + id);
        defaultHintsShouldNotBeFound("id.notEquals=" + id);

        defaultHintsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultHintsShouldNotBeFound("id.greaterThan=" + id);

        defaultHintsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultHintsShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllHintsByHintIsEqualToSomething() throws Exception {
        // Initialize the database
        hintsRepository.saveAndFlush(hints);

        // Get all the hintsList where hint equals to DEFAULT_HINT
        defaultHintsShouldBeFound("hint.equals=" + DEFAULT_HINT);

        // Get all the hintsList where hint equals to UPDATED_HINT
        defaultHintsShouldNotBeFound("hint.equals=" + UPDATED_HINT);
    }

    @Test
    @Transactional
    public void getAllHintsByHintIsNotEqualToSomething() throws Exception {
        // Initialize the database
        hintsRepository.saveAndFlush(hints);

        // Get all the hintsList where hint not equals to DEFAULT_HINT
        defaultHintsShouldNotBeFound("hint.notEquals=" + DEFAULT_HINT);

        // Get all the hintsList where hint not equals to UPDATED_HINT
        defaultHintsShouldBeFound("hint.notEquals=" + UPDATED_HINT);
    }

    @Test
    @Transactional
    public void getAllHintsByHintIsInShouldWork() throws Exception {
        // Initialize the database
        hintsRepository.saveAndFlush(hints);

        // Get all the hintsList where hint in DEFAULT_HINT or UPDATED_HINT
        defaultHintsShouldBeFound("hint.in=" + DEFAULT_HINT + "," + UPDATED_HINT);

        // Get all the hintsList where hint equals to UPDATED_HINT
        defaultHintsShouldNotBeFound("hint.in=" + UPDATED_HINT);
    }

    @Test
    @Transactional
    public void getAllHintsByHintIsNullOrNotNull() throws Exception {
        // Initialize the database
        hintsRepository.saveAndFlush(hints);

        // Get all the hintsList where hint is not null
        defaultHintsShouldBeFound("hint.specified=true");

        // Get all the hintsList where hint is null
        defaultHintsShouldNotBeFound("hint.specified=false");
    }
                @Test
    @Transactional
    public void getAllHintsByHintContainsSomething() throws Exception {
        // Initialize the database
        hintsRepository.saveAndFlush(hints);

        // Get all the hintsList where hint contains DEFAULT_HINT
        defaultHintsShouldBeFound("hint.contains=" + DEFAULT_HINT);

        // Get all the hintsList where hint contains UPDATED_HINT
        defaultHintsShouldNotBeFound("hint.contains=" + UPDATED_HINT);
    }

    @Test
    @Transactional
    public void getAllHintsByHintNotContainsSomething() throws Exception {
        // Initialize the database
        hintsRepository.saveAndFlush(hints);

        // Get all the hintsList where hint does not contain DEFAULT_HINT
        defaultHintsShouldNotBeFound("hint.doesNotContain=" + DEFAULT_HINT);

        // Get all the hintsList where hint does not contain UPDATED_HINT
        defaultHintsShouldBeFound("hint.doesNotContain=" + UPDATED_HINT);
    }


    @Test
    @Transactional
    public void getAllHintsByModifierIsEqualToSomething() throws Exception {
        // Initialize the database
        hintsRepository.saveAndFlush(hints);

        // Get all the hintsList where modifier equals to DEFAULT_MODIFIER
        defaultHintsShouldBeFound("modifier.equals=" + DEFAULT_MODIFIER);

        // Get all the hintsList where modifier equals to UPDATED_MODIFIER
        defaultHintsShouldNotBeFound("modifier.equals=" + UPDATED_MODIFIER);
    }

    @Test
    @Transactional
    public void getAllHintsByModifierIsNotEqualToSomething() throws Exception {
        // Initialize the database
        hintsRepository.saveAndFlush(hints);

        // Get all the hintsList where modifier not equals to DEFAULT_MODIFIER
        defaultHintsShouldNotBeFound("modifier.notEquals=" + DEFAULT_MODIFIER);

        // Get all the hintsList where modifier not equals to UPDATED_MODIFIER
        defaultHintsShouldBeFound("modifier.notEquals=" + UPDATED_MODIFIER);
    }

    @Test
    @Transactional
    public void getAllHintsByModifierIsInShouldWork() throws Exception {
        // Initialize the database
        hintsRepository.saveAndFlush(hints);

        // Get all the hintsList where modifier in DEFAULT_MODIFIER or UPDATED_MODIFIER
        defaultHintsShouldBeFound("modifier.in=" + DEFAULT_MODIFIER + "," + UPDATED_MODIFIER);

        // Get all the hintsList where modifier equals to UPDATED_MODIFIER
        defaultHintsShouldNotBeFound("modifier.in=" + UPDATED_MODIFIER);
    }

    @Test
    @Transactional
    public void getAllHintsByModifierIsNullOrNotNull() throws Exception {
        // Initialize the database
        hintsRepository.saveAndFlush(hints);

        // Get all the hintsList where modifier is not null
        defaultHintsShouldBeFound("modifier.specified=true");

        // Get all the hintsList where modifier is null
        defaultHintsShouldNotBeFound("modifier.specified=false");
    }

    @Test
    @Transactional
    public void getAllHintsByModifierIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        hintsRepository.saveAndFlush(hints);

        // Get all the hintsList where modifier is greater than or equal to DEFAULT_MODIFIER
        defaultHintsShouldBeFound("modifier.greaterThanOrEqual=" + DEFAULT_MODIFIER);

        // Get all the hintsList where modifier is greater than or equal to UPDATED_MODIFIER
        defaultHintsShouldNotBeFound("modifier.greaterThanOrEqual=" + UPDATED_MODIFIER);
    }

    @Test
    @Transactional
    public void getAllHintsByModifierIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        hintsRepository.saveAndFlush(hints);

        // Get all the hintsList where modifier is less than or equal to DEFAULT_MODIFIER
        defaultHintsShouldBeFound("modifier.lessThanOrEqual=" + DEFAULT_MODIFIER);

        // Get all the hintsList where modifier is less than or equal to SMALLER_MODIFIER
        defaultHintsShouldNotBeFound("modifier.lessThanOrEqual=" + SMALLER_MODIFIER);
    }

    @Test
    @Transactional
    public void getAllHintsByModifierIsLessThanSomething() throws Exception {
        // Initialize the database
        hintsRepository.saveAndFlush(hints);

        // Get all the hintsList where modifier is less than DEFAULT_MODIFIER
        defaultHintsShouldNotBeFound("modifier.lessThan=" + DEFAULT_MODIFIER);

        // Get all the hintsList where modifier is less than UPDATED_MODIFIER
        defaultHintsShouldBeFound("modifier.lessThan=" + UPDATED_MODIFIER);
    }

    @Test
    @Transactional
    public void getAllHintsByModifierIsGreaterThanSomething() throws Exception {
        // Initialize the database
        hintsRepository.saveAndFlush(hints);

        // Get all the hintsList where modifier is greater than DEFAULT_MODIFIER
        defaultHintsShouldNotBeFound("modifier.greaterThan=" + DEFAULT_MODIFIER);

        // Get all the hintsList where modifier is greater than SMALLER_MODIFIER
        defaultHintsShouldBeFound("modifier.greaterThan=" + SMALLER_MODIFIER);
    }


    @Test
    @Transactional
    public void getAllHintsByListingsIsEqualToSomething() throws Exception {
        // Get already existing entity
        Listings listings = hints.getListings();
        hintsRepository.saveAndFlush(hints);
        Long listingsId = listings.getId();

        // Get all the hintsList where listings equals to listingsId
        defaultHintsShouldBeFound("listingsId.equals=" + listingsId);

        // Get all the hintsList where listings equals to listingsId + 1
        defaultHintsShouldNotBeFound("listingsId.equals=" + (listingsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultHintsShouldBeFound(String filter) throws Exception {
        restHintsMockMvc.perform(get("/api/hints?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hints.getId().intValue())))
            .andExpect(jsonPath("$.[*].hint").value(hasItem(DEFAULT_HINT)))
            .andExpect(jsonPath("$.[*].modifier").value(hasItem(DEFAULT_MODIFIER.doubleValue())));

        // Check, that the count call also returns 1
        restHintsMockMvc.perform(get("/api/hints/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultHintsShouldNotBeFound(String filter) throws Exception {
        restHintsMockMvc.perform(get("/api/hints?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restHintsMockMvc.perform(get("/api/hints/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingHints() throws Exception {
        // Get the hints
        restHintsMockMvc.perform(get("/api/hints/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHints() throws Exception {
        // Initialize the database
        hintsService.save(hints);

        int databaseSizeBeforeUpdate = hintsRepository.findAll().size();

        // Update the hints
        Hints updatedHints = hintsRepository.findById(hints.getId()).get();
        // Disconnect from session so that the updates on updatedHints are not directly saved in db
        em.detach(updatedHints);
        updatedHints
            .hint(UPDATED_HINT)
            .modifier(UPDATED_MODIFIER);

        restHintsMockMvc.perform(put("/api/hints")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedHints)))
            .andExpect(status().isOk());

        // Validate the Hints in the database
        List<Hints> hintsList = hintsRepository.findAll();
        assertThat(hintsList).hasSize(databaseSizeBeforeUpdate);
        Hints testHints = hintsList.get(hintsList.size() - 1);
        assertThat(testHints.getHint()).isEqualTo(UPDATED_HINT);
        assertThat(testHints.getModifier()).isEqualTo(UPDATED_MODIFIER);
    }

    @Test
    @Transactional
    public void updateNonExistingHints() throws Exception {
        int databaseSizeBeforeUpdate = hintsRepository.findAll().size();

        // Create the Hints

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHintsMockMvc.perform(put("/api/hints")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(hints)))
            .andExpect(status().isBadRequest());

        // Validate the Hints in the database
        List<Hints> hintsList = hintsRepository.findAll();
        assertThat(hintsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteHints() throws Exception {
        // Initialize the database
        hintsService.save(hints);

        int databaseSizeBeforeDelete = hintsRepository.findAll().size();

        // Delete the hints
        restHintsMockMvc.perform(delete("/api/hints/{id}", hints.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Hints> hintsList = hintsRepository.findAll();
        assertThat(hintsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
