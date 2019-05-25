package eu.mar21.rain.core.level.data;

import eu.mar21.rain.core.device.Preferences;

public enum Award {

    LEVEL_50("Masochist", "Reach level 50", Data.PLAYER_LEVEL, 50, null),
    LEVEL_25("Expert", "Reach level 25", Data.PLAYER_LEVEL, 25, LEVEL_50),
    LEVEL_10("Player", "Reach level 10", Data.PLAYER_LEVEL, 10, LEVEL_25),
    LEVEL_5("Casual", "Reach level 5", Data.PLAYER_LEVEL, 5, LEVEL_10),
    LEVEL_1("Get wet", "Play the game", Data.PLAYER_LEVEL, 1, LEVEL_5),

    SCORE_1M("A million", "Reach 1M score", Data.PLAYER_SCORE, 1000000, null),
    SCORE_500K("Halfway there", "Reach 500K score", Data.PLAYER_SCORE, 500000, SCORE_1M),
    SCORE_100K("100K club", "Reach 100K score", Data.PLAYER_SCORE, 100000, SCORE_500K),

    JUMP_7777("Lucky paw", "Jump 7777 times", Data.STAT_TOTAL_JUMPS, 7777, null),
    JUMP_666("Rabbit", "Jump 666 times", Data.STAT_TOTAL_JUMPS, 666, JUMP_7777),

    NODE_10K("Powerhouse III", "Collect 10 000 nodes", Data.STAT_ENERGY_COLLECTED, 10000, null),
    NODE_5K("Powerhouse II", "Collect 5 000 nodes", Data.STAT_ENERGY_COLLECTED, 5000, NODE_10K),
    NODE_1K("Powerhouse I", "Collect 1 000 nodes", Data.STAT_ENERGY_COLLECTED, 1000, NODE_5K),

    STAR_10K("Change machine III", "Collect 10 000 stars", Data.STAT_RANDOM_COLLECTED, 10000, null),
    STAR_5K("Chance machine II", "Collect 5 000 stars", Data.STAT_RANDOM_COLLECTED, 5000, STAR_10K),
    STAR_1K("Chance machine I", "Collect 1 000 stars", Data.STAT_RANDOM_COLLECTED, 1000, STAR_5K),

    SHIELD_10K("Armorer III", "Collect 10 000 shields", Data.STAT_SHIELDS_COLLECTED, 10000, null),
    SHIELD_5K("Armorer II", "Collect 5 000 shields", Data.STAT_SHIELDS_COLLECTED, 5000, SHIELD_10K),
    SHIELD_1K("Armorer I", "Collect 1 000 shields", Data.STAT_SHIELDS_COLLECTED, 1000, SHIELD_5K),

    SHIELD_FULL("Knight", "Have full shields", Data.STAT_SHIELDS_MAX, 10, null),

    DAMAGE_10K("Meat target III", "Get hit 10 000 times", Data.STAT_DMG_TAKEN, 10000, null),
    DAMAGE_5K("Meat target II", "Get hit 5 000 times", Data.STAT_DMG_TAKEN, 5000, DAMAGE_10K),
    DAMAGE_1K("Meat target I", "Get hit 1 000 times", Data.STAT_DMG_TAKEN, 1000, DAMAGE_5K),

    HEALTH("Doctor", "Upgrade health to maximum", Data.PLAYER_HEALTH, 10, null),

    STRIKE_100("Lightning rod", "Get hit by lightning 100 times", Data.STAT_LIGHTNING_HIT, 100, null),
    STRIKE_25("Feel the power", "Get hit by lightning 25 times", Data.STAT_LIGHTNING_HIT, 25, STRIKE_100),
    STRIKE_1("Feels good", "Get hit by lightning", Data.STAT_LIGHTNING_HIT, 1, STRIKE_25),

    EMPTY("", "", null, 0, null);

    // Params
    private final String label;
    private final String des;
    private final Data data;
    private final int req;
    private final Award follow;

    private boolean awarded;

    // Constructor
    Award(String label, String des, Data data, int req, Award follow) {
        this.label = label;
        this.des = des;
        this.data = data;
        this.req = req;
        this.follow = follow;

        this.awarded = Preferences.get(name(), 0) == 1;
    }

    // Methods
    public String getLabel() {
        return this.label;
    }

    public String getDescription() {
        return this.des;
    }

    public boolean isAwarded() {
        return this.awarded;
    }

    public float getCompletion() {
        if (this.awarded) {
            return 1.0f;
        } else if (this.data != null && this.req != 0) {
            return Math.min(1.0f, (float) this.data.get() / (float) this.req);
        } else {
            return 0.0f;
        }
    }

    public Award get() {
        if (this.awarded && this.follow != null) {
            return this.follow.get();
        } else {
            return this;
        }
    }

    public Award tryAward() {
        if (this.awarded && this.follow != null) {
            return this.follow.tryAward();
        } else if (!this.awarded && this.data.get() >= this.req) {
            this.awarded = true;
            return this;
        } else {
            return null;
        }
    }

    // Controls
    public static void reset() {
        for (Award a : values()) {
            a.awarded = false;
        }
    }

    public static void save() {
        for (Award a : values()) {
            Preferences.set(a.name(), a.awarded ? 1 : 0);
        }
    }

}
