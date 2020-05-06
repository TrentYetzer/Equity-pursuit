package com.ep.app.repository;

import com.ep.app.domain.Hints;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Hints entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HintsRepository extends JpaRepository<Hints, Long>, JpaSpecificationExecutor<Hints> {
}
