package eu.mar21.rain.core.entity.spawner;

import eu.mar21.rain.core.entity.item.Shield;
import eu.mar21.rain.core.level.Level;
import eu.mar21.rain.core.utils.Resources;

public class ArmorSpawner extends Spawner {

    // Constructor
    public ArmorSpawner(Level level, double x, double y, double sx, double sy, int rate, int rnd, int count) {
        super(level, x, y, sx - Resources.SHIELD.getWidth(), sy, rate, rnd, count);
    }

    // methods
    @Override
    public void spawn() {
        this.level.add(new Shield(this.level, getRndX(), getRndY()));
    }

}
