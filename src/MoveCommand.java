public class MoveCommand implements Command {//OK
    Direction direction;
    public MoveCommand(Direction direction) {
        this.direction = direction;
    }
    @Override
    public void execute(GameState gameState, Minion minion) {
        Position currentPosition = GameState.getPosition(minion);
        if (currentPosition == null) return;

        Position newPosition = currentPosition.move(direction);
        if (newPosition == null || !GameState.board.isValidPosition(newPosition)) return;

        GameState.moveMinion(minion, direction);
    }
}
