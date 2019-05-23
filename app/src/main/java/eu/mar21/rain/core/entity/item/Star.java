package eu.mar21.rain.core.entity.item;

import eu.mar21.rain.core.graphics.sprite.Sprite;
import eu.mar21.rain.core.level.Level;
import eu.mar21.rain.core.utils.Resources;

public class Star extends Item {

    // Constructor
    public Star(Level level, double x, double y) {
        super(level, x, y, Resources.STAR.getWidth(), Resources.STAR.getHeight(), new Sprite(Resources.STAR), 0.0, 0.0, Item.DEFAULT_DX, Item.DEFAULT_DY);
    }

    // Methods
    @Override
    public void effect() {
        this.level.getData().applyRandom();
    }

}
