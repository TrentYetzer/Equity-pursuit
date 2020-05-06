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

import com.ep.app.domain.Hints;
import com.ep.app.domain.*; // for static metamodels
import com.ep.app.repository.HintsRepository;
import com.ep.app.service.dto.HintsCriteria;

/**
 * Service for executing complex queries for {@link Hints} entities in the database.
 * The main input is a {@link HintsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Hints} or a {@link Page} of {@link Hints} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class HintsQueryService extends QueryService<Hints> {

    private final Logger log = LoggerFactory.getLogger(HintsQueryService.class);

    private final HintsRepository hintsRepository;

    public HintsQueryService(HintsRepository hintsRepository) {
        this.hintsRepository = hintsRepository;
    }

    /**
     * Return a {@link List} of {@link Hints} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Hints> findByCriteria(HintsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Hints> specification = createSpecification(criteria);
        return hintsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Hints} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Hints> findByCriteria(HintsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Hints> specification = createSpecification(criteria);
        return hintsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(HintsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Hints> specification = createSpecification(criteria);
        return hintsRepository.count(specification);
    }

    /**
     * Function to convert {@link HintsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Hints> createSpecification(HintsCriteria criteria) {
        Specification<Hints> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Hints_.id));
            }
            if (criteria.getHint() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHint(), Hints_.hint));
            }
            if (criteria.getModifier() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifier(), Hints_.modifier));
            }
            if (criteria.getListingsId() != null) {
                specification = specification.and(buildSpecification(criteria.getListingsId(),
                    root -> root.join(Hints_.listings, JoinType.LEFT).get(Listings_.id)));
            }
        }
        return specification;
    }
}
