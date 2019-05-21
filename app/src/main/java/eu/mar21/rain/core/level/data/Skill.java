package eu.mar21.rain.core.level.data;

import eu.mar21.rain.core.entity.particle.ShockParticle;
import eu.mar21.rain.core.level.Level;
import eu.mar21.rain.core.utils.Resources;
import eu.mar21.rain.core.utils.Consumer;

public enum Skill {

    SHOCKWAVE((X, Y, L) -> L.add(new ShockParticle(L, X, Resources.SCREEN_HEIGHT, 0, 0)), 5, 15),
    SHIELD_SPAWN((X, Y, L) -> L.getData().addShield(), 1, 40),
    EXPERIENCE_SPAWN((X, Y, L) -> L.getData().addExperienceBoost(2, 60), 60, 60);

    private final Consumer<Double, Double, Level> effect;
    private final int duration;
    private final int power;

    Skill(Consumer<Double, Double, Level> consumer, int duration, int power) {
        this.power = power;
        this.effect = consumer;
        this.duration = duration;
    }

    public int getPowerRequired() {
        return power;
    }

    public void applyEffect(double x, double y, Level level) {
        effect.accept(x, y, level);
    }

    public int getDuration() {
        return duration;
    }

}
