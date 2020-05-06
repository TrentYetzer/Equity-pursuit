package com.ep.app.service;

import com.ep.app.domain.Hints;
import com.ep.app.repository.HintsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Hints}.
 */
@Service
@Transactional
public class HintsService {

    private final Logger log = LoggerFactory.getLogger(HintsService.class);

    private final HintsRepository hintsRepository;

    public HintsService(HintsRepository hintsRepository) {
        this.hintsRepository = hintsRepository;
    }

    /**
     * Save a hints.
     *
     * @param hints the entity to save.
     * @return the persisted entity.
     */
    public Hints save(Hints hints) {
        log.debug("Request to save Hints : {}", hints);
        return hintsRepository.save(hints);
    }

    /**
     * Get all the hints.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Hints> findAll() {
        log.debug("Request to get all Hints");
        return hintsRepository.findAll();
    }

    /**
     * Get one hints by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Hints> findOne(Long id) {
        log.debug("Request to get Hints : {}", id);
        return hintsRepository.findById(id);
    }

    /**
     * Delete the hints by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Hints : {}", id);
        hintsRepository.deleteById(id);
    }
}
