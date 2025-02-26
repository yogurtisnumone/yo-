package src.Game;

import src.Board.Board;
import src.GameState.GameState;
import src.Minion.Minion;
import src.Minion.Miniontype;
import src.Player.Player;
import src.Position.Position;
import src.Strategy.Parser;
import src.Strategy.Strategy;

import java.util.*;

public class Turn {
    private final GameState gameState;
    private final List<Player> players;
    private int turnNumber = 1;
    private final int maxTurn;
    private Scanner scanner;
    private final Board board;
    private Map<String, Integer> defenseMap;
    private Map<String, Strategy> strategyMap;
    private Map<String, String> minionNameMap;

    public Turn(GameState gameState, List<Player> players) {
        this.gameState = gameState;
        this.players = players;
        this.maxTurn = GetConfig.maxTurn;
        this.board = gameState.getBoard();
    }

    public void startGame(){
        scanner = new Scanner(System.in);
        List<String> selectedTypes = new ArrayList<>();
        Map<String, Integer> defenseMap = new HashMap<>();
        Map<String, Strategy> strategyMap = new HashMap<>();
        Map<String,String> minionNameMap = new HashMap<>();

        while (true) {
            System.out.println("Choose a minion type to use in this game (enter 'done' to finish):");
            System.out.println("(Available types: tank, archer, mage, knight, shooter)");
            String type = scanner.nextLine().trim().toLowerCase();

            if (type.equals("done")) break;

            if (!List.of("tank", "archer", "mage", "knight", "shooter").contains(type)) {
                System.out.println("Invalid type! Please enter a valid type.");
                continue;
            }

            if (selectedTypes.contains(type)) {
                System.out.println("You already selected this type!");
                continue;
            }

            selectedTypes.add(type);
        }

        for (String type : selectedTypes) {
            System.out.println("Enter minion's name for " + type + ":");
            String minionName = scanner.nextLine().trim().toLowerCase();
            minionNameMap.put(type, minionName);

            System.out.println("Enter defense value for " + type + ":");
            int defenseValue = scanner.nextInt();
            scanner.nextLine();
            defenseMap.put(type, defenseValue);

            System.out.println("Enter strategy for " + type + ":");
            StringBuilder strategyScript = new StringBuilder();
            while (true) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) break;
                strategyScript.append(line).append("\n");
            }
            Strategy parsedStrategy = (Strategy) Parser.parse(strategyScript.toString());
            strategyMap.put(type, parsedStrategy);
        }
        this.defenseMap = defenseMap;
        this.strategyMap = strategyMap;
        this.minionNameMap = minionNameMap;
    }

    public void playTurn() {
        while (turnNumber <= maxTurn && !isGameOver()) {
            System.out.println("\n--- Turn " + turnNumber + " ---");

            for (Player player : players) {
                InterestCalculator.applyInterest(player, turnNumber);
            }

            for (Player player : players) {
                System.out.println(player.getName() + "'s turn:");

                while (true) {
                    System.out.println("Do you want to buy a spawn cell? (buy/done)");
                    String input = scanner.nextLine().trim().toLowerCase();
                    if (input.equals("done")) break;

                    if (input.equals("buy")) {
                        System.out.println("Enter row and col to buy (e.g., 3 2):");
                        int row = scanner.nextInt();
                        int col = scanner.nextInt();
                        scanner.nextLine();
                        Position pos = new Position(row-1, col-1);
                        if (board.buySpawnZone(pos, player)) {
                            System.out.println("Spawn zone bought successfully.");
                            break;
                        } else {
                            System.out.println("Failed to buy spawn zone.");
                        }
                    }
                }

                if (!player.hasPlacedFreeMinion()) {
                    System.out.println("Placed free minion!");
                    System.out.println("Enter minion type:");
                    String type = scanner.nextLine().trim().toLowerCase();

                    if (!defenseMap.containsKey(type) || !strategyMap.containsKey(type)) {
                        System.out.println("ERROR: Minion type " + type + " is not configured.");
                        continue;
                    }
                    System.out.println("Enter row and col to place:");
                    int row = scanner.nextInt();
                    int col = scanner.nextInt();
                    scanner.nextLine();
                    Position pos = new Position(row-1, col-1);

                    int minionDefense = defenseMap.get(type);
                    Strategy minionStrategy = strategyMap.get(type);
                    String minionName = minionNameMap.get(type);

                    Minion minion = Miniontype.createMinion(type, minionName, minionDefense, minionStrategy, pos, player);
                    if (gameState.placeMinion(minion, pos)) {
                        player.addMinion(minion);
                        System.out.println("Minion placed successfully: " + minion.getName());
                    } else {
                        System.out.println("Failed to place minion at " + pos);
                    }
                }

                while (true) {
                    System.out.println("Do you want to buy minions? (buy/done)");
                    String input = scanner.nextLine().trim().toLowerCase();
                    if (input.equals("done")) break;

                    if (input.equals("buy")) {
                        System.out.println("Enter minion type:");
                        String type = scanner.nextLine().trim().toLowerCase();

                        if (!defenseMap.containsKey(type) || !strategyMap.containsKey(type)) {
                            System.out.println("ERROR: Minion type " + type + " is not configured.");
                            continue;
                        }

                        System.out.println("Enter row and col to place:");
                        int row = scanner.nextInt();
                        int col = scanner.nextInt();
                        scanner.nextLine();
                        Position pos = new Position(row-1, col-1);

                        int minionDefense = defenseMap.get(type);
                        Strategy minionStrategy = strategyMap.get(type);
                        String minionName = minionNameMap.get(type);

                        Minion minion = Miniontype.createMinion(type, minionName, minionDefense, minionStrategy, pos, player);

                        if (gameState.placeMinion(minion, pos)) {
                            player.addMinion(minion);
                            System.out.println("Minion placed successfully: " + minion.getName());
                        } else {
                            System.out.println("Failed to place minion at " + pos);
                        }
                        System.out.println("Player " + player.getName() + " now has " + player.getMinions().size() + " minions.");
                    }
                }
                if (player.getMinions().isEmpty()) {
                    declareWinner();
                    return;
                }

                for (Minion minion : player.getMinions()) {
                    if (!minion.isAlive()) continue;
                    if (minion.hasEndedTurn()) {
                        System.out.println(minion.getName() + " skips this turn.");
                        continue;
                    }

                    minion.resetExecution();
                    System.out.printf("%s (HP: %d, Position: %s, Budget: %d) is executing strategy...%n",
                            minion.getName(), minion.getHp(), gameState.getPosition(minion), player.getBudget());

                    minion.execute(gameState, minion);
                    System.out.println(minion.getName() + " finished executing.");
                }
            }

            if (isGameOver()) {
                declareWinner();
                return;
            }

            turnNumber++;
        }
        determineWinnerByStats();
    }

    public boolean isGameOver() {
        boolean defeated = turnNumber > 1 && players.stream().anyMatch(Player::isDefeated);
        boolean spawnLimitReached = players.stream().allMatch(player ->
                player.getSpawnCount() >= player.getMaxSpawn());
        boolean maxTurnReached = turnNumber > maxTurn;

        System.out.println("Checking game over: Defeated=" + defeated + ", SpawnLimitReached=" + spawnLimitReached +
                ", MaxTurnReached=" + maxTurnReached);

        return defeated || spawnLimitReached || maxTurnReached;
    }

    public void declareWinner() {
        Player p1 = players.get(0);
        Player p2 = players.get(1);

        if (p1.isDefeated()) {
            System.out.println(p2.getName() + " Wins! (Opponent has no minions left)");

        } else if (p2.isDefeated()) {
            System.out.println(p1.getName() + " Wins! (Opponent has no minions left)");
        }
    }

    private void determineWinnerByStats() {
        Player p1 = players.get(0);
        Player p2 = players.get(1);

        int p1MinionCount = p1.getMinions().size();
        int p2MinionCount = p2.getMinions().size();

        if (p1MinionCount > p2MinionCount) {
            System.out.println(p1.getName() + " Wins! (More minions remaining)");
            return;
        } else if (p2MinionCount > p1MinionCount) {
            System.out.println(p2.getName() + " Wins! (More minions remaining)");
            return;
        }

        int p1TotalHP = p1.getMinions().stream().mapToInt(Minion::getHp).sum();
        int p2TotalHP = p2.getMinions().stream().mapToInt(Minion::getHp).sum();

        if (p1TotalHP > p2TotalHP) {
            System.out.println(p1.getName() + " Wins! (Higher total HP)");
            return;
        } else if (p2TotalHP > p1TotalHP) {
            System.out.println(p2.getName() + " Wins! (Higher total HP)");
            return;
        }

        if (p1.getBudget() > p2.getBudget()) {
            System.out.println(p1.getName() + " Wins! (More budget remaining)");
        } else if (p2.getBudget() > p1.getBudget()) {
            System.out.println(p2.getName() + " Wins! (More budget remaining)");
        } else {
            System.out.println("App ends in a tie!");
        }
    }
}
