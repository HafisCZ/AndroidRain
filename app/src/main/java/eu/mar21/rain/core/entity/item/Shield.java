package eu.mar21.rain.core.entity.item;

import eu.mar21.rain.core.graphics.sprite.Sprite;
import eu.mar21.rain.core.level.Level;
import eu.mar21.rain.core.utils.Resources;

public class Shield extends Item {

    public static final double WIDTH = 40;
    public static final double HEIGHT = 60;

    public Shield(double x, double y, Level level) {
        super(x, y, WIDTH, HEIGHT, new Sprite(Resources.SHIELD), 0, 0, level);
    }

    @Override
    public void applyEffect() {
        this.level.getData().addShield();
    }

}
