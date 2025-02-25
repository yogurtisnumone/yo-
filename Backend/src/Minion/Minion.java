package src.Minion;

import src.GameState.GameState;
import src.Strategy.Strategy;
import src.Player.Player;
import src.Position.Position;
import src.Game.GetConfig;

public abstract class Minion {
    String name;
    private int hp;
    private final int defense;
    private final Strategy strategy;
    private Position position;
    private final Player owner;
    private boolean isDone = false;

    public Minion(String name, int defense, Strategy strategy, Position position, Player owner) {
        this.name = name;
        this.defense = defense;
        this.strategy = strategy;
        this.position = position;
        this.owner = owner;
        this.hp = GetConfig.Hp;
    }
    public void execute(GameState gameState, Minion minion) {
        if (strategy != null) {
            strategy.execute(gameState, minion);
        }
    }

    public void takeDamage(GameState gameState, int damage) {
        hp = Math.max(0, hp - damage);
        System.out.println(name + " took " + damage + " damage. HP: " + hp);

        if (hp == 0) {
            System.out.println(name + " has been eliminated!");
            gameState.removeMinion(this);
            owner.removeMinion(this);
        }
    }

    public boolean isAlive() {
        return hp >= 1;
    }

    public Player getOwner() {
        return owner;
    }

    public int getHp() {
        return hp;
    }

    public void setPosition(Position newPosition) {
        this.position = newPosition;
    }

    public Position getPosition() {
        return position;
    }

    public String getName() {
        return name;
    }

    public int getDefense() { return defense; }

    public void stopExecution() {
        isDone = true;
    }

    public void resetExecution() {
        isDone = false;
    }

    public boolean hasEndedTurn() {
        return isDone;
    }
}
