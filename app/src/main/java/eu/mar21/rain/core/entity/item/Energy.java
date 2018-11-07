package eu.mar21.rain.core.entity.item;

import eu.mar21.rain.core.graphics.sprite.Sprite;
import eu.mar21.rain.core.level.Level;
import eu.mar21.rain.core.utils.Resources;

public class Energy extends Item {

    public static final double WIDTH = 14;
    public static final double HEIGHT = 14;

    public static final double SPRITE_X_OFFSET = -8;
    public static final double SPRITE_Y_OFFSET = -8;

    public Energy(double x, double y, Level level) {
        super(x, y, WIDTH, HEIGHT, new Sprite(Resources.ENERGY), SPRITE_X_OFFSET, SPRITE_Y_OFFSET, level);
    }

    @Override
    public void applyEffect() {
        this.level.getData().addEnergy(1);
    }
}
