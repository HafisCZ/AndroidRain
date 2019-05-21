package eu.mar21.rain.core.entity.particle;

import eu.mar21.rain.core.entity.Entity;
import eu.mar21.rain.core.graphics.Drawable;
import eu.mar21.rain.core.level.Level;

public abstract class Particle extends Entity implements Drawable {

    //Constructor
    protected Particle(Level level, double x, double y, double sx, double sy) {
        super(level, x, y, sx, sy);
    }

}
