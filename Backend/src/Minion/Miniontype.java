package src.Minion;

import src.Player.Player;
import src.Position.Position;
import src.Strategy.Strategy;

public class Miniontype {
    public static Minion createMinion(String type, String name, int defense, Strategy strategy,
                                      Position position, Player player) {
        return switch (type.toLowerCase()) {
            case "tank" -> new TankMinion(name, defense, strategy, position, player);
            case "archer" -> new ArcherMinion(name, defense, strategy, position, player);
            case "mage" -> new MageMinion(name, defense, strategy, position, player);
            case "knight" -> new KnightMinion(name, defense, strategy, position, player);
            case "shooter" -> new ShooterMinion(name, defense, strategy, position, player);
            default -> throw new IllegalArgumentException("Unknown minion type: " + type);
        };
    }
}
