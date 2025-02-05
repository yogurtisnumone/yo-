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
            case "ally" -> gameState.allyMinion(minion);
            case "opponent" -> gameState.opponentMinion(minion);
            case "nearby" -> {
                if (direction == null) throw new RuntimeException("Nearby needs a direction");
                yield gameState.nearbyMinions(minion, direction);
            }
            default -> throw new RuntimeException("Unknown info type: " + info);
        };
    }
}
