package src.Minion;

import src.Player.Player;
import src.Position.Position;
import src.Strategy.Strategy;

public class ArcherMinion extends Minion{
    public ArcherMinion(String name, int defense, Strategy strategy, Position position, Player owner) {
        super(name, defense, strategy, position, owner);
    }
}
