package com.ows.leaderboard.service;

import com.ows.leaderboard.exceptions.GameNotFoundException;
import com.ows.leaderboard.exceptions.PlayerNotFoundException;
import com.ows.leaderboard.modal.Game;
import com.ows.leaderboard.modal.Player;
import com.ows.leaderboard.modal.ScoreDetail;
import com.ows.leaderboard.repository.GameRepository;
import com.ows.leaderboard.repository.PlayerRepository;
import com.ows.leaderboard.repository.ScoreDetailRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LeaderBoardJPAService {
    private final GameRepository gameRepository;
    private final ScoreDetailRepository scoreDetailRepository;
    private final PlayerRepository playerRepository;
    private final LeaderboardCacheService leaderboardCacheService;
    public LeaderBoardJPAService(LeaderboardCacheService leaderboardCacheService,GameRepository gameRepository, ScoreDetailRepository scoreDetailRepository, PlayerRepository playerRepository) {
        this.leaderboardCacheService = leaderboardCacheService;
        this.gameRepository = gameRepository;
        this.scoreDetailRepository = scoreDetailRepository;
        this.playerRepository = playerRepository;
    }
    public List<Player> findAllPlayers() {
        return playerRepository.findAll();
    }
    public List<ScoreDetail> findAllScoreDetails() {
        return scoreDetailRepository.findAll();
    }
    public List<Game> findAllGames() {
        return gameRepository.findAll();
    }
    public List<Player> findAllPlayersByGameId(Long gameId) {
        var scores = scoreDetailRepository.findAllByGameId(gameId);
        var list = new ArrayList<Player>();
        for (var score : scores) {
            list.add(score.getPlayer());
        }
        return list;
    }
    public ScoreDetail addScoreDetail(ScoreDetail scoreDetail) {
        return scoreDetailRepository.save(scoreDetail);
    }
    public Player addPlayer(Player player) {
        var savedPlayer = playerRepository.save(player);
        leaderboardCacheService.addPlayer(savedPlayer);
        return  savedPlayer;
    }
    @Cacheable(value = "player", key = "#id")
    public Player getPlayer(Long id) {
        return playerRepository.findById(id)
            .orElseThrow(() -> new PlayerNotFoundException(id));
    }
    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }
    public List<ScoreDetail> getAllScoreDetails() {
        return scoreDetailRepository.findAll();
    }
    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }
    public Game addGame(Game game) {
        var savedGame = gameRepository.save(game);
        leaderboardCacheService.addGame(savedGame);
        return savedGame;
    }
    @Cacheable(value = "game", key = "#id")
    public Game getGame(Long id) {
        return gameRepository.findById(id)
            .orElseThrow(() -> new GameNotFoundException(id));
    }
}
