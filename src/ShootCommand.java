public class ShootCommand implements Statement {//OK
    private final Direction direction;
    private final Expression power;

    public ShootCommand(Direction direction, Expression power) {
        this.direction = direction;
        this.power = power;
    }

    @Override
    public void execute(GameState gameState, Minion minion) {
        Position targetPosition = GameState.getPosition(minion).move(direction);
        Minion target = GameState.getMinion(targetPosition);
        if (target != null) {
            target.takeDamage(power.evaluate(gameState, minion));
        }
    }
}
