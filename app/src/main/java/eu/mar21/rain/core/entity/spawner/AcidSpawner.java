package eu.mar21.rain.core.entity.spawner;

import eu.mar21.rain.core.entity.mob.Acid;
import eu.mar21.rain.core.level.Level;
import eu.mar21.rain.core.utils.Resources;

public class AcidSpawner extends Spawner {

    public AcidSpawner(double x, double y, double width, double height, Level level, int rate, int variation, int count) {
        super(x, y, width - Resources.ACID[0].getWidth(), height, level, rate, variation, count);
    }

    @Override
    public void spawn() {
        this.level.add(new Acid(getRandomX(), getRandomY(), this.level));
    }

}
