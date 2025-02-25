package src.Strategy;

import src.GameState.GameState;
import src.Minion.Minion;

public class BinaryExpression implements Expression {//finish
    final Expression left, right;
    final String operator;

    public BinaryExpression(Expression left, String operator, Expression right ) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    @Override
    public int evaluate(GameState gameState, Minion minion) {
        int leftVal = left.evaluate(gameState, minion);
        int rightVal = right.evaluate(gameState, minion);

        return switch (operator) {
            case "+" -> leftVal + rightVal;
            case "-" -> leftVal - rightVal;
            case "*" -> leftVal * rightVal;
            case "/" -> rightVal != 0 ? leftVal / rightVal : 0;
            case "%" -> rightVal != 0 ? leftVal % rightVal : 0;
            case "^" -> (int) Math.pow(leftVal, rightVal);
            default -> throw new IllegalArgumentException("Unknown " + operator);
        };
    }
}
