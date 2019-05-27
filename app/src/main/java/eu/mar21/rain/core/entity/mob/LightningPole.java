package eu.mar21.rain.core.entity.mob;

import eu.mar21.rain.core.graphics.sprite.Sprite;
import eu.mar21.rain.core.level.Level;
import eu.mar21.rain.core.utils.Resources;

public class LightningPole extends Mob {

    // Params
    private int durability;

    // Constructor
    public LightningPole(Level level, double x, int durability) {
        super(level, x, Resources.SCREEN_HEIGHT - Resources.POLE.getHeight(), Resources.POLE.getWidth(), Resources.POLE.getHeight(), new Sprite(Resources.POLE), 0, 0);

        this.durability = durability;
    }

    // Methods
    @Override
    public void tick() {
        if (this.durability <= 0) {
            remove();
        }
    }

    public void strike() {
        this.durability--;
    }

}
