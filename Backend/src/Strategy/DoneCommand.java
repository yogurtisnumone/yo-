package src.Strategy;

import src.GameState.GameState;
import src.Minion.Minion;

public class DoneCommand implements Command {
    @Override
    public void execute(GameState gameState, Minion minion) {
        System.out.println(minion.getName() + " ends its turn.");
        minion.stopExecution();
    }
}
