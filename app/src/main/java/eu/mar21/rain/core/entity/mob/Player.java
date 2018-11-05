package eu.mar21.rain.core.entity.mob;

import eu.mar21.rain.core.Level.Level;

public class Player extends Mob {

    public Player(double x, double y, Level level) {
        super(x, y, 0, 0, null, 0, 0, level);
    }

    @Override
    public void tick() {

    }

}
