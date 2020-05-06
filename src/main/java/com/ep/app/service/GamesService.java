package com.ep.app.service;

import com.ep.app.domain.Games;
import com.ep.app.repository.GamesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Games}.
 */
@Service
@Transactional
public class GamesService {

    private final Logger log = LoggerFactory.getLogger(GamesService.class);

    private final GamesRepository gamesRepository;

    public GamesService(GamesRepository gamesRepository) {
        this.gamesRepository = gamesRepository;
    }

    /**
     * Save a games.
     *
     * @param games the entity to save.
     * @return the persisted entity.
     */
    public Games save(Games games) {
        log.debug("Request to save Games : {}", games);
        return gamesRepository.save(games);
    }

    /**
     * Get all the games.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Games> findAll() {
        log.debug("Request to get all Games");
        return gamesRepository.findAll();
    }

    /**
     * Get one games by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Games> findOne(Long id) {
        log.debug("Request to get Games : {}", id);
        return gamesRepository.findById(id);
    }

    /**
     * Delete the games by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Games : {}", id);
        gamesRepository.deleteById(id);
    }
}
