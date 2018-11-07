package eu.mar21.rain.core.entity.spawner;

import eu.mar21.rain.core.level.Level;
import eu.mar21.rain.core.entity.item.Shield;

public class ArmorSpawner extends Spawner {

    public ArmorSpawner(double x, double y, double width, double height, Level level, int rate, int variation, int count) {
        super(x, y, width, height, level, rate, variation, count);
    }

    @Override
    public void spawn() {
        this.level.add(new Shield(getRandomX(), getRandomY(), this.level));
    }

}
