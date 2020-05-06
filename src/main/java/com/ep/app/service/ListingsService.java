package com.ep.app.service;

import com.ep.app.domain.Listings;
import com.ep.app.repository.ListingsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Listings}.
 */
@Service
@Transactional
public class ListingsService {

    private final Logger log = LoggerFactory.getLogger(ListingsService.class);

    private final ListingsRepository listingsRepository;

    public ListingsService(ListingsRepository listingsRepository) {
        this.listingsRepository = listingsRepository;
    }

    /**
     * Save a listings.
     *
     * @param listings the entity to save.
     * @return the persisted entity.
     */
    public Listings save(Listings listings) {
        log.debug("Request to save Listings : {}", listings);
        return listingsRepository.save(listings);
    }

    /**
     * Get all the listings.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Listings> findAll() {
        log.debug("Request to get all Listings");
        return listingsRepository.findAll();
    }

    /**
     * Get one listings by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Listings> findOne(Long id) {
        log.debug("Request to get Listings : {}", id);
        return listingsRepository.findById(id);
    }

    /**
     * Delete the listings by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Listings : {}", id);
        listingsRepository.deleteById(id);
    }
}
