public class CompareExpression implements Expression {//finish
    private final Expression left, right;
    private final String operator;

    public CompareExpression(Expression left, Expression right, String operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }


    @Override
    public int evaluate(GameState gameState, Minion minion) {
        int leftVal = left.evaluate(gameState, minion);
        int rightVal = right.evaluate(gameState, minion);

        return switch (operator) {
            case ">" -> (leftVal > rightVal) ? 1 : 0;
            case ">=" -> (leftVal >= rightVal) ? 1 : 0;
            case "<" -> (leftVal < rightVal) ? 1 : 0;
            case "<=" -> (leftVal <= rightVal) ? 1 : 0;
            case "==" -> (leftVal == rightVal) ? 1 : 0;
            case "!=" -> (leftVal != rightVal) ? 1 : 0;
            default -> throw new IllegalArgumentException("Unknown comparison operator: " + operator);
        };
    }
}
