package src.Strategy;

import src.GameState.GameState;
import src.Minion.Minion;

public class WhileStatement implements Statement {//OK
    private static final int maxLoop = 10_000;
    Expression condition;
    BlockStatement body;
    public WhileStatement(Expression condition, BlockStatement body) {
        this.condition = condition;
        this.body = body;
    }

    @Override
    public void execute(GameState gameState, Minion minion) {
        int iteration = 0;

        while (condition.evaluate(gameState, minion) > 0) {
            if (iteration >= maxLoop) {
                break;
            }
            body.execute(gameState, minion);
            iteration++;
        }
    }
}
