package eu.mar21.rain.core.entity.particle;

import eu.mar21.rain.core.Level.Level;
import eu.mar21.rain.core.entity.Entity;

public abstract class Particle extends Entity {

    protected Particle(double x, double y, double width, double height, Level level) {
        super(x, y, width, height, level);
    }

    @Override
    public abstract void tick();

}