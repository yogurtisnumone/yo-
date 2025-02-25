package src.Strategy;

import src.GameState.GameState;
import src.Minion.Minion;
import src.Position.Position;
import src.Position.Direction;
import src.Player.Player;

public class ShootCommand implements Command {//finished
    private final Direction direction;
    private final Expression power;

    public ShootCommand(Direction direction, Expression power) {
        this.direction = direction;
        this.power = power;
    }

    @Override
    public void execute(GameState gameState, Minion minion) {
        Player player = minion.getOwner();
        Position minionPosition = gameState.getPosition(minion);
        if (minionPosition == null) {
            System.out.println("ShootCommand failed: Minion has no position.");
            return;
        }

        Position targetPosition = minionPosition.move(direction);
        Minion target = (targetPosition != null && Position.isValidPosition(targetPosition))
                ? gameState.getMinion(targetPosition)
                : null;

        int damage = power.evaluate(gameState, minion);
        int totalCost = damage + 1;

        if (target != null) {
            if (!player.spendBudget(totalCost)) {
                System.out.println(minion.getName() + " tried to shoot but has insufficient budget.");
                return;
            }
            int reducedDamage = Math.max(1, damage - target.getDefense());
            target.takeDamage(gameState, reducedDamage);

            System.out.println(minion.getName() + " shoots " + direction + " at " +
                    target.getName() + " dealing " + reducedDamage + " damage!");
            if (!target.isAlive()) {
                System.out.println(target.getName() + " has been eliminated!");
                gameState.removeMinion(target);
            }

            if (target.getOwner().equals(player)) {
                System.out.println("WARNING: " + minion.getName() + " attacked an ally: " + target.getName());
            }

        } else {
            System.out.println(minion.getName() + " shoots " + direction + " but hits nothing.");
        }
    }
}
