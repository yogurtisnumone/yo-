import java.util.List;

class BlockStatement implements Strategy, Statement {//OK
    private final List<Statement> statements;

    public BlockStatement(List<Statement> statements) {
        this.statements = statements;
    }
    @Override
    public void execute(GameState gameState, Minion minion) {
        for (Statement stmt : statements) {
            stmt.execute(gameState, minion);
        }
    }
}

