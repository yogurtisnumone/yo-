package src.GameState;

import src.Board.Board;
import src.Player.Player;
import src.Minion.Minion;
import src.Position.Position;
import src.Position.Direction;
import src.Game.GetConfig;

import java.util.Map;

public class DuelGameState implements GameState {
    private final Board board;
    private final Map<String, Player> players;
    private final int spawnCost;

    public DuelGameState(Board board, Map<String, Player> players) {
        this.board = board;
        this.players = players;
        this.spawnCost = GetConfig.spawnCost;
    }

    @Override
    public boolean placeMinion(Minion minion, Position position) {
        Player player = minion.getOwner();

        if (board.getMinionAt(position) != null) {
            System.out.println("Cannot place " + minion.getName() + " at " + position + ". Already occupied!");
            return false;
        }

        if (!board.isSpawnZone(position, player)) {
            System.out.println("Cannot place minion outside spawn zone!");
            return false;
        }

        if (!player.hasPlacedFreeMinion()) {
            player.setPlacedFreeMinion(true);
            board.placeMinion(minion, position);
            System.out.println(player.getName() + " placed a free minion: " + minion.getName());
            return true;
        }

        if (!player.spendBudget(spawnCost)) {
            System.out.println(player.getName() + " cannot afford to place " + minion.getName());
            return false;
        }

        board.placeMinion(minion, position);
        System.out.println(player.getName() + " placed minion: " + minion.getName() + " (Cost: " + spawnCost+ ")");
        return true;

    }

    @Override
    public void removeMinion(Minion minion) {
        board.removeMinion(minion);
    }

    @Override
    public void moveMinion(Minion minion, Direction direction) {
        Position currentPosition = board.getPosition(minion);
        if (currentPosition == null) return;

        Position newPosition = currentPosition.move(direction);
        if (!Position.isValidPosition(newPosition)) return;

        board.moveMinion(minion, newPosition);

    }

    @Override
    public Position getPosition(Minion minion) {
        return board.getPosition(minion);
    }

    @Override
    public Minion getMinion(Position position) {
        return board.getMinionAt(position);
    }

    @Override
    public Board getBoard() {
        return board;
    }

    @Override
    public void addPlayer(Player player) {
        players.put(player.getName(), player);

    }

    @Override
    public Player getPlayer(String playerName) {
        return players.get(playerName);
    }

}
