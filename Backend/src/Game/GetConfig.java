package src.Game;

public class GetConfig {
    public static int spawnCost;
    public static int hexPurchaseCost;
    public static double Budget;
    public static int Hp;
    public static int turnBudget;
    public static double maxBudget;
    public static double interestPct;
    public static int maxTurn;
    public static int maxSpawn;

    public GetConfig() {
        ConfigReader config = ConfigReader.getInstance();
        spawnCost = config.getIntProperty("spawn_cost");
        hexPurchaseCost = config.getIntProperty("hex_purchase_cost");
        Budget = config.getDoubleProperty("init_budget");
        Hp = config.getIntProperty("init_hp");
        turnBudget = config.getIntProperty("turn_budget");
        maxBudget = config.getIntProperty("max_budget");
        interestPct = config.getDoubleProperty("interest_pct");
        maxTurn = config.getIntProperty("max_turns");
        maxSpawn = config.getIntProperty("max_spawns");
    }
}
