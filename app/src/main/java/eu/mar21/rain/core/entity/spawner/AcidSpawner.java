package eu.mar21.rain.core.entity.spawner;

import eu.mar21.rain.core.entity.mob.Acid;
import eu.mar21.rain.core.level.Level;
import eu.mar21.rain.core.utils.Resources;

public class AcidSpawner extends Spawner {

    // Constructor
    public AcidSpawner(Level level, double x, double y, double sx, double sy, int rate, int rnd, int count) {
        super(level, x, y, sx - Resources.ACID[0].getWidth(), sy, rate, rnd, count);
    }

    // Methods
    @Override
    public void spawn() {
        this.level.add(new Acid(this.level, getRndX(), getRndY()));
    }

}
