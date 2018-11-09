package eu.mar21.rain.core.entity.item;

import eu.mar21.rain.core.graphics.sprite.Sprite;
import eu.mar21.rain.core.level.Level;
import eu.mar21.rain.core.utils.Resources;

public class Energy extends Item {

    public Energy(double x, double y, Level level) {
        super(x, y, Resources.ENERGY.getWidth(), Resources.ENERGY.getHeight(), new Sprite(Resources.ENERGY), 0, 0, level);
    }

    @Override
    public void applyEffect() {
        this.level.getData().addEnergy(1);
    }
}
