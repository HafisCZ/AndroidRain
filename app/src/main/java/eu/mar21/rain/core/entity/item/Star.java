package eu.mar21.rain.core.entity.item;

import eu.mar21.rain.core.graphics.Notification;
import eu.mar21.rain.core.graphics.sprite.Sprite;
import eu.mar21.rain.core.level.Level;
import eu.mar21.rain.core.level.data.Statistics;
import eu.mar21.rain.core.utils.Resources;

public class Star extends Item {

    public Star(double x, double y, Level level) {
        super(x, y, Resources.STAR.getWidth(), Resources.STAR.getHeight(), new Sprite(Resources.STAR), 0, 0, level);
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

        Statistics.STAT_COUNT_STARS.add();
        this.level.showNotification(new Notification("ITEM RECEIVED", selector == 0 ? "ENERGY" : ( selector == 1 ? "SHIELD" : "EXPERIENCE"), null));
    }

}
