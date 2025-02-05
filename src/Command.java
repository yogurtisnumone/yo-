public interface Command extends Statement{//finish
    void execute(GameState gameState, Minion minion);
}
