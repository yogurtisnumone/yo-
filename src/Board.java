import java.util.HashMap;
import java.util.Map;

public class Board {
    private static final int size = 8;
    private final Map<Position,Minion> board;

    public Board() {
        this.board = new HashMap<>();
    }

    public void placeMinion(Minion minion, Position position) {
        board.put(position, minion);
    }

    public void moveMinion(Minion minion, Position newPosition) {
        if (board.containsValue(minion) && isValidPosition(newPosition)) {
            Position oldPosition = getPosition(minion);
            board.remove(oldPosition);
            board.put(newPosition, minion);
        }
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

    public boolean isValidPosition(Position position) {
        return position.row() >= 0 && position.row() < size &&
                position.col() >= 0 && position.col() < size;
    }

    /*
     public void removeMinion(Minion minion) {
        Position position = getPosition(minion);
        if (position != null) {
           board.remove(position);
        }
    }
    */
}
