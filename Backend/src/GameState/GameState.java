package src.GameState;

import src.Minion.Minion;
import src.Position.Position;
import src.Position.Direction;
import src.Board.Board;
import src.Player.Player;

public interface GameState {

    boolean placeMinion(Minion minion, Position position);
    void removeMinion(Minion minion);
    void moveMinion(Minion minion, Direction direction);
    Position getPosition(Minion minion);
    Minion getMinion(Position position);
    Board getBoard();
    void addPlayer(Player player);
    Player getPlayer(String playerName);
}
