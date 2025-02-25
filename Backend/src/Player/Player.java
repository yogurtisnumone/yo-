package src.Player;

import src.Minion.Minion;
import src.Game.GetConfig;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private final String name;
    private final List<Minion> minions;
    private double budget;
    private final double maxBudget;
    private int spawnCount = 0;
    private final int maxSpawn;
    private boolean hasPlacedFreeMinion = false;
    private boolean isBot;

    public Player(String name) {
        this.name = name;
        this.minions = new ArrayList<>();
        this.budget = GetConfig.Budget;
        this.maxBudget = GetConfig.maxBudget;
        this.maxSpawn = GetConfig.maxSpawn;
    }

    public String getName() {
        return name;
    }

    public int getBudget() {
        return (int) budget;
    }

    public void addBudget(double amount) {
        if((budget+amount) <= maxBudget){
            budget += amount;
        }else if(budget != maxBudget){
            budget = maxBudget;
        }
    }

    public boolean spendBudget(int amount) {
        if (budget >= amount) {
            budget = Math.max(0, budget - amount);
            System.out.println(name + " spent " + amount + ", new budget: " + getBudget());
            return true;
        }
        return false;
    }

    public boolean canSpawnMore() {
        return spawnCount <= maxSpawn;
    }

    public boolean hasPlacedFreeMinion() {
        return hasPlacedFreeMinion;
    }

    public void setPlacedFreeMinion(boolean placedFreeMinion) {
        this.hasPlacedFreeMinion = placedFreeMinion;
    }

    public void addMinion(Minion minion) {
        if (canSpawnMore()) {
            minions.add(minion);
            spawnCount++;
        }
    }

    public void removeMinion(Minion minion) {
        minions.remove(minion);
    }

    public boolean isDefeated() {
        return getMinions().isEmpty() || spawnCount >= maxSpawn;
    }

    public List<Minion> getMinions() {
        return minions;
    }

    public int getSpawnCount() {
        return spawnCount;
    }

    public int getMaxSpawn() {
        return maxSpawn;
    }

    public boolean isBot() {
        return isBot;
    }

    public void setBot(boolean isBot) {
        this.isBot = isBot;
    }
}
