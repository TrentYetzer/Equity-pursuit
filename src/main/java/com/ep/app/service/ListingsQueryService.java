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

import com.ep.app.domain.Listings;
import com.ep.app.domain.*; // for static metamodels
import com.ep.app.repository.ListingsRepository;
import com.ep.app.service.dto.ListingsCriteria;

/**
 * Service for executing complex queries for {@link Listings} entities in the database.
 * The main input is a {@link ListingsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Listings} or a {@link Page} of {@link Listings} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ListingsQueryService extends QueryService<Listings> {

    private final Logger log = LoggerFactory.getLogger(ListingsQueryService.class);

    private final ListingsRepository listingsRepository;

    public ListingsQueryService(ListingsRepository listingsRepository) {
        this.listingsRepository = listingsRepository;
    }

    /**
     * Return a {@link List} of {@link Listings} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Listings> findByCriteria(ListingsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Listings> specification = createSpecification(criteria);
        return listingsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Listings} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Listings> findByCriteria(ListingsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Listings> specification = createSpecification(criteria);
        return listingsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ListingsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Listings> specification = createSpecification(criteria);
        return listingsRepository.count(specification);
    }

    /**
     * Function to convert {@link ListingsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Listings> createSpecification(ListingsCriteria criteria) {
        Specification<Listings> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Listings_.id));
            }
            if (criteria.getZpid() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getZpid(), Listings_.zpid));
            }
            if (criteria.getStreet() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStreet(), Listings_.street));
            }
            if (criteria.getCity() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCity(), Listings_.city));
            }
            if (criteria.getState() != null) {
                specification = specification.and(buildStringSpecification(criteria.getState(), Listings_.state));
            }
            if (criteria.getZipCode() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getZipCode(), Listings_.zipCode));
            }
            if (criteria.getZestimate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getZestimate(), Listings_.zestimate));
            }
            if (criteria.getAddress() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAddress(), Listings_.address));
            }
            if (criteria.getLongitude() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLongitude(), Listings_.longitude));
            }
            if (criteria.getLatitude() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLatitude(), Listings_.latitude));
            }
        }
        return specification;
    }
}
