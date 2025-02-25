package src.Minion;

import src.Player.Player;
import src.Position.Position;
import src.Strategy.Strategy;

public class KnightMinion extends Minion {
    public KnightMinion(String name, int defense, Strategy strategy, Position position, Player owner) {
        super(name, defense, strategy, position, owner);
    }
}
