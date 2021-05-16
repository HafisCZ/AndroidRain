package eu.mar21.rain.core.level.data;

import eu.mar21.rain.core.device.Preferences;

public enum Data {

    DEBUG(0),

    // Statistics
    STAT_DMG_TAKEN(0),
    STAT_TOTAL_EXP(0),
    STAT_SHIELDS_COLLECTED(0),
    STAT_ENERGY_COLLECTED(0),
    STAT_RANDOM_COLLECTED(0),
    STAT_TOTAL_JUMPS(0),
    STAT_LONGEST_GAME(0),
    STAT_SKILL_ACTIVATIONS(0),
    STAT_LIGHTNING_HIT(0),
    STAT_SHIELDS_MAX(0),
    STAT_EVENTS(0),
    STAT_TIME_PLAYED(0),
    STAT_GAMES_PLAYED(0),

    // Player data
    PLAYER_SCORE(0),
    PLAYER_LEVEL(1),
    PLAYER_POINTS(0);

    // Params
    private int val;
    private final int def;

    // Constructor
    Data(int def) {
        this.def = def;

        this.val = Preferences.get(name(), def);
    }

    // Methods
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
