package com.ep.app.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.ep.app.domain.Scores;
import com.ep.app.domain.*; // for static metamodels
import com.ep.app.repository.ScoresRepository;
import com.ep.app.service.dto.ScoresCriteria;

/**
 * Service for executing complex queries for {@link Scores} entities in the database.
 * The main input is a {@link ScoresCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Scores} or a {@link Page} of {@link Scores} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ScoresQueryService extends QueryService<Scores> {

    private final Logger log = LoggerFactory.getLogger(ScoresQueryService.class);

    private final ScoresRepository scoresRepository;

    public ScoresQueryService(ScoresRepository scoresRepository) {
        this.scoresRepository = scoresRepository;
    }

    /**
     * Return a {@link List} of {@link Scores} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Scores> findByCriteria(ScoresCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Scores> specification = createSpecification(criteria);
        return scoresRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Scores} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Scores> findByCriteria(ScoresCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Scores> specification = createSpecification(criteria);
        return scoresRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ScoresCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Scores> specification = createSpecification(criteria);
        return scoresRepository.count(specification);
    }

    /**
     * Function to convert {@link ScoresCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Scores> createSpecification(ScoresCriteria criteria) {
        Specification<Scores> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Scores_.id));
            }
            if (criteria.getScore() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getScore(), Scores_.score));
            }
            if (criteria.getGamesId() != null) {
                specification = specification.and(buildSpecification(criteria.getGamesId(),
                    root -> root.join(Scores_.games, JoinType.LEFT).get(Games_.id)));
            }
        }
        return specification;
    }
}
