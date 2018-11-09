package eu.mar21.rain.core.entity.spawner;

import eu.mar21.rain.core.entity.item.Energy;
import eu.mar21.rain.core.level.Level;
import eu.mar21.rain.core.utils.Resources;

public class EnergySpawner extends Spawner {

    public EnergySpawner(double x, double y, double width, double height, Level level, int rate, int variation, int count) {
        super(x, y, width - Resources.ENERGY.getWidth(), height, level, rate, variation, count);
    }

    @Override
    public void spawn() {
        this.level.add(new Energy(getRandomX(), getRandomY(), this.level));
    }

}
