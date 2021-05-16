package eu.mar21.rain.core.level.data;

import eu.mar21.rain.core.device.Preferences;
import eu.mar21.rain.core.utils.functional.Function;

public enum Award {

    LEVEL_50("Masochist", "Reach level 50", () -> Data.PLAYER_LEVEL.get() / 50.0f, null),
    LEVEL_25("Expert", "Reach level 25", () -> Data.PLAYER_LEVEL.get() / 25.0f, LEVEL_50),
    LEVEL_10("Player", "Reach level 10", () -> Data.PLAYER_LEVEL.get() / 10.0f, LEVEL_25),
    LEVEL_5("Casual", "Reach level 5", () -> Data.PLAYER_LEVEL.get() / 5.0f, LEVEL_10),
    LEVEL_1("Get wet", "Play the game", () -> Data.STAT_GAMES_PLAYED.get() / 1.0f, LEVEL_5),

    SCORE_1M("A million", "Reach 1M score", () -> Data.PLAYER_SCORE.get() / 1000000.0f, null),
    SCORE_500K("Halfway there", "Reach 500K score", () -> Data.PLAYER_SCORE.get() / 500000.0f, SCORE_1M),
    SCORE_100K("100K club", "Reach 100K score", () -> Data.PLAYER_SCORE.get() / 100000.0f, SCORE_500K),

    JUMP_7777("Lucky paw", "Jump 7777 times", () -> Data.STAT_TOTAL_JUMPS.get() / 7777.0f, null),
    JUMP_666("Rabbit", "Jump 666 times", () -> Data.STAT_TOTAL_JUMPS.get() / 666.0f, JUMP_7777),

    NODE_50K("Powerhouse IV", "Collect 50 000 nodes", () -> Data.STAT_ENERGY_COLLECTED.get() / 50000.0f, null),
    NODE_10K("Powerhouse III", "Collect 10 000 nodes", () -> Data.STAT_ENERGY_COLLECTED.get() / 10000.0f, NODE_50K),
    NODE_5K("Powerhouse II", "Collect 5 000 nodes", () -> Data.STAT_ENERGY_COLLECTED.get() / 5000.0f, NODE_10K),
    NODE_1K("Powerhouse I", "Collect 1 000 nodes", () -> Data.STAT_ENERGY_COLLECTED.get() / 1000.0f, NODE_5K),

    STAR_10K("Change machine III", "Collect 10 000 stars", () -> Data.STAT_RANDOM_COLLECTED.get() / 10000.0f, null),
    STAR_5K("Chance machine II", "Collect 5 000 stars", () -> Data.STAT_RANDOM_COLLECTED.get() / 5000.0f, STAR_10K),
    STAR_1K("Chance machine I", "Collect 1 000 stars", () -> Data.STAT_RANDOM_COLLECTED.get() / 1000.0f, STAR_5K),
    STAR_1("?", "?", () -> Data.STAT_RANDOM_COLLECTED.get() / 1.0f, STAR_1K),

    SHIELD_10K("Armorer III", "Collect 10 000 shields", () -> Data.STAT_SHIELDS_COLLECTED.get() / 10000.0f, null),
    SHIELD_5K("Armorer II", "Collect 5 000 shields", () -> Data.STAT_SHIELDS_COLLECTED.get() / 5000.0f, SHIELD_10K),
    SHIELD_1K("Armorer I", "Collect 1 000 shields", () -> Data.STAT_SHIELDS_COLLECTED.get() / 1000.0f, SHIELD_5K),

    SHIELD_FULL("Knight", "Have all 10 shields", () -> Data.STAT_SHIELDS_MAX.get() / 10.0f, null),

    DAMAGE_10K("Meat target III", "Get hit 10 000 times", () -> Data.STAT_DMG_TAKEN.get() / 10000.0f, null),
    DAMAGE_5K("Meat target II", "Get hit 5 000 times", () -> Data.STAT_DMG_TAKEN.get() / 5000.0f, DAMAGE_10K),
    DAMAGE_1K("Meat target I", "Get hit 1 000 times", () -> Data.STAT_DMG_TAKEN.get() / 1000.0f, DAMAGE_5K),

    HEALTH("Doctor", "Upgrade health to maximum", () ->
            (Upgrade.HEALTH_EXTRA_9.isOwned() ? 1.0f / 9.0f : 0.0f) +
            (Upgrade.HEALTH_EXTRA_8.isOwned() ? 1.0f / 9.0f : 0.0f) +
            (Upgrade.HEALTH_EXTRA_7.isOwned() ? 1.0f / 9.0f : 0.0f) +
            (Upgrade.HEALTH_EXTRA_6.isOwned() ? 1.0f / 9.0f : 0.0f) +
            (Upgrade.HEALTH_EXTRA_5.isOwned() ? 1.0f / 9.0f : 0.0f) +
            (Upgrade.HEALTH_EXTRA_4.isOwned() ? 1.0f / 9.0f : 0.0f) +
            (Upgrade.HEALTH_EXTRA_3.isOwned() ? 1.0f / 9.0f : 0.0f) +
            (Upgrade.HEALTH_EXTRA_2.isOwned() ? 1.0f / 9.0f : 0.0f) +
            (Upgrade.HEALTH_EXTRA.isOwned() ? 1.0f / 9.0f : 0.0f)
    , null),

    STRIKE_1000("Lightning rod", "Get hit by lightning 1000 times", () -> Data.STAT_LIGHTNING_HIT.get() / 1000.0f, null),
    STRIKE_250("Feel the power", "Get hit by lightning 250 times", () -> Data.STAT_LIGHTNING_HIT.get() / 250.0f, STRIKE_1000),
    STRIKE_1("Feels good", "Get hit by lightning", () -> Data.STAT_LIGHTNING_HIT.get() / 1.0f, STRIKE_250),

    EVENT_100("Eventfull", "Participate in 100 events", () -> Data.STAT_EVENTS.get() / 100.0f, null),
    EVENT_1("First-timer", "Participate in an event", () -> Data.STAT_EVENTS.get() / 1.0f, EVENT_100),

    EMPTY("", "", () -> 0.0f, null);

    // Params
    private final String label;
    private final String des;
    private final Award follow;
    private final Function<Float> func;

    private boolean awarded;

    // Constructor
    Award(String label, String des, Function<Float> func, Award follow) {
        this.label = label;
        this.des = des;
        this.func = func;
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
        } else {
            return Math.min(this.func.apply(), 1.0f);
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
        } else if (!this.awarded && this.func.apply() >= 1.0f) {
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
