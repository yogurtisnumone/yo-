package src.Board;

import src.Minion.Minion;
import src.Position.Position;
import src.Player.Player;
import src.Game.GetConfig;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;



public class Board {
    private Map<Position,Minion> board;
    private final Set<Position> SPAWN_ZONE_PLAYER1;
    private final Set<Position> SPAWN_ZONE_PLAYER2;
    private final int hexPurchaseCost;

    public Board() {
        this.board = new HashMap<>();
        this.hexPurchaseCost = GetConfig.hexPurchaseCost;

        SPAWN_ZONE_PLAYER1 = new HashSet<>();
        SPAWN_ZONE_PLAYER1.add(new Position(0, 0));
        SPAWN_ZONE_PLAYER1.add(new Position(0, 1));
        SPAWN_ZONE_PLAYER1.add(new Position(1, 0));
        SPAWN_ZONE_PLAYER1.add(new Position(1, 1));
        SPAWN_ZONE_PLAYER1.add(new Position(0, 2));

        SPAWN_ZONE_PLAYER2 = new HashSet<>();
        SPAWN_ZONE_PLAYER2.add(new Position(7, 5));
        SPAWN_ZONE_PLAYER2.add(new Position(7, 6));
        SPAWN_ZONE_PLAYER2.add(new Position(7, 7));
        SPAWN_ZONE_PLAYER2.add(new Position(6, 6));
        SPAWN_ZONE_PLAYER2.add(new Position(6, 7));
    }

    public void placeMinion(Minion minion, Position position) {
        if (!board.containsKey(position)) {
            board.put(position, minion);
        }
    }

    public void moveMinion(Minion minion, Position newPosition) {
        if (!board.containsValue(minion)) {
            System.out.println("Not found minion on board");
            return;
        }

        Position oldPosition = getPosition(minion);

        if (oldPosition == null) {
            System.out.println("Minion has no position");
            return;
        }

        if (board.containsKey(newPosition)) {
            System.out.println("This position has already minion on the board");
            return;
        }

        board.remove(oldPosition);
        board.put(newPosition, minion);
        minion.setPosition(newPosition);
    }

    public Position getPosition(Minion minion) {
        for (Map.Entry<Position, Minion> entry : board.entrySet()) {
            if (entry.getValue().equals(minion)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public Minion getMinionAt(Position position) {
        return board.get(position);
    }

    public void removeMinion(Minion minion) {
        Position position = getPosition(minion);
        if (position != null) {
            board.remove(position);
        }
    }

    public boolean isSpawnZone(Position position, Player player) {
        if (player.getName().equals("Player1")) {
            return SPAWN_ZONE_PLAYER1.contains(position);
        } else if (player.getName().equals("Player2")) {
            return SPAWN_ZONE_PLAYER2.contains(position);
        }
        return false;
    }

    public boolean buySpawnZone(Position newPosition, Player player) {
        Set<Position> playerSpawnZone = player.getName().equals("Player1") ? SPAWN_ZONE_PLAYER1 : SPAWN_ZONE_PLAYER2;

        if (isAdjacentToSpawnZone(newPosition, playerSpawnZone)) {
            if (player.spendBudget(hexPurchaseCost)) {
                playerSpawnZone.add(newPosition);
                System.out.println(player.getName() + " buy Spawn Zone at " + newPosition);
                return true;
            } else {
                System.out.println(player.getName() + " There is not enough budget to buy a spawn zone!");
            }
        } else {
            System.out.println("Position " + newPosition + " Not stuck in spawn zone!");
        }
        return false;
    }

    private boolean isAdjacentToSpawnZone(Position position, Set<Position> spawnZone) {
        for (Position p : spawnZone) {
            if (p.isAdjacent(position)) {
                return true;
            }
        }
        return false;
    }
}
