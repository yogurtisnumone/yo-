package src.Game;

import src.Player.Player;

public class InterestCalculator {
    private static final double baseRate = GetConfig.interestPct;

    public static double calculateInterest(Player player, int turn) {
        double budget = player.getBudget();

        if (budget > 0 && turn > 1) {
            double interestRate = baseRate * Math.log10(budget) * Math.log(turn);
            return (budget * interestRate) / 100;
        }
        return 0;

    }

    public static void applyInterest(Player player, int turn) {
        double interest = calculateInterest(player, turn);
        player.addBudget(interest);

        System.out.printf("%s Earn interest %d (Rate: %.2f%%), new budget: %d%n",
                player.getName(), Math.round(interest), baseRate, Math.round(player.getBudget()));
    }
}
