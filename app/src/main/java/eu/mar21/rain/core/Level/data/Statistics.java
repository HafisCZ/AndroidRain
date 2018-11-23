package eu.mar21.rain.core.level.data;

import eu.mar21.rain.core.utils.DataStorage;

public enum Statistics {

    STAT_COUNT_DAMAGE(0),
    STAT_COUNT_EXP(0),
    STAT_COUNT_SHIELD(0),
    STAT_COUNT_NODES(0),
    STAT_COUNT_STARS(0),
    STAT_COUNT_JUMP(0),
    STAT_COUNT_ACTIVATE(0),

    PLAYER_SCORE(0),
    PLAYER_LEVEL(1),
    PLAYER_SPENDABLE_POINTS(0),
    PLAYER_MAX_HEALTH(3),
    PLAYER_DEF_SHIELD(0),
    PLAYER_UPGRADE_DMG_SHOCKWAVE(0),
    PLAYER_UPGRADE_BETTER_NODES(0),
    PLAYER_SKILL_SHOCKWAVE(0),
    PLAYER_SKILL_DOUBLE_EXP(0),
    PLAYER_SKILL_SHIELD(0);

    private int value;
    private int def;

    Statistics(int def) {
        this.def = def;
        this.value = DataStorage.INSTANCE.get(name(), def);
    }

    public int get() {
        return this.value;
    }

    public void set(int value) {
        this.value = value;
    }

    public void add() {
        add(1);
    }

    public void add(int amount) {
        this.value += amount;
    }

    public static void clear() {
        for (Statistics s : values()) {
            s.set(s.def);
        }
    }

    public static void save() {
        for (Statistics s : values()) {
            DataStorage.INSTANCE.set(s.name(), s.value);
        }
    }

}
