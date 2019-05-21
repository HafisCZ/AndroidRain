package eu.mar21.rain.core.level.data;

import eu.mar21.rain.core.device.Preferences;

public enum Statistics {

    STAT_DMG_TAKEN,
    STAT_TOTAL_EXP,
    STAT_SHIELDS_COLLECTED,
    STAT_ENERGY_COLLECTED,
    STAT_RANDOM_COLLECTED,
    STAT_TOTAL_JUMPS,
    STAT_LONGEST_GAME,
    STAT_SKILL_ACTIVATIONS,

    PLAYER_SCORE,
    PLAYER_LEVEL(1),
    PLAYER_POINTS,
    PLAYER_HEALTH(3),
    PLAYER_SHIELDS,

    UPGRADE_RANDOM(1),
    UPGRADE_SKILL_SHOCK,
    UPGRADE_SKILL_XPBOOST,
    UPGRADE_SKILL_SHIELD,
    UPGRADE_ENERGY_MULT;

    // Params
    private int val;
    private int def;

    // Constructor
    Statistics() {
        this(0);
    }

    Statistics(int def) {
        this.def = def;
        this.val = Preferences.get(name(), def);
    }

    public int get() {
        return this.val;
    }

    public void set(int val) {
        this.val = val;
    }

    public void add(int val) {
        this.val += val;
    }

    public static void reset() {
        for (Statistics s : values()) {
            s.val = s.def;
        }
    }

    public static void save() {
        for (Statistics s :  values()) {
            Preferences.set(s.name(), s.val);
        }
    }

}
