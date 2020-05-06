package com.ep.app.service;

import com.ep.app.domain.Scores;
import com.ep.app.repository.ScoresRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Scores}.
 */
@Service
@Transactional
public class ScoresService {

    private final Logger log = LoggerFactory.getLogger(ScoresService.class);

    private final ScoresRepository scoresRepository;

    public ScoresService(ScoresRepository scoresRepository) {
        this.scoresRepository = scoresRepository;
    }

    /**
     * Save a scores.
     *
     * @param scores the entity to save.
     * @return the persisted entity.
     */
    public Scores save(Scores scores) {
        log.debug("Request to save Scores : {}", scores);
        return scoresRepository.save(scores);
    }

    /**
     * Get all the scores.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Scores> findAll() {
        log.debug("Request to get all Scores");
        return scoresRepository.findAll();
    }

    /**
     * Get one scores by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Scores> findOne(Long id) {
        log.debug("Request to get Scores : {}", id);
        return scoresRepository.findById(id);
    }

    /**
     * Delete the scores by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Scores : {}", id);
        scoresRepository.deleteById(id);
    }
}
