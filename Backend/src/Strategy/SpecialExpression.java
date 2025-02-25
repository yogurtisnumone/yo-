package src.Strategy;

import src.Game.GetConfig;
import src.GameState.GameState;
import src.Minion.Minion;
import src.Position.Position;

import java.util.Random;

public class SpecialExpression implements Expression {
    final String specialExpression;
    private static final Random random = new Random();
    public SpecialExpression(String specialExpression) {
        this.specialExpression = specialExpression;
    }
    @Override
    public int evaluate(GameState gameState, Minion minion) {
        return switch(specialExpression){
            case "budget" -> minion.getOwner().getBudget();
            case "random" -> random.nextInt(1000);
            case "row" -> {
                Position pos = gameState.getPosition(minion);
                if (pos == null) {
                    throw new RuntimeException("Minion ไม่มีตำแหน่ง (row) ไม่สามารถประเมินได้");
                }
                yield pos.row();
            }
            case "col" -> {
                Position pos = gameState.getPosition(minion);
                if (pos == null) {
                    throw new RuntimeException("Minion ไม่มีตำแหน่ง (col) ไม่สามารถประเมินได้");
                }
                yield pos.col();
            }
            case "int" -> (int) Math.round(GetConfig.interestPct);
            case "maxbudget" -> (int) GetConfig.maxBudget;
            case "spawnsleft" -> minion.getOwner().getMaxSpawn() - minion.getOwner().getSpawnCount();
            default -> throw new RuntimeException("Unknown special variable : " + specialExpression);
        };
    }
}
