package eu.mar21.rain.core.level.data;

import eu.mar21.rain.core.device.Preferences;
import eu.mar21.rain.core.utils.functional.Function;

public enum Upgrade {

    HEALTH_EXTRA_9("Extra health IX", "Start with 10 health", 3, null, null),
    HEALTH_EXTRA_8("Extra health VIII", "Start with 9 health", 3, null, HEALTH_EXTRA_9),
    HEALTH_EXTRA_7("Extra health VII", "Start with 8 health", 3, null, HEALTH_EXTRA_8),
    HEALTH_EXTRA_6("Extra health VI", "Start with 7 health", 2, null, HEALTH_EXTRA_7),
    HEALTH_EXTRA_5("Extra health V", "Start with 6 health", 2, null, HEALTH_EXTRA_6),
    HEALTH_EXTRA_4("Extra health IV", "Start with 5 health", 2, null, HEALTH_EXTRA_5),
    HEALTH_EXTRA_3("Extra health III", "Start with 4 health", 1, null, HEALTH_EXTRA_4),
    HEALTH_EXTRA_2("Extra health II", "Start with 3 health", 1, null, HEALTH_EXTRA_3),
    HEALTH_EXTRA("Extra health I", "Start with 2 health", 1, null, HEALTH_EXTRA_2),

    BATTERY_5("Power storage V", "An extra battery", 2, null, null),
    BATTERY_4("Power storage IV", "An extra battery", 2, null, BATTERY_5),
    BATTERY_3("Power storage III", "An extra battery", 1, null, BATTERY_4),
    BATTERY_2("Power storage II", "An extra battery", 1, null, BATTERY_3),
    BATTERY("Power storage I", "A battery to store power inside", 1, null, BATTERY_2),
    BATTERY_LIGHTNING("Lightning trap", "Converts lightning into power", 2, BATTERY::isOwned, null),
    BATTERY_BALANCE("Balancing circuits", "Keep power in left-most battery", 2, BATTERY::isOwned, null),

    SKILL_SHOCK("Shockwave emitter", "Creates a shockwave that destroys acid", 1, null, null),
    SKILL_ARMOR("Armorer", "Gain a piece of armor", 1, null, null),

    SKILL_LIGHTNING_POLE_CHARGE("Charge collectors", "Each strike generates power", 2, null, null),
    SKILL_LIGHTNING_POLE("Lightning pole", "Deploy pole that catches 20 strikes", 5, () -> Data.STAT_LIGHTNING_HIT.get() >= 10, SKILL_LIGHTNING_POLE_CHARGE),

    SKILL_EXP_EXTRA("Efficiency module", "Get twice as much at once", 2, null, null),
    SKILL_EXP("Booster", "Gain 60 seconds of extra experience", 2, null, SKILL_EXP_EXTRA),

    STAR_XP_BOOST("Multiplier", "Stars will drop experience multipliers", 1, null, null),
    STAR_XP_INSTANT("Experience", "Stars will drop experience", 1, null, null),
    STAR_ARMOR("Armor", "Stars will drop armor", 1, null, null),
    STAR_POINT("Upgrade point", "Stars will drop upgrade points", 1, () -> STAR_XP_BOOST.owned && STAR_XP_INSTANT.owned && STAR_ARMOR.owned, null),

    MOVEMENT_EXTRA("Speed", "Move faster", 1, null, null)

    ;

    // Params
    private final String label;
    private final String description;
    private final int cost;
    private final Function<Boolean> req;
    private final Upgrade follow;

    private boolean owned;

    // Constructor
    Upgrade(String label, String description, int cost, Function<Boolean> req, Upgrade follow) {
        this.label = label;
        this.description = description;
        this.cost = cost;
        this.req = req;
        this.follow = follow;

        this.owned = Preferences.get(name(), 0) == 1;
    }

    // Methods
    public String getLabel() {
        return this.label;
    }

    public String getDescription() {
        return this.description;
    }

    public boolean isAvailable() {
        return this.cost <= Data.PLAYER_POINTS.get() && (this.req == null || this.req.apply());
    }

    public boolean isKnown() {
        return this.req == null || this.req.apply();
    }

    public int getCost() {
        return this.cost;
    }

    public boolean isOwned() {
        return this.owned;
    }

    public Upgrade get() {
        if (this.owned && this.follow != null) {
            return this.follow.get();
        } else {
            return this;
        }
    }

    public Upgrade tryBuy() {
        if (this.owned && this.follow != null) {
            return this.follow.tryBuy();
        } else if (!this.owned && isAvailable()) {
            Data.PLAYER_POINTS.add(-this.cost);
            this.owned = true;
            return this;
        } else {
            return null;
        }
    }

    // Controls
    public static void reset() {
        for (Upgrade a : values()) {
            a.owned = false;
        }

        Data.PLAYER_POINTS.set(Data.PLAYER_LEVEL.get() - 1);
    }

    public static void save() {
        for (Upgrade a : values()) {
            Preferences.set(a.name(), a.owned ? 1 : 0);
        }
    }

}
