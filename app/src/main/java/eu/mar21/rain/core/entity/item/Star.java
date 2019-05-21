package eu.mar21.rain.core.entity.item;

import eu.mar21.rain.core.graphics.Notification;
import eu.mar21.rain.core.graphics.sprite.Sprite;
import eu.mar21.rain.core.level.Level;
import eu.mar21.rain.core.level.data.Statistics;
import eu.mar21.rain.core.utils.Resources;

public class Star extends Item {

    // Constructor
    public Star(Level level, double x, double y) {
        super(level, x, y, Resources.STAR.getWidth(), Resources.STAR.getHeight(), new Sprite(Resources.STAR), 0.0, 0.0, Item.DEFAULT_DX, Item.DEFAULT_DY);
    }

    // Methods
    @Override
    public void effect() {
        switch (RANDOM.nextInt(Statistics.UPGRADE_RANDOM.get())) {
            case 1 : {
                int experience = 5 + RANDOM.nextInt(this.level.getData().getRequiredExperience() / 5);
                this.level.getData().addExperience(experience);
                this.level.showNotification(new Notification(Notification.NotificationStyle.GREEN,"ITEM RECEIVED", experience + " EXP"));
                break;
            }
            case 2 : {
                this.level.getData().addShield();
                this.level.showNotification(new Notification(Notification.NotificationStyle.GREEN,"ITEM RECEIVED", "SHIELD"));
                break;
            }
            case 3 : {
                int multiplier = 2 + RANDOM.nextInt(Statistics.PLAYER_LEVEL.get());
                this.level.getData().addExperienceBoost(multiplier, 10);
                this.level.showNotification(new Notification(Notification.NotificationStyle.GREEN, "ITEM RECEIVED", multiplier + "X EXP FOR 10 SECONDS"));
                break;
            }
            case 4 : {
                if (RANDOM.nextInt(20) == 0) {
                    this.level.getData().addPoint();
                    this.level.showNotification(new Notification(Notification.NotificationStyle.YELLOW,"ITEM RECEIVED", "LEVEL POINT"));
                    break;
                }
            }
            default : {
                if (this.level.getData().getSkill() != null) {
                    int energy = 4 + RANDOM.nextInt(this.level.getData().getSkill().getPowerRequired() / 4);

                    this.level.getData().addEnergy(energy);
                    this.level.showNotification(new Notification(Notification.NotificationStyle.GREEN,"ITEM RECEIVED", energy + " ENERGY"));
                    break;
                }
            }
        }

        Statistics.STAT_RANDOM_COLLECTED.add(1);
    }

}
