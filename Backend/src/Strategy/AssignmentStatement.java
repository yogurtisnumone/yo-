package src.Strategy;

import src.GameState.GameState;
import src.Minion.Minion;

public class AssignmentStatement implements Statement {//OK
    String varName;
    Expression value;
    public AssignmentStatement(String varName, Expression value) {
        this.varName = varName;
        this.value = value;
    }

    @Override
    public void execute(GameState gameState, Minion minion) {
        if (varName == null || varName.isEmpty()) {
            throw new RuntimeException("Assignment error: Variable name is null or empty.");
        }

        int evaluatedValue;
        try {
            evaluatedValue = value.evaluate(gameState, minion);
        } catch (Exception e) {
            throw new RuntimeException("Error evaluating expression for " + varName + ": " + e.getMessage());
        }

        GameStateParser.getInstance().setVariable(varName, evaluatedValue);
        System.out.println("Assigned " + varName + " = " + evaluatedValue);
    }

}
