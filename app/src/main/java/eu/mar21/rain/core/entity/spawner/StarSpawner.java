package eu.mar21.rain.core.entity.spawner;

import eu.mar21.rain.core.entity.item.Star;
import eu.mar21.rain.core.level.Level;

public class StarSpawner extends Spawner {

    public StarSpawner(double x, double y, double width, double height, Level level, int rate, int variation, int count) {
        super(x, y, width, height, level, rate, variation, count);
    }

    @Override
    public void spawn() {
        this.level.add(new Star(getRandomX(), getRandomY(), this.level));
    }

}
