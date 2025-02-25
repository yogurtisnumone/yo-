package src.Strategy;

import src.GameState.GameState;
import src.Minion.Minion;

public class BudgetExpression implements Expression {

    @Override
    public int evaluate(GameState gameState, Minion minion) {
        return minion.getOwner().getBudget();
    }
}