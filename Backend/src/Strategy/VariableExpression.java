package src.Strategy;

import src.GameState.GameState;
import src.Minion.Minion;

public class VariableExpression implements Expression {//finish
    private final String varName;
    public VariableExpression(String varName) {
        this.varName = varName;
    }
    @Override
    public int evaluate(GameState gameState, Minion minion) {
        return GameStateParser.getInstance().getVariable(varName); // Fetch from GameState.GameState
    }
}
