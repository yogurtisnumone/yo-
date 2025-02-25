package src.Strategy;

import src.GameState.GameState;
import src.Minion.Minion;
import src.Position.Direction;
import src.Position.Position;
import src.Player.Player;

public class MoveCommand implements Command {//finished
    Direction direction;
    public MoveCommand(Direction direction) {
        this.direction = direction;
    }
    @Override
    public void execute(GameState gameState, Minion minion) {
        Position currentPosition = gameState.getPosition(minion);
        if (currentPosition == null) {
            System.out.println("MoveCommand failed: Minion has no position.");
            return;
        }

        Position newPosition = currentPosition.move(direction);
        if (newPosition == null || !Position.isValidPosition(newPosition)) {
            System.out.println("MoveCommand failed: Invalid move to "+ newPosition);
            return;
        }

        Minion occupiedMinion = gameState.getMinion(newPosition);
        if (occupiedMinion != null) {
            System.out.println("MoveCommand failed: Position " + newPosition + " is already occupied by " +
                    occupiedMinion.getName());
            return;
        }

        Player owner = minion.getOwner();
        if (!owner.spendBudget(1)) {
            System.out.println(minion.getName() + " tried to move but has insufficient budget.");
            return;
        }

        System.out.println(minion.getName() + " moves " + direction + " to " + newPosition);
        gameState.moveMinion(minion, direction);
    }
}
