package eu.mar21.rain.core.level.event;

import eu.mar21.rain.core.entity.particle.FlashParticle;
import eu.mar21.rain.core.level.Level;
import eu.mar21.rain.core.utils.Resources;

public class StormEvent extends Event {

    // Params
    private int ticks;
    private int decay;

    // Constructor
    public StormEvent(Level level) {
        super(level);
    }

    // Methods
    @Override
    public void onStart() {
        this.ticks = 60 * (10 + RANDOM.nextInt(20));
        this.decay = 0;
    }

    @Override
    public void onUpdate() {
        if (--this.decay < 0) {
            this.level.add(new FlashParticle(this.level, Resources.SCREEN_WIDTH * RANDOM.nextDouble(), 10));
            this.decay = 30 + RANDOM.nextInt(150);
        }

        if (--this.ticks < 0) {
            remove();
        }
    }

    @Override
    public void onRemoval() {

    }

}
