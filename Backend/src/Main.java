package src;

import src.Board.Board;
import src.GameState.DuelGameState;
import src.Player.Player;
import src.Game.Turn;
import src.Game.GetConfig;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Main {
    public static void main(String[] args) {
        GetConfig config = new GetConfig();

        Board board = new Board();
        Map<String, Player> playersMap = new HashMap<>();
        DuelGameState gameState = new DuelGameState(board, playersMap);

        // Create Players
        Player player1 = new Player("Player1");
        Player player2 = new Player("Player2");

        gameState.addPlayer(player1);
        gameState.addPlayer(player2);

        Turn turn = new Turn(gameState, List.of(player1, player2));
        turn.startGame();
        System.out.println("=== Battle Start! ===");
        turn.playTurn();
    }
}
