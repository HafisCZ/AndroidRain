package eu.mar21.rain.core.level.data;

import eu.mar21.rain.core.device.Preferences;

public enum Data {

    // Statistics
    STAT_DMG_TAKEN(null, null, 0),
    STAT_TOTAL_EXP(null, null, 0),
    STAT_SHIELDS_COLLECTED(null, null, 0),
    STAT_ENERGY_COLLECTED(null, null, 0),
    STAT_RANDOM_COLLECTED(null, null, 0),
    STAT_TOTAL_JUMPS(null, null, 0),
    STAT_LONGEST_GAME(null, null, 0),
    STAT_SKILL_ACTIVATIONS(null, null, 0),
    STAT_LIGHTNING_HIT(null, null, 0),

    // Player data
    PLAYER_SCORE(null, null, 0),
    PLAYER_LEVEL(null, null, 1),
    PLAYER_POINTS(null, null, 0),
    PLAYER_HEALTH(null, null, 3),
    PLAYER_SHIELDS(null, null, 0),

    // Shop upgrades
    UPGRADE_RANDOM(null, null, 1),
    UPGRADE_SKILL_SHOCK(null, null, 0),
    UPGRADE_SKILL_XPBOOST(null, null, 0),
    UPGRADE_SKILL_SHIELD(null, null, 0),
    UPGRADE_ENERGY_MULT(null, null, 0),

    // Achievements
    ACH_LEVEL1("Get wet", "Play the game", 0),
    ACH_LEVEL5("Casual", "Reach level 5", 0),
    ACH_LEVEL10("Gamer", "Reach level 10",0),
    ACH_LEVEL25("Expert", "Reach level 25", 0),
    ACH_LEVEL50("Masochist", "Reach level 50", 0),

    ACH_SCORE100K("100K club", "Reach 100 000 score", 0),
    ACH_MAXARMOR("Armorer", "Collect 10 shields", 0),
    ACH_MAXHEALTH("Doctor", "Fully upgrade health", 0),
    ACH_JUMP1K("Bunny", "Jump 1000 times", 0),

    ACH_NODE1K("Node collector", "Collect 1000 nodes", 0),
    ACH_SHIELD1K("Shield collector", "Collect 1000 shields", 0),
    ACH_STAR1K("Star collector", "Collect 1000 stars", 0),
    ACH_DAMAGE1K("Target practise", "Take 1000 damage", 0),

    ACH_LIGHTNINGROD0("Feels good", "Get hit by lightning", 0),
    ACH_LIGHTNINGROD1("Lightning rod", "Get hit by lightning 100 times", 0),

    ACH_EMPTY(null, null, 0);

    // Params
    private int val;

    private final int def;
    private final String label;
    private final String description;

    // Constructor
    Data(String label, String description, int def) {
        this.label = label;
        this.description = description;
        this.def = def;

        this.val = Preferences.get(name(), def);
    }

    // Methods
    public int get() {
        return this.val;
    }

    public String getLabel() {
        return this.label;
    }

    public String getDescription() {
        return this.description;
    }

    public void set(int val) {
        this.val = val;
    }

    public void add(int val) {
        this.val += val;
    }

    public static void reset() {
        for (Data s : values()) {
            s.val = s.def;
        }
    }

    public static void save() {
        for (Data s :  values()) {
            Preferences.set(s.name(), s.val);
        }
    }

}
