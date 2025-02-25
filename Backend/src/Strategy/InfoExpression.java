package src.Strategy;

import src.GameState.GameState;
import src.Minion.Minion;
import src.Position.Direction;

public class InfoExpression implements Expression {//OK
    private final String info;
    private final Direction direction;
    public InfoExpression(String info) {
        this.info = info;
        this.direction = null;
    }
    public InfoExpression(String info, Direction direction) {
        this.info = info;
        this.direction = direction;
    }

    @Override
    public int evaluate(GameState gameState, Minion minion) {
        return switch (info) {
            case "ally" -> GameStateParser.getInstance().allyMinion(gameState,minion);
            case "opponent" -> GameStateParser.getInstance().opponentMinion(gameState, minion);
            case "nearby" -> {
                if (direction == null) throw new RuntimeException("Nearby needs a direction");
                yield GameStateParser.getInstance().nearbyMinions(gameState, minion, direction);
            }
            default -> throw new RuntimeException("Unknown info type: " + info);
        };
    }
}
