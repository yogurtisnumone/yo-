public class WhileStatement implements Statement {//OK
    Expression condition;
    BlockStatement body;
    public WhileStatement(Expression condition, BlockStatement body) {
        this.condition = condition;
        this.body = body;
    }

    @Override
    public void execute(GameState gameState, Minion minion) {
        while (condition.evaluate(gameState, minion) > 0) {
            body.execute(gameState, minion);
        }
    }
}
