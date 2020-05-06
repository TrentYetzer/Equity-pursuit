package com.ep.app.web.rest;

import com.ep.app.EquityPursuitApp;
import com.ep.app.domain.Listings;
import com.ep.app.repository.ListingsRepository;
import com.ep.app.service.ListingsService;
import com.ep.app.service.dto.ListingsCriteria;
import com.ep.app.service.ListingsQueryService;

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
 * Integration tests for the {@link ListingsResource} REST controller.
 */
@SpringBootTest(classes = EquityPursuitApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class ListingsResourceIT {

    private static final Integer DEFAULT_ZPID = 1;
    private static final Integer UPDATED_ZPID = 2;
    private static final Integer SMALLER_ZPID = 1 - 1;

    private static final String DEFAULT_STREET = "AAAAAAAAAA";
    private static final String UPDATED_STREET = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_STATE = "BBBBBBBBBB";

    private static final Integer DEFAULT_ZIP_CODE = 1;
    private static final Integer UPDATED_ZIP_CODE = 2;
    private static final Integer SMALLER_ZIP_CODE = 1 - 1;

    private static final Double DEFAULT_ZESTIMATE = 0D;
    private static final Double UPDATED_ZESTIMATE = 1D;
    private static final Double SMALLER_ZESTIMATE = 0D - 1D;

    private static final Integer DEFAULT_ADDRESS = 1;
    private static final Integer UPDATED_ADDRESS = 2;
    private static final Integer SMALLER_ADDRESS = 1 - 1;

    private static final Double DEFAULT_LONGITUDE = 1D;
    private static final Double UPDATED_LONGITUDE = 2D;
    private static final Double SMALLER_LONGITUDE = 1D - 1D;

    private static final Double DEFAULT_LATITUDE = 1D;
    private static final Double UPDATED_LATITUDE = 2D;
    private static final Double SMALLER_LATITUDE = 1D - 1D;

    @Autowired
    private ListingsRepository listingsRepository;

    @Autowired
    private ListingsService listingsService;

    @Autowired
    private ListingsQueryService listingsQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restListingsMockMvc;

    private Listings listings;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Listings createEntity(EntityManager em) {
        Listings listings = new Listings()
            .zpid(DEFAULT_ZPID)
            .street(DEFAULT_STREET)
            .city(DEFAULT_CITY)
            .state(DEFAULT_STATE)
            .zipCode(DEFAULT_ZIP_CODE)
            .zestimate(DEFAULT_ZESTIMATE)
            .address(DEFAULT_ADDRESS)
            .longitude(DEFAULT_LONGITUDE)
            .latitude(DEFAULT_LATITUDE);
        return listings;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Listings createUpdatedEntity(EntityManager em) {
        Listings listings = new Listings()
            .zpid(UPDATED_ZPID)
            .street(UPDATED_STREET)
            .city(UPDATED_CITY)
            .state(UPDATED_STATE)
            .zipCode(UPDATED_ZIP_CODE)
            .zestimate(UPDATED_ZESTIMATE)
            .address(UPDATED_ADDRESS)
            .longitude(UPDATED_LONGITUDE)
            .latitude(UPDATED_LATITUDE);
        return listings;
    }

    @BeforeEach
    public void initTest() {
        listings = createEntity(em);
    }

    @Test
    @Transactional
    public void createListings() throws Exception {
        int databaseSizeBeforeCreate = listingsRepository.findAll().size();

        // Create the Listings
        restListingsMockMvc.perform(post("/api/listings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(listings)))
            .andExpect(status().isCreated());

        // Validate the Listings in the database
        List<Listings> listingsList = listingsRepository.findAll();
        assertThat(listingsList).hasSize(databaseSizeBeforeCreate + 1);
        Listings testListings = listingsList.get(listingsList.size() - 1);
        assertThat(testListings.getZpid()).isEqualTo(DEFAULT_ZPID);
        assertThat(testListings.getStreet()).isEqualTo(DEFAULT_STREET);
        assertThat(testListings.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testListings.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testListings.getZipCode()).isEqualTo(DEFAULT_ZIP_CODE);
        assertThat(testListings.getZestimate()).isEqualTo(DEFAULT_ZESTIMATE);
        assertThat(testListings.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testListings.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testListings.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
    }

    @Test
    @Transactional
    public void createListingsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = listingsRepository.findAll().size();

        // Create the Listings with an existing ID
        listings.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restListingsMockMvc.perform(post("/api/listings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(listings)))
            .andExpect(status().isBadRequest());

        // Validate the Listings in the database
        List<Listings> listingsList = listingsRepository.findAll();
        assertThat(listingsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkZpidIsRequired() throws Exception {
        int databaseSizeBeforeTest = listingsRepository.findAll().size();
        // set the field null
        listings.setZpid(null);

        // Create the Listings, which fails.

        restListingsMockMvc.perform(post("/api/listings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(listings)))
            .andExpect(status().isBadRequest());

        List<Listings> listingsList = listingsRepository.findAll();
        assertThat(listingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStreetIsRequired() throws Exception {
        int databaseSizeBeforeTest = listingsRepository.findAll().size();
        // set the field null
        listings.setStreet(null);

        // Create the Listings, which fails.

        restListingsMockMvc.perform(post("/api/listings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(listings)))
            .andExpect(status().isBadRequest());

        List<Listings> listingsList = listingsRepository.findAll();
        assertThat(listingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCityIsRequired() throws Exception {
        int databaseSizeBeforeTest = listingsRepository.findAll().size();
        // set the field null
        listings.setCity(null);

        // Create the Listings, which fails.

        restListingsMockMvc.perform(post("/api/listings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(listings)))
            .andExpect(status().isBadRequest());

        List<Listings> listingsList = listingsRepository.findAll();
        assertThat(listingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStateIsRequired() throws Exception {
        int databaseSizeBeforeTest = listingsRepository.findAll().size();
        // set the field null
        listings.setState(null);

        // Create the Listings, which fails.

        restListingsMockMvc.perform(post("/api/listings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(listings)))
            .andExpect(status().isBadRequest());

        List<Listings> listingsList = listingsRepository.findAll();
        assertThat(listingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkZipCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = listingsRepository.findAll().size();
        // set the field null
        listings.setZipCode(null);

        // Create the Listings, which fails.

        restListingsMockMvc.perform(post("/api/listings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(listings)))
            .andExpect(status().isBadRequest());

        List<Listings> listingsList = listingsRepository.findAll();
        assertThat(listingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkZestimateIsRequired() throws Exception {
        int databaseSizeBeforeTest = listingsRepository.findAll().size();
        // set the field null
        listings.setZestimate(null);

        // Create the Listings, which fails.

        restListingsMockMvc.perform(post("/api/listings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(listings)))
            .andExpect(status().isBadRequest());

        List<Listings> listingsList = listingsRepository.findAll();
        assertThat(listingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = listingsRepository.findAll().size();
        // set the field null
        listings.setAddress(null);

        // Create the Listings, which fails.

        restListingsMockMvc.perform(post("/api/listings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(listings)))
            .andExpect(status().isBadRequest());

        List<Listings> listingsList = listingsRepository.findAll();
        assertThat(listingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLongitudeIsRequired() throws Exception {
        int databaseSizeBeforeTest = listingsRepository.findAll().size();
        // set the field null
        listings.setLongitude(null);

        // Create the Listings, which fails.

        restListingsMockMvc.perform(post("/api/listings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(listings)))
            .andExpect(status().isBadRequest());

        List<Listings> listingsList = listingsRepository.findAll();
        assertThat(listingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLatitudeIsRequired() throws Exception {
        int databaseSizeBeforeTest = listingsRepository.findAll().size();
        // set the field null
        listings.setLatitude(null);

        // Create the Listings, which fails.

        restListingsMockMvc.perform(post("/api/listings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(listings)))
            .andExpect(status().isBadRequest());

        List<Listings> listingsList = listingsRepository.findAll();
        assertThat(listingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllListings() throws Exception {
        // Initialize the database
        listingsRepository.saveAndFlush(listings);

        // Get all the listingsList
        restListingsMockMvc.perform(get("/api/listings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(listings.getId().intValue())))
            .andExpect(jsonPath("$.[*].zpid").value(hasItem(DEFAULT_ZPID)))
            .andExpect(jsonPath("$.[*].street").value(hasItem(DEFAULT_STREET)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)))
            .andExpect(jsonPath("$.[*].zipCode").value(hasItem(DEFAULT_ZIP_CODE)))
            .andExpect(jsonPath("$.[*].zestimate").value(hasItem(DEFAULT_ZESTIMATE.doubleValue())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getListings() throws Exception {
        // Initialize the database
        listingsRepository.saveAndFlush(listings);

        // Get the listings
        restListingsMockMvc.perform(get("/api/listings/{id}", listings.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(listings.getId().intValue()))
            .andExpect(jsonPath("$.zpid").value(DEFAULT_ZPID))
            .andExpect(jsonPath("$.street").value(DEFAULT_STREET))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE))
            .andExpect(jsonPath("$.zipCode").value(DEFAULT_ZIP_CODE))
            .andExpect(jsonPath("$.zestimate").value(DEFAULT_ZESTIMATE.doubleValue()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.doubleValue()))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.doubleValue()));
    }


    @Test
    @Transactional
    public void getListingsByIdFiltering() throws Exception {
        // Initialize the database
        listingsRepository.saveAndFlush(listings);

        Long id = listings.getId();

        defaultListingsShouldBeFound("id.equals=" + id);
        defaultListingsShouldNotBeFound("id.notEquals=" + id);

        defaultListingsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultListingsShouldNotBeFound("id.greaterThan=" + id);

        defaultListingsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultListingsShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllListingsByZpidIsEqualToSomething() throws Exception {
        // Initialize the database
        listingsRepository.saveAndFlush(listings);

        // Get all the listingsList where zpid equals to DEFAULT_ZPID
        defaultListingsShouldBeFound("zpid.equals=" + DEFAULT_ZPID);

        // Get all the listingsList where zpid equals to UPDATED_ZPID
        defaultListingsShouldNotBeFound("zpid.equals=" + UPDATED_ZPID);
    }

    @Test
    @Transactional
    public void getAllListingsByZpidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        listingsRepository.saveAndFlush(listings);

        // Get all the listingsList where zpid not equals to DEFAULT_ZPID
        defaultListingsShouldNotBeFound("zpid.notEquals=" + DEFAULT_ZPID);

        // Get all the listingsList where zpid not equals to UPDATED_ZPID
        defaultListingsShouldBeFound("zpid.notEquals=" + UPDATED_ZPID);
    }

    @Test
    @Transactional
    public void getAllListingsByZpidIsInShouldWork() throws Exception {
        // Initialize the database
        listingsRepository.saveAndFlush(listings);

        // Get all the listingsList where zpid in DEFAULT_ZPID or UPDATED_ZPID
        defaultListingsShouldBeFound("zpid.in=" + DEFAULT_ZPID + "," + UPDATED_ZPID);

        // Get all the listingsList where zpid equals to UPDATED_ZPID
        defaultListingsShouldNotBeFound("zpid.in=" + UPDATED_ZPID);
    }

    @Test
    @Transactional
    public void getAllListingsByZpidIsNullOrNotNull() throws Exception {
        // Initialize the database
        listingsRepository.saveAndFlush(listings);

        // Get all the listingsList where zpid is not null
        defaultListingsShouldBeFound("zpid.specified=true");

        // Get all the listingsList where zpid is null
        defaultListingsShouldNotBeFound("zpid.specified=false");
    }

    @Test
    @Transactional
    public void getAllListingsByZpidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        listingsRepository.saveAndFlush(listings);

        // Get all the listingsList where zpid is greater than or equal to DEFAULT_ZPID
        defaultListingsShouldBeFound("zpid.greaterThanOrEqual=" + DEFAULT_ZPID);

        // Get all the listingsList where zpid is greater than or equal to UPDATED_ZPID
        defaultListingsShouldNotBeFound("zpid.greaterThanOrEqual=" + UPDATED_ZPID);
    }

    @Test
    @Transactional
    public void getAllListingsByZpidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        listingsRepository.saveAndFlush(listings);

        // Get all the listingsList where zpid is less than or equal to DEFAULT_ZPID
        defaultListingsShouldBeFound("zpid.lessThanOrEqual=" + DEFAULT_ZPID);

        // Get all the listingsList where zpid is less than or equal to SMALLER_ZPID
        defaultListingsShouldNotBeFound("zpid.lessThanOrEqual=" + SMALLER_ZPID);
    }

    @Test
    @Transactional
    public void getAllListingsByZpidIsLessThanSomething() throws Exception {
        // Initialize the database
        listingsRepository.saveAndFlush(listings);

        // Get all the listingsList where zpid is less than DEFAULT_ZPID
        defaultListingsShouldNotBeFound("zpid.lessThan=" + DEFAULT_ZPID);

        // Get all the listingsList where zpid is less than UPDATED_ZPID
        defaultListingsShouldBeFound("zpid.lessThan=" + UPDATED_ZPID);
    }

    @Test
    @Transactional
    public void getAllListingsByZpidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        listingsRepository.saveAndFlush(listings);

        // Get all the listingsList where zpid is greater than DEFAULT_ZPID
        defaultListingsShouldNotBeFound("zpid.greaterThan=" + DEFAULT_ZPID);

        // Get all the listingsList where zpid is greater than SMALLER_ZPID
        defaultListingsShouldBeFound("zpid.greaterThan=" + SMALLER_ZPID);
    }


    @Test
    @Transactional
    public void getAllListingsByStreetIsEqualToSomething() throws Exception {
        // Initialize the database
        listingsRepository.saveAndFlush(listings);

        // Get all the listingsList where street equals to DEFAULT_STREET
        defaultListingsShouldBeFound("street.equals=" + DEFAULT_STREET);

        // Get all the listingsList where street equals to UPDATED_STREET
        defaultListingsShouldNotBeFound("street.equals=" + UPDATED_STREET);
    }

    @Test
    @Transactional
    public void getAllListingsByStreetIsNotEqualToSomething() throws Exception {
        // Initialize the database
        listingsRepository.saveAndFlush(listings);

        // Get all the listingsList where street not equals to DEFAULT_STREET
        defaultListingsShouldNotBeFound("street.notEquals=" + DEFAULT_STREET);

        // Get all the listingsList where street not equals to UPDATED_STREET
        defaultListingsShouldBeFound("street.notEquals=" + UPDATED_STREET);
    }

    @Test
    @Transactional
    public void getAllListingsByStreetIsInShouldWork() throws Exception {
        // Initialize the database
        listingsRepository.saveAndFlush(listings);

        // Get all the listingsList where street in DEFAULT_STREET or UPDATED_STREET
        defaultListingsShouldBeFound("street.in=" + DEFAULT_STREET + "," + UPDATED_STREET);

        // Get all the listingsList where street equals to UPDATED_STREET
        defaultListingsShouldNotBeFound("street.in=" + UPDATED_STREET);
    }

    @Test
    @Transactional
    public void getAllListingsByStreetIsNullOrNotNull() throws Exception {
        // Initialize the database
        listingsRepository.saveAndFlush(listings);

        // Get all the listingsList where street is not null
        defaultListingsShouldBeFound("street.specified=true");

        // Get all the listingsList where street is null
        defaultListingsShouldNotBeFound("street.specified=false");
    }
                @Test
    @Transactional
    public void getAllListingsByStreetContainsSomething() throws Exception {
        // Initialize the database
        listingsRepository.saveAndFlush(listings);

        // Get all the listingsList where street contains DEFAULT_STREET
        defaultListingsShouldBeFound("street.contains=" + DEFAULT_STREET);

        // Get all the listingsList where street contains UPDATED_STREET
        defaultListingsShouldNotBeFound("street.contains=" + UPDATED_STREET);
    }

    @Test
    @Transactional
    public void getAllListingsByStreetNotContainsSomething() throws Exception {
        // Initialize the database
        listingsRepository.saveAndFlush(listings);

        // Get all the listingsList where street does not contain DEFAULT_STREET
        defaultListingsShouldNotBeFound("street.doesNotContain=" + DEFAULT_STREET);

        // Get all the listingsList where street does not contain UPDATED_STREET
        defaultListingsShouldBeFound("street.doesNotContain=" + UPDATED_STREET);
    }


    @Test
    @Transactional
    public void getAllListingsByCityIsEqualToSomething() throws Exception {
        // Initialize the database
        listingsRepository.saveAndFlush(listings);

        // Get all the listingsList where city equals to DEFAULT_CITY
        defaultListingsShouldBeFound("city.equals=" + DEFAULT_CITY);

        // Get all the listingsList where city equals to UPDATED_CITY
        defaultListingsShouldNotBeFound("city.equals=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    public void getAllListingsByCityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        listingsRepository.saveAndFlush(listings);

        // Get all the listingsList where city not equals to DEFAULT_CITY
        defaultListingsShouldNotBeFound("city.notEquals=" + DEFAULT_CITY);

        // Get all the listingsList where city not equals to UPDATED_CITY
        defaultListingsShouldBeFound("city.notEquals=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    public void getAllListingsByCityIsInShouldWork() throws Exception {
        // Initialize the database
        listingsRepository.saveAndFlush(listings);

        // Get all the listingsList where city in DEFAULT_CITY or UPDATED_CITY
        defaultListingsShouldBeFound("city.in=" + DEFAULT_CITY + "," + UPDATED_CITY);

        // Get all the listingsList where city equals to UPDATED_CITY
        defaultListingsShouldNotBeFound("city.in=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    public void getAllListingsByCityIsNullOrNotNull() throws Exception {
        // Initialize the database
        listingsRepository.saveAndFlush(listings);

        // Get all the listingsList where city is not null
        defaultListingsShouldBeFound("city.specified=true");

        // Get all the listingsList where city is null
        defaultListingsShouldNotBeFound("city.specified=false");
    }
                @Test
    @Transactional
    public void getAllListingsByCityContainsSomething() throws Exception {
        // Initialize the database
        listingsRepository.saveAndFlush(listings);

        // Get all the listingsList where city contains DEFAULT_CITY
        defaultListingsShouldBeFound("city.contains=" + DEFAULT_CITY);

        // Get all the listingsList where city contains UPDATED_CITY
        defaultListingsShouldNotBeFound("city.contains=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    public void getAllListingsByCityNotContainsSomething() throws Exception {
        // Initialize the database
        listingsRepository.saveAndFlush(listings);

        // Get all the listingsList where city does not contain DEFAULT_CITY
        defaultListingsShouldNotBeFound("city.doesNotContain=" + DEFAULT_CITY);

        // Get all the listingsList where city does not contain UPDATED_CITY
        defaultListingsShouldBeFound("city.doesNotContain=" + UPDATED_CITY);
    }


    @Test
    @Transactional
    public void getAllListingsByStateIsEqualToSomething() throws Exception {
        // Initialize the database
        listingsRepository.saveAndFlush(listings);

        // Get all the listingsList where state equals to DEFAULT_STATE
        defaultListingsShouldBeFound("state.equals=" + DEFAULT_STATE);

        // Get all the listingsList where state equals to UPDATED_STATE
        defaultListingsShouldNotBeFound("state.equals=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    public void getAllListingsByStateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        listingsRepository.saveAndFlush(listings);

        // Get all the listingsList where state not equals to DEFAULT_STATE
        defaultListingsShouldNotBeFound("state.notEquals=" + DEFAULT_STATE);

        // Get all the listingsList where state not equals to UPDATED_STATE
        defaultListingsShouldBeFound("state.notEquals=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    public void getAllListingsByStateIsInShouldWork() throws Exception {
        // Initialize the database
        listingsRepository.saveAndFlush(listings);

        // Get all the listingsList where state in DEFAULT_STATE or UPDATED_STATE
        defaultListingsShouldBeFound("state.in=" + DEFAULT_STATE + "," + UPDATED_STATE);

        // Get all the listingsList where state equals to UPDATED_STATE
        defaultListingsShouldNotBeFound("state.in=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    public void getAllListingsByStateIsNullOrNotNull() throws Exception {
        // Initialize the database
        listingsRepository.saveAndFlush(listings);

        // Get all the listingsList where state is not null
        defaultListingsShouldBeFound("state.specified=true");

        // Get all the listingsList where state is null
        defaultListingsShouldNotBeFound("state.specified=false");
    }
                @Test
    @Transactional
    public void getAllListingsByStateContainsSomething() throws Exception {
        // Initialize the database
        listingsRepository.saveAndFlush(listings);

        // Get all the listingsList where state contains DEFAULT_STATE
        defaultListingsShouldBeFound("state.contains=" + DEFAULT_STATE);

        // Get all the listingsList where state contains UPDATED_STATE
        defaultListingsShouldNotBeFound("state.contains=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    public void getAllListingsByStateNotContainsSomething() throws Exception {
        // Initialize the database
        listingsRepository.saveAndFlush(listings);

        // Get all the listingsList where state does not contain DEFAULT_STATE
        defaultListingsShouldNotBeFound("state.doesNotContain=" + DEFAULT_STATE);

        // Get all the listingsList where state does not contain UPDATED_STATE
        defaultListingsShouldBeFound("state.doesNotContain=" + UPDATED_STATE);
    }


    @Test
    @Transactional
    public void getAllListingsByZipCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        listingsRepository.saveAndFlush(listings);

        // Get all the listingsList where zipCode equals to DEFAULT_ZIP_CODE
        defaultListingsShouldBeFound("zipCode.equals=" + DEFAULT_ZIP_CODE);

        // Get all the listingsList where zipCode equals to UPDATED_ZIP_CODE
        defaultListingsShouldNotBeFound("zipCode.equals=" + UPDATED_ZIP_CODE);
    }

    @Test
    @Transactional
    public void getAllListingsByZipCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        listingsRepository.saveAndFlush(listings);

        // Get all the listingsList where zipCode not equals to DEFAULT_ZIP_CODE
        defaultListingsShouldNotBeFound("zipCode.notEquals=" + DEFAULT_ZIP_CODE);

        // Get all the listingsList where zipCode not equals to UPDATED_ZIP_CODE
        defaultListingsShouldBeFound("zipCode.notEquals=" + UPDATED_ZIP_CODE);
    }

    @Test
    @Transactional
    public void getAllListingsByZipCodeIsInShouldWork() throws Exception {
        // Initialize the database
        listingsRepository.saveAndFlush(listings);

        // Get all the listingsList where zipCode in DEFAULT_ZIP_CODE or UPDATED_ZIP_CODE
        defaultListingsShouldBeFound("zipCode.in=" + DEFAULT_ZIP_CODE + "," + UPDATED_ZIP_CODE);

        // Get all the listingsList where zipCode equals to UPDATED_ZIP_CODE
        defaultListingsShouldNotBeFound("zipCode.in=" + UPDATED_ZIP_CODE);
    }

    @Test
    @Transactional
    public void getAllListingsByZipCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        listingsRepository.saveAndFlush(listings);

        // Get all the listingsList where zipCode is not null
        defaultListingsShouldBeFound("zipCode.specified=true");

        // Get all the listingsList where zipCode is null
        defaultListingsShouldNotBeFound("zipCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllListingsByZipCodeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        listingsRepository.saveAndFlush(listings);

        // Get all the listingsList where zipCode is greater than or equal to DEFAULT_ZIP_CODE
        defaultListingsShouldBeFound("zipCode.greaterThanOrEqual=" + DEFAULT_ZIP_CODE);

        // Get all the listingsList where zipCode is greater than or equal to UPDATED_ZIP_CODE
        defaultListingsShouldNotBeFound("zipCode.greaterThanOrEqual=" + UPDATED_ZIP_CODE);
    }

    @Test
    @Transactional
    public void getAllListingsByZipCodeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        listingsRepository.saveAndFlush(listings);

        // Get all the listingsList where zipCode is less than or equal to DEFAULT_ZIP_CODE
        defaultListingsShouldBeFound("zipCode.lessThanOrEqual=" + DEFAULT_ZIP_CODE);

        // Get all the listingsList where zipCode is less than or equal to SMALLER_ZIP_CODE
        defaultListingsShouldNotBeFound("zipCode.lessThanOrEqual=" + SMALLER_ZIP_CODE);
    }

    @Test
    @Transactional
    public void getAllListingsByZipCodeIsLessThanSomething() throws Exception {
        // Initialize the database
        listingsRepository.saveAndFlush(listings);

        // Get all the listingsList where zipCode is less than DEFAULT_ZIP_CODE
        defaultListingsShouldNotBeFound("zipCode.lessThan=" + DEFAULT_ZIP_CODE);

        // Get all the listingsList where zipCode is less than UPDATED_ZIP_CODE
        defaultListingsShouldBeFound("zipCode.lessThan=" + UPDATED_ZIP_CODE);
    }

    @Test
    @Transactional
    public void getAllListingsByZipCodeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        listingsRepository.saveAndFlush(listings);

        // Get all the listingsList where zipCode is greater than DEFAULT_ZIP_CODE
        defaultListingsShouldNotBeFound("zipCode.greaterThan=" + DEFAULT_ZIP_CODE);

        // Get all the listingsList where zipCode is greater than SMALLER_ZIP_CODE
        defaultListingsShouldBeFound("zipCode.greaterThan=" + SMALLER_ZIP_CODE);
    }


    @Test
    @Transactional
    public void getAllListingsByZestimateIsEqualToSomething() throws Exception {
        // Initialize the database
        listingsRepository.saveAndFlush(listings);

        // Get all the listingsList where zestimate equals to DEFAULT_ZESTIMATE
        defaultListingsShouldBeFound("zestimate.equals=" + DEFAULT_ZESTIMATE);

        // Get all the listingsList where zestimate equals to UPDATED_ZESTIMATE
        defaultListingsShouldNotBeFound("zestimate.equals=" + UPDATED_ZESTIMATE);
    }

    @Test
    @Transactional
    public void getAllListingsByZestimateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        listingsRepository.saveAndFlush(listings);

        // Get all the listingsList where zestimate not equals to DEFAULT_ZESTIMATE
        defaultListingsShouldNotBeFound("zestimate.notEquals=" + DEFAULT_ZESTIMATE);

        // Get all the listingsList where zestimate not equals to UPDATED_ZESTIMATE
        defaultListingsShouldBeFound("zestimate.notEquals=" + UPDATED_ZESTIMATE);
    }

    @Test
    @Transactional
    public void getAllListingsByZestimateIsInShouldWork() throws Exception {
        // Initialize the database
        listingsRepository.saveAndFlush(listings);

        // Get all the listingsList where zestimate in DEFAULT_ZESTIMATE or UPDATED_ZESTIMATE
        defaultListingsShouldBeFound("zestimate.in=" + DEFAULT_ZESTIMATE + "," + UPDATED_ZESTIMATE);

        // Get all the listingsList where zestimate equals to UPDATED_ZESTIMATE
        defaultListingsShouldNotBeFound("zestimate.in=" + UPDATED_ZESTIMATE);
    }

    @Test
    @Transactional
    public void getAllListingsByZestimateIsNullOrNotNull() throws Exception {
        // Initialize the database
        listingsRepository.saveAndFlush(listings);

        // Get all the listingsList where zestimate is not null
        defaultListingsShouldBeFound("zestimate.specified=true");

        // Get all the listingsList where zestimate is null
        defaultListingsShouldNotBeFound("zestimate.specified=false");
    }

    @Test
    @Transactional
    public void getAllListingsByZestimateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        listingsRepository.saveAndFlush(listings);

        // Get all the listingsList where zestimate is greater than or equal to DEFAULT_ZESTIMATE
        defaultListingsShouldBeFound("zestimate.greaterThanOrEqual=" + DEFAULT_ZESTIMATE);

        // Get all the listingsList where zestimate is greater than or equal to UPDATED_ZESTIMATE
        defaultListingsShouldNotBeFound("zestimate.greaterThanOrEqual=" + UPDATED_ZESTIMATE);
    }

    @Test
    @Transactional
    public void getAllListingsByZestimateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        listingsRepository.saveAndFlush(listings);

        // Get all the listingsList where zestimate is less than or equal to DEFAULT_ZESTIMATE
        defaultListingsShouldBeFound("zestimate.lessThanOrEqual=" + DEFAULT_ZESTIMATE);

        // Get all the listingsList where zestimate is less than or equal to SMALLER_ZESTIMATE
        defaultListingsShouldNotBeFound("zestimate.lessThanOrEqual=" + SMALLER_ZESTIMATE);
    }

    @Test
    @Transactional
    public void getAllListingsByZestimateIsLessThanSomething() throws Exception {
        // Initialize the database
        listingsRepository.saveAndFlush(listings);

        // Get all the listingsList where zestimate is less than DEFAULT_ZESTIMATE
        defaultListingsShouldNotBeFound("zestimate.lessThan=" + DEFAULT_ZESTIMATE);

        // Get all the listingsList where zestimate is less than UPDATED_ZESTIMATE
        defaultListingsShouldBeFound("zestimate.lessThan=" + UPDATED_ZESTIMATE);
    }

    @Test
    @Transactional
    public void getAllListingsByZestimateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        listingsRepository.saveAndFlush(listings);

        // Get all the listingsList where zestimate is greater than DEFAULT_ZESTIMATE
        defaultListingsShouldNotBeFound("zestimate.greaterThan=" + DEFAULT_ZESTIMATE);

        // Get all the listingsList where zestimate is greater than SMALLER_ZESTIMATE
        defaultListingsShouldBeFound("zestimate.greaterThan=" + SMALLER_ZESTIMATE);
    }


    @Test
    @Transactional
    public void getAllListingsByAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        listingsRepository.saveAndFlush(listings);

        // Get all the listingsList where address equals to DEFAULT_ADDRESS
        defaultListingsShouldBeFound("address.equals=" + DEFAULT_ADDRESS);

        // Get all the listingsList where address equals to UPDATED_ADDRESS
        defaultListingsShouldNotBeFound("address.equals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllListingsByAddressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        listingsRepository.saveAndFlush(listings);

        // Get all the listingsList where address not equals to DEFAULT_ADDRESS
        defaultListingsShouldNotBeFound("address.notEquals=" + DEFAULT_ADDRESS);

        // Get all the listingsList where address not equals to UPDATED_ADDRESS
        defaultListingsShouldBeFound("address.notEquals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllListingsByAddressIsInShouldWork() throws Exception {
        // Initialize the database
        listingsRepository.saveAndFlush(listings);

        // Get all the listingsList where address in DEFAULT_ADDRESS or UPDATED_ADDRESS
        defaultListingsShouldBeFound("address.in=" + DEFAULT_ADDRESS + "," + UPDATED_ADDRESS);

        // Get all the listingsList where address equals to UPDATED_ADDRESS
        defaultListingsShouldNotBeFound("address.in=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllListingsByAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        listingsRepository.saveAndFlush(listings);

        // Get all the listingsList where address is not null
        defaultListingsShouldBeFound("address.specified=true");

        // Get all the listingsList where address is null
        defaultListingsShouldNotBeFound("address.specified=false");
    }

    @Test
    @Transactional
    public void getAllListingsByAddressIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        listingsRepository.saveAndFlush(listings);

        // Get all the listingsList where address is greater than or equal to DEFAULT_ADDRESS
        defaultListingsShouldBeFound("address.greaterThanOrEqual=" + DEFAULT_ADDRESS);

        // Get all the listingsList where address is greater than or equal to UPDATED_ADDRESS
        defaultListingsShouldNotBeFound("address.greaterThanOrEqual=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllListingsByAddressIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        listingsRepository.saveAndFlush(listings);

        // Get all the listingsList where address is less than or equal to DEFAULT_ADDRESS
        defaultListingsShouldBeFound("address.lessThanOrEqual=" + DEFAULT_ADDRESS);

        // Get all the listingsList where address is less than or equal to SMALLER_ADDRESS
        defaultListingsShouldNotBeFound("address.lessThanOrEqual=" + SMALLER_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllListingsByAddressIsLessThanSomething() throws Exception {
        // Initialize the database
        listingsRepository.saveAndFlush(listings);

        // Get all the listingsList where address is less than DEFAULT_ADDRESS
        defaultListingsShouldNotBeFound("address.lessThan=" + DEFAULT_ADDRESS);

        // Get all the listingsList where address is less than UPDATED_ADDRESS
        defaultListingsShouldBeFound("address.lessThan=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllListingsByAddressIsGreaterThanSomething() throws Exception {
        // Initialize the database
        listingsRepository.saveAndFlush(listings);

        // Get all the listingsList where address is greater than DEFAULT_ADDRESS
        defaultListingsShouldNotBeFound("address.greaterThan=" + DEFAULT_ADDRESS);

        // Get all the listingsList where address is greater than SMALLER_ADDRESS
        defaultListingsShouldBeFound("address.greaterThan=" + SMALLER_ADDRESS);
    }


    @Test
    @Transactional
    public void getAllListingsByLongitudeIsEqualToSomething() throws Exception {
        // Initialize the database
        listingsRepository.saveAndFlush(listings);

        // Get all the listingsList where longitude equals to DEFAULT_LONGITUDE
        defaultListingsShouldBeFound("longitude.equals=" + DEFAULT_LONGITUDE);

        // Get all the listingsList where longitude equals to UPDATED_LONGITUDE
        defaultListingsShouldNotBeFound("longitude.equals=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    public void getAllListingsByLongitudeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        listingsRepository.saveAndFlush(listings);

        // Get all the listingsList where longitude not equals to DEFAULT_LONGITUDE
        defaultListingsShouldNotBeFound("longitude.notEquals=" + DEFAULT_LONGITUDE);

        // Get all the listingsList where longitude not equals to UPDATED_LONGITUDE
        defaultListingsShouldBeFound("longitude.notEquals=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    public void getAllListingsByLongitudeIsInShouldWork() throws Exception {
        // Initialize the database
        listingsRepository.saveAndFlush(listings);

        // Get all the listingsList where longitude in DEFAULT_LONGITUDE or UPDATED_LONGITUDE
        defaultListingsShouldBeFound("longitude.in=" + DEFAULT_LONGITUDE + "," + UPDATED_LONGITUDE);

        // Get all the listingsList where longitude equals to UPDATED_LONGITUDE
        defaultListingsShouldNotBeFound("longitude.in=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    public void getAllListingsByLongitudeIsNullOrNotNull() throws Exception {
        // Initialize the database
        listingsRepository.saveAndFlush(listings);

        // Get all the listingsList where longitude is not null
        defaultListingsShouldBeFound("longitude.specified=true");

        // Get all the listingsList where longitude is null
        defaultListingsShouldNotBeFound("longitude.specified=false");
    }

    @Test
    @Transactional
    public void getAllListingsByLongitudeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        listingsRepository.saveAndFlush(listings);

        // Get all the listingsList where longitude is greater than or equal to DEFAULT_LONGITUDE
        defaultListingsShouldBeFound("longitude.greaterThanOrEqual=" + DEFAULT_LONGITUDE);

        // Get all the listingsList where longitude is greater than or equal to UPDATED_LONGITUDE
        defaultListingsShouldNotBeFound("longitude.greaterThanOrEqual=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    public void getAllListingsByLongitudeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        listingsRepository.saveAndFlush(listings);

        // Get all the listingsList where longitude is less than or equal to DEFAULT_LONGITUDE
        defaultListingsShouldBeFound("longitude.lessThanOrEqual=" + DEFAULT_LONGITUDE);

        // Get all the listingsList where longitude is less than or equal to SMALLER_LONGITUDE
        defaultListingsShouldNotBeFound("longitude.lessThanOrEqual=" + SMALLER_LONGITUDE);
    }

    @Test
    @Transactional
    public void getAllListingsByLongitudeIsLessThanSomething() throws Exception {
        // Initialize the database
        listingsRepository.saveAndFlush(listings);

        // Get all the listingsList where longitude is less than DEFAULT_LONGITUDE
        defaultListingsShouldNotBeFound("longitude.lessThan=" + DEFAULT_LONGITUDE);

        // Get all the listingsList where longitude is less than UPDATED_LONGITUDE
        defaultListingsShouldBeFound("longitude.lessThan=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    public void getAllListingsByLongitudeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        listingsRepository.saveAndFlush(listings);

        // Get all the listingsList where longitude is greater than DEFAULT_LONGITUDE
        defaultListingsShouldNotBeFound("longitude.greaterThan=" + DEFAULT_LONGITUDE);

        // Get all the listingsList where longitude is greater than SMALLER_LONGITUDE
        defaultListingsShouldBeFound("longitude.greaterThan=" + SMALLER_LONGITUDE);
    }


    @Test
    @Transactional
    public void getAllListingsByLatitudeIsEqualToSomething() throws Exception {
        // Initialize the database
        listingsRepository.saveAndFlush(listings);

        // Get all the listingsList where latitude equals to DEFAULT_LATITUDE
        defaultListingsShouldBeFound("latitude.equals=" + DEFAULT_LATITUDE);

        // Get all the listingsList where latitude equals to UPDATED_LATITUDE
        defaultListingsShouldNotBeFound("latitude.equals=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    public void getAllListingsByLatitudeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        listingsRepository.saveAndFlush(listings);

        // Get all the listingsList where latitude not equals to DEFAULT_LATITUDE
        defaultListingsShouldNotBeFound("latitude.notEquals=" + DEFAULT_LATITUDE);

        // Get all the listingsList where latitude not equals to UPDATED_LATITUDE
        defaultListingsShouldBeFound("latitude.notEquals=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    public void getAllListingsByLatitudeIsInShouldWork() throws Exception {
        // Initialize the database
        listingsRepository.saveAndFlush(listings);

        // Get all the listingsList where latitude in DEFAULT_LATITUDE or UPDATED_LATITUDE
        defaultListingsShouldBeFound("latitude.in=" + DEFAULT_LATITUDE + "," + UPDATED_LATITUDE);

        // Get all the listingsList where latitude equals to UPDATED_LATITUDE
        defaultListingsShouldNotBeFound("latitude.in=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    public void getAllListingsByLatitudeIsNullOrNotNull() throws Exception {
        // Initialize the database
        listingsRepository.saveAndFlush(listings);

        // Get all the listingsList where latitude is not null
        defaultListingsShouldBeFound("latitude.specified=true");

        // Get all the listingsList where latitude is null
        defaultListingsShouldNotBeFound("latitude.specified=false");
    }

    @Test
    @Transactional
    public void getAllListingsByLatitudeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        listingsRepository.saveAndFlush(listings);

        // Get all the listingsList where latitude is greater than or equal to DEFAULT_LATITUDE
        defaultListingsShouldBeFound("latitude.greaterThanOrEqual=" + DEFAULT_LATITUDE);

        // Get all the listingsList where latitude is greater than or equal to UPDATED_LATITUDE
        defaultListingsShouldNotBeFound("latitude.greaterThanOrEqual=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    public void getAllListingsByLatitudeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        listingsRepository.saveAndFlush(listings);

        // Get all the listingsList where latitude is less than or equal to DEFAULT_LATITUDE
        defaultListingsShouldBeFound("latitude.lessThanOrEqual=" + DEFAULT_LATITUDE);

        // Get all the listingsList where latitude is less than or equal to SMALLER_LATITUDE
        defaultListingsShouldNotBeFound("latitude.lessThanOrEqual=" + SMALLER_LATITUDE);
    }

    @Test
    @Transactional
    public void getAllListingsByLatitudeIsLessThanSomething() throws Exception {
        // Initialize the database
        listingsRepository.saveAndFlush(listings);

        // Get all the listingsList where latitude is less than DEFAULT_LATITUDE
        defaultListingsShouldNotBeFound("latitude.lessThan=" + DEFAULT_LATITUDE);

        // Get all the listingsList where latitude is less than UPDATED_LATITUDE
        defaultListingsShouldBeFound("latitude.lessThan=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    public void getAllListingsByLatitudeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        listingsRepository.saveAndFlush(listings);

        // Get all the listingsList where latitude is greater than DEFAULT_LATITUDE
        defaultListingsShouldNotBeFound("latitude.greaterThan=" + DEFAULT_LATITUDE);

        // Get all the listingsList where latitude is greater than SMALLER_LATITUDE
        defaultListingsShouldBeFound("latitude.greaterThan=" + SMALLER_LATITUDE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultListingsShouldBeFound(String filter) throws Exception {
        restListingsMockMvc.perform(get("/api/listings?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(listings.getId().intValue())))
            .andExpect(jsonPath("$.[*].zpid").value(hasItem(DEFAULT_ZPID)))
            .andExpect(jsonPath("$.[*].street").value(hasItem(DEFAULT_STREET)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)))
            .andExpect(jsonPath("$.[*].zipCode").value(hasItem(DEFAULT_ZIP_CODE)))
            .andExpect(jsonPath("$.[*].zestimate").value(hasItem(DEFAULT_ZESTIMATE.doubleValue())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())));

        // Check, that the count call also returns 1
        restListingsMockMvc.perform(get("/api/listings/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultListingsShouldNotBeFound(String filter) throws Exception {
        restListingsMockMvc.perform(get("/api/listings?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restListingsMockMvc.perform(get("/api/listings/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingListings() throws Exception {
        // Get the listings
        restListingsMockMvc.perform(get("/api/listings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateListings() throws Exception {
        // Initialize the database
        listingsService.save(listings);

        int databaseSizeBeforeUpdate = listingsRepository.findAll().size();

        // Update the listings
        Listings updatedListings = listingsRepository.findById(listings.getId()).get();
        // Disconnect from session so that the updates on updatedListings are not directly saved in db
        em.detach(updatedListings);
        updatedListings
            .zpid(UPDATED_ZPID)
            .street(UPDATED_STREET)
            .city(UPDATED_CITY)
            .state(UPDATED_STATE)
            .zipCode(UPDATED_ZIP_CODE)
            .zestimate(UPDATED_ZESTIMATE)
            .address(UPDATED_ADDRESS)
            .longitude(UPDATED_LONGITUDE)
            .latitude(UPDATED_LATITUDE);

        restListingsMockMvc.perform(put("/api/listings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedListings)))
            .andExpect(status().isOk());

        // Validate the Listings in the database
        List<Listings> listingsList = listingsRepository.findAll();
        assertThat(listingsList).hasSize(databaseSizeBeforeUpdate);
        Listings testListings = listingsList.get(listingsList.size() - 1);
        assertThat(testListings.getZpid()).isEqualTo(UPDATED_ZPID);
        assertThat(testListings.getStreet()).isEqualTo(UPDATED_STREET);
        assertThat(testListings.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testListings.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testListings.getZipCode()).isEqualTo(UPDATED_ZIP_CODE);
        assertThat(testListings.getZestimate()).isEqualTo(UPDATED_ZESTIMATE);
        assertThat(testListings.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testListings.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testListings.getLatitude()).isEqualTo(UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    public void updateNonExistingListings() throws Exception {
        int databaseSizeBeforeUpdate = listingsRepository.findAll().size();

        // Create the Listings

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restListingsMockMvc.perform(put("/api/listings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(listings)))
            .andExpect(status().isBadRequest());

        // Validate the Listings in the database
        List<Listings> listingsList = listingsRepository.findAll();
        assertThat(listingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteListings() throws Exception {
        // Initialize the database
        listingsService.save(listings);

        int databaseSizeBeforeDelete = listingsRepository.findAll().size();

        // Delete the listings
        restListingsMockMvc.perform(delete("/api/listings/{id}", listings.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Listings> listingsList = listingsRepository.findAll();
        assertThat(listingsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
