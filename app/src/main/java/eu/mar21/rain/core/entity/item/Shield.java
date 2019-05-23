package eu.mar21.rain.core.entity.item;

import eu.mar21.rain.core.graphics.sprite.Sprite;
import eu.mar21.rain.core.level.Level;
import eu.mar21.rain.core.utils.Resources;

public class Shield extends Item {

    // Constructor
    public Shield(Level level, double x, double y) {
        super(level, x, y, Resources.SHIELD.getWidth(), Resources.SHIELD.getHeight(), new Sprite(Resources.SHIELD), 0.0, 0.0, Item.DEFAULT_DX, Item.DEFAULT_DY);
    }

    // Methods
    @Override
    public void effect() {
        this.level.getData().applyShield();
    }

}
