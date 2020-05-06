package com.ep.app.repository;

import com.ep.app.domain.Listings;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Listings entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ListingsRepository extends JpaRepository<Listings, Long>, JpaSpecificationExecutor<Listings> {
}
