public class NumberExpression implements Expression {//finish
    private final int value;
    public NumberExpression(int value) {
        this.value = value;
    }
    @Override
    public int evaluate(GameState gameState, Minion minion) {
        return value;
    }
}
