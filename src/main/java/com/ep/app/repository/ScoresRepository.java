package com.ep.app.repository;

import com.ep.app.domain.Scores;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Scores entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ScoresRepository extends JpaRepository<Scores, Long>, JpaSpecificationExecutor<Scores> {
}
