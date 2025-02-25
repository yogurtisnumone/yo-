package src.Strategy;

import src.GameState.GameState;
import src.Minion.Minion;

public interface Strategy {//finish
    void execute(GameState gameState, Minion minion);
    static Strategy fromStatement(Statement statement) {
        return statement::execute;
    }
}
