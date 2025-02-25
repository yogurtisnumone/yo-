package src.Strategy;

import java.util.Random;

import src.GameState.GameState;
import src.Minion.Minion;

public class RandomExpression implements Expression {//finish
    private static final Random RANDOM = new Random();

    @Override
    public int evaluate(GameState gameState, Minion minion) {
        return RANDOM.nextInt(1000);
    }
    @Override
    public String toString() {
        return "random()";
    }
}
