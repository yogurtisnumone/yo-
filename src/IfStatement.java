public class IfStatement implements Statement{//OK
    Expression condition;
    Statement thenBody, elseBody;
    public IfStatement(Expression condition, Statement thenBody, Statement elseBody) {
        this.condition = condition;
        this.thenBody = thenBody;
        this.elseBody = elseBody;
    }
    @Override
    public void execute(GameState gameState, Minion minion) {
        if(condition.evaluate(gameState,minion) > 0){
            thenBody.execute(gameState,minion);
        }else{
            elseBody.execute(gameState,minion);
        }
    }
}
