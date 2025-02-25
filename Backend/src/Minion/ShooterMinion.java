package src.Minion;

import src.Player.Player;
import src.Position.Position;
import src.Strategy.Strategy;

public class ShooterMinion extends Minion {
    public ShooterMinion(String name, int defense, Strategy strategy, Position position, Player owner) {
        super(name, defense, strategy, position, owner);
    }
}
