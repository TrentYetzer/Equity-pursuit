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

import com.ep.app.domain.Games;
import com.ep.app.domain.*; // for static metamodels
import com.ep.app.repository.GamesRepository;
import com.ep.app.service.dto.GamesCriteria;

/**
 * Service for executing complex queries for {@link Games} entities in the database.
 * The main input is a {@link GamesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Games} or a {@link Page} of {@link Games} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class GamesQueryService extends QueryService<Games> {

    private final Logger log = LoggerFactory.getLogger(GamesQueryService.class);

    private final GamesRepository gamesRepository;

    public GamesQueryService(GamesRepository gamesRepository) {
        this.gamesRepository = gamesRepository;
    }

    /**
     * Return a {@link List} of {@link Games} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Games> findByCriteria(GamesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Games> specification = createSpecification(criteria);
        return gamesRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Games} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Games> findByCriteria(GamesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Games> specification = createSpecification(criteria);
        return gamesRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(GamesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Games> specification = createSpecification(criteria);
        return gamesRepository.count(specification);
    }

    /**
     * Function to convert {@link GamesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Games> createSpecification(GamesCriteria criteria) {
        Specification<Games> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Games_.id));
            }
            if (criteria.getPlaytime() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPlaytime(), Games_.playtime));
            }
            if (criteria.getListingList() != null) {
                specification = specification.and(buildStringSpecification(criteria.getListingList(), Games_.listingList));
            }
            if (criteria.getScore() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getScore(), Games_.score));
            }
            if (criteria.getTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTime(), Games_.time));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserId(),
                    root -> root.join(Games_.user, JoinType.LEFT).get(User_.id)));
            }
        }
        return specification;
    }
}
