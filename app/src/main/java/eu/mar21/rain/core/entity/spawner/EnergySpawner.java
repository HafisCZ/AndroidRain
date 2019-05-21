package eu.mar21.rain.core.entity.spawner;

import eu.mar21.rain.core.entity.item.Energy;
import eu.mar21.rain.core.level.Level;
import eu.mar21.rain.core.utils.Resources;

public class EnergySpawner extends Spawner {

    // Constructor
    public EnergySpawner(Level level, double x, double y, double sx, double sy, int rate, int rnd, int count) {
        super(level, x, y, sx - Resources.ENERGY.getWidth(), sy, rate, rnd, count);
    }

    // Methods
    @Override
    public void spawn() {
        this.level.add(new Energy(this.level, getRndX(), getRndY()));
    }

}
