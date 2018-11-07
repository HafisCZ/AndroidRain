package eu.mar21.rain.core.entity.item;

import eu.mar21.rain.core.level.Level;
import eu.mar21.rain.core.graphics.sprite.Sprite;
import eu.mar21.rain.core.utils.Resources;

public class Star extends Item {

    public static final double WIDTH = 14;
    public static final double HEIGHT = 14;
    public static final double SPRITE_X_OFFSET = -4;
    public static final double SPRITE_Y_OFFSET = -4;

    public Star(double x, double y, Level level) {
        super(x, y, WIDTH, HEIGHT, new Sprite(Resources.STAR), SPRITE_X_OFFSET, SPRITE_Y_OFFSET, level);
    }

    @Override
    public void applyEffect() {
        int selector = RANDOM.nextInt(3);

        switch (selector) {
            case 0:
                this.level.getData().addEnergy(5);
                break;
            case 1:
                this.level.getData().addShield();
                break;
            case 2:
                this.level.getData().addExperience(10 * 60);
                break;
        }

    }

}
