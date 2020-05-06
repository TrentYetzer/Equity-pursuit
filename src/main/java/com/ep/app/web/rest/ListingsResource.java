package com.ep.app.web.rest;

import com.ep.app.domain.Listings;
import com.ep.app.service.ListingsService;
import com.ep.app.web.rest.errors.BadRequestAlertException;
import com.ep.app.service.dto.ListingsCriteria;
import com.ep.app.service.ListingsQueryService;

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
 * REST controller for managing {@link com.ep.app.domain.Listings}.
 */
@RestController
@RequestMapping("/api")
public class ListingsResource {

    private final Logger log = LoggerFactory.getLogger(ListingsResource.class);

    private static final String ENTITY_NAME = "listings";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ListingsService listingsService;

    private final ListingsQueryService listingsQueryService;

    public ListingsResource(ListingsService listingsService, ListingsQueryService listingsQueryService) {
        this.listingsService = listingsService;
        this.listingsQueryService = listingsQueryService;
    }

    /**
     * {@code POST  /listings} : Create a new listings.
     *
     * @param listings the listings to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new listings, or with status {@code 400 (Bad Request)} if the listings has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/listings")
    public ResponseEntity<Listings> createListings(@Valid @RequestBody Listings listings) throws URISyntaxException {
        log.debug("REST request to save Listings : {}", listings);
        if (listings.getId() != null) {
            throw new BadRequestAlertException("A new listings cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Listings result = listingsService.save(listings);
        return ResponseEntity.created(new URI("/api/listings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /listings} : Updates an existing listings.
     *
     * @param listings the listings to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated listings,
     * or with status {@code 400 (Bad Request)} if the listings is not valid,
     * or with status {@code 500 (Internal Server Error)} if the listings couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/listings")
    public ResponseEntity<Listings> updateListings(@Valid @RequestBody Listings listings) throws URISyntaxException {
        log.debug("REST request to update Listings : {}", listings);
        if (listings.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Listings result = listingsService.save(listings);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, listings.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /listings} : get all the listings.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of listings in body.
     */
    @GetMapping("/listings")
    public ResponseEntity<List<Listings>> getAllListings(ListingsCriteria criteria) {
        log.debug("REST request to get Listings by criteria: {}", criteria);
        List<Listings> entityList = listingsQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /listings/count} : count all the listings.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/listings/count")
    public ResponseEntity<Long> countListings(ListingsCriteria criteria) {
        log.debug("REST request to count Listings by criteria: {}", criteria);
        return ResponseEntity.ok().body(listingsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /listings/:id} : get the "id" listings.
     *
     * @param id the id of the listings to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the listings, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/listings/{id}")
    public ResponseEntity<Listings> getListings(@PathVariable Long id) {
        log.debug("REST request to get Listings : {}", id);
        Optional<Listings> listings = listingsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(listings);
    }

    /**
     * {@code DELETE  /listings/:id} : delete the "id" listings.
     *
     * @param id the id of the listings to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/listings/{id}")
    public ResponseEntity<Void> deleteListings(@PathVariable Long id) {
        log.debug("REST request to delete Listings : {}", id);
        listingsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
