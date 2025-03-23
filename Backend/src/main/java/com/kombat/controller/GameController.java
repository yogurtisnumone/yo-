package com.kombat.controller;

import com.kombat.dto.GameStartRequest;
import com.kombat.dto.MinionPlacementRequest;
import com.kombat.service.GameService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/game")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/start")
    public ResponseEntity<String> startGame(@RequestBody GameStartRequest request) {
        gameService.startNewGame(request);
        return ResponseEntity.ok("Game started successfully!");
    }

    @PostMapping("/turn")
    public ResponseEntity<String> playTurn() {
        gameService.playTurn();
        return ResponseEntity.ok("Turn played.");
    }

    @GetMapping("/minions/selected")
    public ResponseEntity<Map<String, Object>> getSelectedMinions() {
        return ResponseEntity.ok(gameService.getSelectedMinions());
    }

    @GetMapping("/state/common")
    public ResponseEntity<Map<String, Object>> getCommonGameState() {
        return ResponseEntity.ok(gameService.getCommonGameState());
    }

    @GetMapping("/state/player/{name}")
    public ResponseEntity<Map<String, Object>> getPlayerGameState(@PathVariable String name) {
        return ResponseEntity.ok(gameService.getPlayerGameState(name));
    }

    @PostMapping("/minions/place")
    public ResponseEntity<String> placeMinion(@RequestBody MinionPlacementRequest request) {
        String result = gameService.placeMinion(request);
        return ResponseEntity.ok(result);
    }
}
