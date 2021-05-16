package eu.mar21.rain.core.level.event;

import eu.mar21.rain.core.entity.spawner.AcidSpawner;
import eu.mar21.rain.core.level.Level;
import eu.mar21.rain.core.utils.Number;
import eu.mar21.rain.core.utils.Resources;
import eu.mar21.rain.core.utils.Timer;

public class AcidEvent extends Event {

    // Params
    private final Timer timer;
    private final AcidSpawner spawner;

    // Constructor
    public AcidEvent(Level level) {
        super(level);

        this.timer = new Timer(this::remove, Number.between(1800, 3601));
        this.spawner = new AcidSpawner(this.level,0, -50, Resources.WIDTH, 0,  20, 5, 2);
        this.level.add(this.spawner);
    }

    // Methods
    @Override
    public void onUpdate() {
        this.timer.tick();
    }

    @Override
    public void onExit() {
        this.spawner.remove();
    }

}
