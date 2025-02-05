import java.util.HashMap;
import java.util.Map;

public class GameState {
    static Board board;
    private int turns;
    int maxTurns = 5;
    private final Map<String, Integer> variables = new HashMap<>();

    public GameState() {
        board = new Board();
        this.turns = 0;
    }

    public void placeMinion(Minion minion,Position position) {
        board.placeMinion(minion,position);
    }
    public static void moveMinion(Minion minion, Direction direction) {
        Position currentPosition = board.getPosition(minion);
        if (currentPosition == null) return;

        Position newPosition = currentPosition.move(direction);
        if (!board.isValidPosition(newPosition)) return;

        board.moveMinion(minion, newPosition);
    }


    public static Position getPosition(Minion minion) {
        return board.getPosition(minion);
    }

    public static Minion getMinion(Position position) {
        return board.getMinionAt(position);
    }

    public int opponentMinion(Minion minion) {
        return entityMinions(minion, false);
    }

    public int allyMinion(Minion minion) {
        return entityMinions(minion, true);
    }

    public int entityMinions(Minion minion, boolean OpponentAlly) {
        Position position = board.getPosition(minion);
        if (position == null) {
            throw new RuntimeException("Error: Minion has no assigned position.");
        }

        int minDistance = Integer.MAX_VALUE;
        int direction = 0;
        for (int i = 0; i < Direction.values().length; i++) {
            Direction directionEnum = Direction.values()[i];
            Position currentPosition = position.move(directionEnum);
            int distance = 1;

            while (true) {
                if (currentPosition == null || !board.isValidPosition(currentPosition)) break;
                Minion foundMinion = board.getMinionAt(currentPosition);

                if (foundMinion != null &&
                        (OpponentAlly && foundMinion.getOwner().equals(minion.getOwner()) ||
                                (!OpponentAlly && !foundMinion.getOwner().equals(minion.getOwner())))) {
                    if (distance < minDistance) {
                        minDistance = distance;
                        direction = i + 1;
                    }
                    break;
                }
                currentPosition = currentPosition.move(directionEnum);
                distance++;
            }
        }
        return (minDistance == Integer.MAX_VALUE) ? 0 : (minDistance * 10 + direction);
    }

    public int nearbyMinions(Minion minion,Direction direction) {
        Position position = getPosition(minion).move(direction);
        return (board.getMinionAt(position) != null) ? 1 : 0;
    }

    public void nextTurn() {
        turns++;
        if (turns >= maxTurns) {
            endGame();
        }
    }

    private void endGame() {
        System.out.println("Game Over!");
    }

    public void setVariable(String varName, int value) {
        variables.put(varName, value);
    }

    public int getVariable(String varName) {
        return variables.getOrDefault(varName, 0);
    }
}
