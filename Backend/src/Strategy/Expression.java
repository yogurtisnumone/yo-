package src.Strategy;

import src.GameState.GameState;
import src.Minion.Minion;

public interface Expression {//finish
    int evaluate(GameState gameState, Minion minion);
}
