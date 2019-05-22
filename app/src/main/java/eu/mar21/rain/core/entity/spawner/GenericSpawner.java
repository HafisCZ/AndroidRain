package eu.mar21.rain.core.entity.spawner;

import eu.mar21.rain.core.level.Level;
import eu.mar21.rain.core.utils.Consumer;

public class GenericSpawner extends Spawner {

    // Params
    private final Consumer<Level> action;

    // Constructor
    public GenericSpawner(Level level, int rate, int rnd, int count, Consumer<Level> action) {
        super(level, 0, 0, 0, 0, rate, rnd, count);

        this.action = action;
    }

    // Methods
    public void spawn() {
        this.action.accept(this.level);
    }

}
