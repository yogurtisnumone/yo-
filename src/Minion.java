public abstract class Minion {
    String name;
    private int hp;
    private final int defense;
    private final Strategy strategy;
    private final Position position;
    private final Player owner;

    public Minion(String name, int hp, int defense, Strategy strategy, Position position, Player owner) {
        this.name = name;
        this.hp = hp;
        this.defense = defense;
        this.strategy = strategy;
        this.position = position;
        this.owner = owner;
    }
    public void execute(GameState gameState,Minion minion) {
        if (strategy != null) {
            strategy.execute(gameState, minion);
        }
    }

    public void takeDamage(int damage) {
        int reducedDamage = Math.max(1, damage - defense);
        hp = Math.max(0, hp - reducedDamage);
    }
    /*
    public boolean isAlive() {
        return hp > 0;
    }
    */
    public String getOwner() {
        return owner.getName();
    }

    public int getHp() {
        return hp;
    }
    public Position getPosition() {
        return position;
    }

    public String getName() {
        return name;
    }

}
