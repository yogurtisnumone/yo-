import java.util.ArrayList;
import java.util.List;

public class Player {
    private final String name;
    List<Minion> minions;

    public Player(String name) {
        this.name = name;
        this.minions = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addMinion(Minion minion) {
        minions.add(minion);
    }

    public boolean isDefeated() {
        return minions.stream().allMatch(minion -> minion.getHp() <= 0);
    }

    public List<Minion> getMinions() {
        return minions;
    }
}
