public class Main {
    public static void main(String[] args) {
        GameState gameState = new GameState();

        // Create Players
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");

        // Define strategies
        String strategy = """
    t = t + 1
    m = 0
    while (3 - m > 0) {
        if (budget - 100 > 0) then {} else done
        opponentLoc = opponent
        if (opponentLoc / 10 - 1 > 0) {
            if (opponentLoc % 10 - 5 > 0) then move downleft
            else if (opponentLoc % 10 - 4 > 0) then move down
            else if (opponentLoc % 10 - 3 > 0) then move downright
            else if (opponentLoc % 10 - 2 > 0) then move upleft
            else if (opponentLoc % 10 - 1 > 0) then move upright
            else move up
        } else if (opponentLoc > 0) {
            if (opponentLoc % 10 - 5 > 0) {
                cost = 10 ^ (nearby upleft % 100 + 1)
                if (budget - cost > 0) then shoot upleft else {}
            } else if (opponentLoc % 10 - 4 > 0) {
                cost = 10 ^ (nearby downleft % 100 + 1)
                if (budget - cost > 0) then shoot downleft else {}
            } else {
                cost = 10 ^ (nearby up % 100 + 1)
                if (budget - cost > 0) then shoot up else {}
            }
        } else {
            try = 0
            while (3 - try > 0) {
                success = 1
                dir = random % 6
                if ((dir - 4) * ((nearby upleft % 10) + 1) ^ 2 > 0) then move upleft
                else if ((dir - 3) * ((nearby downleft % 10) + 1) ^ 2 > 0) then move downleft
                else if ((dir - 2) * ((nearby down % 10) + 1) ^ 2 > 0) then move down
                else success = 0
                if (success > 0) then try = 3 else try = try + 1
            }
            m = m + 1
        }
    }
""";

        Minion minion1 = new TankMinion("Warrior1", 100, 20, (Strategy) Parser.parse(strategy), new Position(2, 2), player1);
        Minion minion2 = new TankMinion("Warrior2", 100, 20, (Strategy) Parser.parse(strategy), new Position(5, 5), player2);

        player1.addMinion(minion1);
        player2.addMinion(minion2);

        gameState.placeMinion(minion1, new Position(2, 2));
        gameState.placeMinion(minion2, new Position(5, 5));

        // Simulate game turns
        //while (!player1.isDefeated() && !player2.isDefeated()) {
            System.out.println("--- New Turn ---");
            for (Minion minion : player1.getMinions()) {
                executeMinionTurn(gameState,minion);
            }
            for (Minion minion : player2.getMinions()) {
                executeMinionTurn(gameState,minion);
            }
            gameState.nextTurn();
       // }

        // Declare winner
        if (player1.isDefeated()) {
            System.out.println("Player 2 Wins!");
        } else {
            System.out.println("Player 1 Wins!");
        }

    }

    private static void executeMinionTurn(GameState gameState, Minion minion) {
        System.out.println(minion.getName() + " (HP: " + minion.getHp() +
                ", Position: " + minion.getPosition() + ") is executing strategy...");

        minion.execute(gameState,minion);

        System.out.println(minion.getName() + " now has " + minion.getHp() + " HP, " +
                "new position: " + minion.getPosition());
    }
}
