package src.Strategy;

import src.GameState.GameState;
import src.Minion.Minion;

public interface Command extends Statement {//finish
    void execute(GameState gameState, Minion minion);
}
