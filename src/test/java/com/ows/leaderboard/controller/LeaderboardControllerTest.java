package com.ows.leaderboard.controller;

import static org.junit.jupiter.api.Assertions.*;
import static reactor.core.publisher.Mono.when;

import com.ows.leaderboard.modal.ScoreDetail;
import com.ows.leaderboard.service.LeaderBoardJPAService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

class LeaderboardControllerTest {
    @Mock
    LeaderBoardJPAService leaderboardService;
    @InjectMocks
    LeaderboardController leaderboardController;
    @Mock
    ScoreDetail detail;
    @Test
    void getScore() {
        assertTrue(true);
    }
}