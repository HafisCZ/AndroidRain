package eu.mar21.rain.core.entity.spawner;

import eu.mar21.rain.core.entity.item.Star;
import eu.mar21.rain.core.level.Level;
import eu.mar21.rain.core.utils.Resources;

public class StarSpawner extends Spawner {

    // Constructor
    public StarSpawner(Level level, double x, double y, double sx, double sy, int rate, int rnd, int count) {
        super(level, x, y, sx - Resources.STAR.getWidth(), sy, rate, rnd, count);
    }

    // Methods
    @Override
    public void spawn() {
        this.level.add(new Star(this.level, getRndX(), getRndY()));
    }

}
