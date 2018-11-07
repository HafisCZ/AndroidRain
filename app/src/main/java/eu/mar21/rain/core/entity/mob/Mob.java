package eu.mar21.rain.core.entity.mob;

import java.util.Objects;

import eu.mar21.rain.core.level.Level;
import eu.mar21.rain.core.entity.Entity;
import eu.mar21.rain.core.graphics.sprite.Sprite;

public abstract class Mob extends Entity {

    public Mob(double x, double y, double width, double height, Sprite sprite, double offsetX, double offsetY, Level level) {
        super(x, y, width, height, Objects.requireNonNull(sprite), offsetX, offsetY, level);
    }

}
