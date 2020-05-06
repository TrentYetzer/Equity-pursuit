package com.ep.app.repository;

import com.ep.app.domain.Games;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Games entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GamesRepository extends JpaRepository<Games, Long>, JpaSpecificationExecutor<Games> {

    @Query("select games from Games games where games.user.login = ?#{principal.username}")
    List<Games> findByUserIsCurrentUser();
}
