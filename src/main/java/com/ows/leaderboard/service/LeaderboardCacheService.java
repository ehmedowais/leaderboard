package com.ows.leaderboard.service;

import com.ows.leaderboard.modal.Game;
import com.ows.leaderboard.modal.Player;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

// Reads and writes the same caches used by @Cacheable("player") / @Cacheable("game"),
// so manual writes and annotation-based caching stay in sync. TTL comes from the cacheManager config.
@Service
public class LeaderboardCacheService {
    private final CacheManager cacheManager;
    public LeaderboardCacheService(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }
    public void addPlayer(Player player) {
        cache("player").put(player.getId(), player);
    }
    public Player getPlayer(Long playerId) {
        return cache("player").get(playerId, Player.class);
    }
    public void addGame(Game game) {
        cache("game").put(game.getId(), game);
    }
    public Game getGame(Long gameId) {
        return cache("game").get(gameId, Game.class);
    }
    private Cache cache(String name) {
        return cacheManager.getCache(name);
    }
}
