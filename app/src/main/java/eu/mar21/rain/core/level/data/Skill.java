package eu.mar21.rain.core.level.data;

import eu.mar21.rain.core.entity.particle.ShockParticle;
import eu.mar21.rain.core.level.Level;
import eu.mar21.rain.core.utils.Resources;
import eu.mar21.rain.core.utils.functional.TriConsumer;

public enum Skill {

    // Values
    SHOCKWAVE((L, X, Y) -> L.add(new ShockParticle(L, X, Resources.SCREEN_HEIGHT)), 1, 5),
    SHIELD_SPAWN((L, X, Y) -> L.getData().applyShield(), 2, 1),
    EXPERIENCE_SPAWN((L, X, Y) -> L.getData().applyExperienceMultiplier(2 * Data.UPGRADE_SKILL_XPBOOST.get(), 60), 3, 60);

    // Params
    private final TriConsumer<Level, Double, Double> effect;
    private final int power;
    private final int decay;

    // Constructor
    Skill(TriConsumer<Level, Double, Double> effect, int power, int decay) {
        this.effect = effect;
        this.power = power;
        this.decay = decay;
    }

    // Methods
    public int getPower() {
        return this.power;
    }

    public int getDecay() {
        return this.decay;
    }

    public void applyEffect(Level level, double x, double y) {
        this.effect.accept(level, x, y);
    }

}
