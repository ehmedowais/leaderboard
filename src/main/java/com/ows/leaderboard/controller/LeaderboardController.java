package com.ows.leaderboard.controller;



import com.ows.leaderboard.modal.Game;
import com.ows.leaderboard.modal.Player;
import com.ows.leaderboard.modal.ScoreDetail;
import com.ows.leaderboard.service.LeaderBoardJPAService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LeaderboardController {
    private final LeaderBoardJPAService leaderboardService;
    public LeaderboardController(LeaderBoardJPAService leaderboardService) {
        super();
        this.leaderboardService = leaderboardService;
    }
    @GetMapping("/greeting")
    public String greeting() {

        return "Hello World";
    }

    @GetMapping("/scores")
    public ResponseEntity<List<ScoreDetail>> getScore() {
        var score = leaderboardService.findAllScoreDetails();
        return new ResponseEntity<>(score, HttpStatus.OK);

    }

    @PostMapping("/scores")
    public ResponseEntity<ScoreDetail> saveScore(@RequestBody ScoreDetail scoreDetail) {
        leaderboardService.addScoreDetail(scoreDetail);
        return new ResponseEntity<>(scoreDetail, HttpStatus.OK);
    }
    @GetMapping("/games")
    public ResponseEntity<List<Game>> getGames() {
        var games =leaderboardService.findAllGames();
        return new ResponseEntity<>(games, HttpStatus.OK);
    }
    @GetMapping("/games/{id}")
    public ResponseEntity<Game> getGame(@PathVariable Long id) {
        var game = leaderboardService.getGame(id);
        return new ResponseEntity<>(game, HttpStatus.OK);
    }
    @PostMapping("/games")
    public ResponseEntity<Game> saveGame(@RequestBody Game game) {
        var g = leaderboardService.addGame(game);
        return new ResponseEntity<>(g, HttpStatus.OK);
    }
    @GetMapping("/players")
    public ResponseEntity<List<Player>> getPlayers() {
        var players =leaderboardService.findAllPlayers();
        return new ResponseEntity<>(players, HttpStatus.OK);
    }
    @GetMapping("/players/{id}")
    public ResponseEntity<Player> getPlayers(@PathVariable Long id) {
        var players =leaderboardService.getPlayer(id);
        return new ResponseEntity<>(players, HttpStatus.OK);
    }
    @PostMapping("/players")
    public ResponseEntity<Player> savePlayer(@RequestBody Player player) {
        var p = leaderboardService.addPlayer(player);
        return new ResponseEntity<>(p, HttpStatus.OK);
    }
}
