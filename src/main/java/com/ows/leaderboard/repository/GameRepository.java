package com.ows.leaderboard.repository;

import com.ows.leaderboard.modal.Game;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    List<Game> findByIdLessThan(Long id);
}
