package eu.mar21.rain.core.entity.spawner;

import eu.mar21.rain.core.level.Level;
import eu.mar21.rain.core.utils.functional.TriConsumer;

public class GenericSpawner extends Spawner {

    // Params
    private final TriConsumer<Level, Double, Double> action;

    // Constructor
    public GenericSpawner(Level level, double x, double y, double w, double h, int rate, int rnd, int count, TriConsumer<Level, Double, Double> action) {
        super(level, x, y, w, h, rate, rnd, count);

        this.action = action;
    }

    // Methods
    public void spawn() {
        this.action.accept(this.level, getRndX(), getRndY());
    }

}
