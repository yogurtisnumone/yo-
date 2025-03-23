package com.kombat.service;

import com.kombat.Backend.Board.Board;
import com.kombat.Backend.GameState.DuelGameState;
import com.kombat.Backend.Game.Turn;
import com.kombat.Backend.Minion.Minion;
import com.kombat.Backend.Minion.Miniontype;
import com.kombat.Backend.Player.Player;
import com.kombat.Backend.Position.Position;
import com.kombat.Backend.Strategy.Strategy;
import com.kombat.dto.GameStartRequest;
import com.kombat.dto.MinionPlacementRequest;
import com.kombat.model.GameMode;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GameService {

    private DuelGameState gameState;
    private Turn turn;

    public void startNewGame(GameStartRequest request) {
        GameMode mode = request.getMode();
        Board board = new Board();
        Map<String, Player> players = new HashMap<>();

        if (mode == GameMode.DUEL) {
            Player p1 = new Player(request.getPlayer1());
            Player p2 = new Player(request.getPlayer2());
            players.put(p1.getName(), p1);
            players.put(p2.getName(), p2);
        } else if (mode == GameMode.SOLO) {
            Player p1 = new Player(request.getPlayer1());
            Player bot = new Player("AI");
            players.put(p1.getName(), p1);
            players.put(bot.getName(), bot);
        } else if (mode == GameMode.AUTO) {
            Player auto1 = new Player("Bot A");
            Player auto2 = new Player("Bot B");
            players.put(auto1.getName(), auto1);
            players.put(auto2.getName(), auto2);
        }

        gameState = new DuelGameState(board, players);
        for (Player p : players.values()) {
            gameState.addPlayer(p);
        }

        turn = new Turn(gameState, new ArrayList<>(players.values()));
        turn.startGame();
    }

    public void playTurn() {
        if (turn != null) {
            turn.playTurn();
        }
    }

    public Map<String, Object> getSelectedMinions() {
        return (turn != null) ? turn.getSelectedMinionDetails() : Collections.emptyMap();
    }

    public Map<String, Object> getSelectedMinionDetails() {
        return (turn != null) ? turn.getSelectedMinionDetails() : Collections.emptyMap();
    }

    public Map<String, Object> getCommonGameState() {
        Map<String, Object> map = new HashMap<>();
        if (turn != null) {
            map.put("currentTurn", turn.getTurnNumber());
            map.put("maxTurn", turn.getMaxTurn());
        }
        return map;
    }

    public Map<String, Object> getPlayerGameState(String name) {
        Map<String, Object> map = new HashMap<>();
        if (gameState != null) {
            Player player = gameState.getPlayer(name);
            if (player != null) {
                map.put("budget", player.getBudget());
                map.put("minions", player.getMinions());
            }
        }
        return map;
    }

    public String placeMinion(MinionPlacementRequest request) {
        if (turn == null || gameState == null) {
            return "Game has not started.";
        }

        Player player = gameState.getPlayer(request.getPlayerName());
        if (player == null) {
            return "Player not found.";
        }

        Map<String, Object> selected = turn.getSelectedMinionDetails();
        Map<String, Integer> defenses = (Map<String, Integer>) selected.get("defenses");
        Map<String, String> names = (Map<String, String>) selected.get("names");
        Map<String, Strategy> strategies = turn.getStrategyMap();

        String type = request.getMinionType();
        if (!defenses.containsKey(type) || !strategies.containsKey(type)) {
            return "Invalid minion type.";
        }

        Position pos = new Position(request.getRow(), request.getCol());
        int defense = defenses.get(type);
        String name = names.get(type);
        Strategy strategy = strategies.get(type);

        Minion minion = Miniontype.createMinion(type, name, defense, strategy, pos, player);
        if (gameState.placeMinion(minion, pos)) {
            player.addMinion(minion);
            return "Minion placed successfully!";
        } else {
            return "Failed to place minion.";
        }
    }
}
