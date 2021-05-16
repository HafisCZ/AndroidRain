package eu.mar21.rain.core.level.event;

import eu.mar21.rain.core.entity.particle.FlashParticle;
import eu.mar21.rain.core.level.Level;
import eu.mar21.rain.core.utils.Number;
import eu.mar21.rain.core.utils.Resources;
import eu.mar21.rain.core.utils.Timer;

public class StormEvent extends Event {

    // Params
    private final Timer timer;
    private final Timer light;

    // Constructor
    public StormEvent(Level level) {
        super(level);

        this.timer = new Timer(this::remove, Number.between(900, 2701));
        this.light = new Timer(() -> this.level.add(new FlashParticle(this.level, Number.between(0, Resources.WIDTH), Resources.HEIGHT, 10)), Number.between(30, 181));
    }

    // Methods
    @Override
    public void onUpdate() {
        this.timer.tick();

        if (this.light.tick()) {
            this.light.set(Number.between(30, 181));
        }
    }

}
