package com.ows.leaderboard.repository;


import com.ows.leaderboard.modal.ScoreDetail;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoreDetailRepository extends JpaRepository<ScoreDetail, Long> {
    List<ScoreDetail> findAllByGameId(Long gameId);
    List<ScoreDetail> findAllByPlayerId(Long playerId);
    ScoreDetail findByGameIdAndPlayerId(Long gameId, Long playerId);
}
