package com.ows.leaderboard.config;

import com.ows.leaderboard.repository.GameRepository;
import com.ows.leaderboard.repository.PlayerRepository;
import com.ows.leaderboard.service.LeaderboardCacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CacheLoader {
    private final PlayerRepository playerRepository;
    private final GameRepository gameRepository;
    private final LeaderboardCacheService leaderboardCacheService;
    public CacheLoader(PlayerRepository playerRepository, GameRepository gameRepository, LeaderboardCacheService leaderboardCacheService) {
        this.playerRepository = playerRepository;
        this.gameRepository = gameRepository;
        this.leaderboardCacheService = leaderboardCacheService;
    }
    @EventListener(ApplicationReadyEvent.class)
    public void loadCache() {
        log.info("Prewarming players cache ...");
        playerRepository.findByIdLessThan(10L).forEach(leaderboardCacheService::addPlayer);
        log.info("Prewarming games cache ...");
        gameRepository.findByIdLessThan(10L).forEach(leaderboardCacheService::addGame);
    }

}
