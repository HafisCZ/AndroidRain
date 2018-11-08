package eu.mar21.rain.core.level.data;

import eu.mar21.rain.core.entity.particle.ShockParticle;
import eu.mar21.rain.core.level.Level;
import eu.mar21.rain.core.utils.Resources;
import eu.mar21.rain.core.utils.TriConsumer;

public enum Skill {

    SHOCKWAVE((X, Y, L) -> L.add(new ShockParticle(X, Resources.SCREEN_HEIGHT, 0, 0, L)), 2 * 60, 5),
    SHIELD_SPAWN((X, Y, L) -> L.getData().addShield(), 60 >> 1, 3),
    EXPERIENCE_SPAWN((X, Y, L) -> L.getData().addExperienceBoost(2, 60 * 60), 60 * 60, 1);

    private final TriConsumer<Double, Double, Level> effect;
    private final int duration;
    private final int rateMultiplier;

    private Skill(TriConsumer<Double, Double, Level> consumer, int duration, int rateMultiplier) {
        this.effect = consumer;
        this.duration = duration;
        this.rateMultiplier = rateMultiplier;
    }

    public void applyEffect(double x, double y, Level level) {
        effect.accept(x, y, level);
    }

    public int getDuration() {
        return duration;
    }

    public int getRateMultiplier() {
        return rateMultiplier;
    }

}
