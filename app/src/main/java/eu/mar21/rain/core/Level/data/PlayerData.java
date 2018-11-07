package eu.mar21.rain.core.level.data;

import android.graphics.Canvas;

import eu.mar21.rain.core.level.Level;
import eu.mar21.rain.core.utils.Resources;

public class PlayerData {

    private int health;
    private int shield;
    private int experience;
    private int energy;

    public PlayerData() {
        this.health = 5;
        this.shield = 5;
        this.energy = 0;
        this.experience = 0;
    }

    public void damage() {
        if (this.health > 0) {
            if (this.shield > 0) {
                this.shield--;
            } else {
                this.health--;
            }
        }
    }

    public void drawDebug(Canvas c) {
        c.drawText("HP: " + Integer.toString(this.health), c.getWidth() - 150, 30, Resources.FONT);
        c.drawText("AR: " + Integer.toString(this.shield), c.getWidth() - 150, 60, Resources.FONT);
        c.drawText("EN: " + Integer.toString(this.energy), c.getWidth() - 150, 90, Resources.FONT);
        c.drawText("XP: " + Integer.toString(this.experience), c.getWidth() - 150, 120, Resources.FONT);
    }

    public int getHealth() {
        return this.health;
    }

    public int getShield() {
        return this.shield;
    }

    public int getExperience() {
        return this.experience;
    }

    public int getEnergy() {
        return this.energy;
    }

    public void skill(Level level) {

    }

    public void addEnergy(int amount) {
        this.energy += amount;
    }

    public void addShield() {
        this.shield++;
    }

    public void addExperience(int amount) {
        this.experience += amount;
    }
}
