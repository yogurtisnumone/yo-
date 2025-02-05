public class VariableExpression implements Expression {//finish
    private final String varName;
    public VariableExpression(String varName) {
        this.varName = varName;
    }
    @Override
    public int evaluate(GameState gameState, Minion minion) {
        return gameState.getVariable(varName); // Fetch from GameState
    }
}
