package eu.mar21.rain.core.level.data;

import eu.mar21.rain.core.level.Level;

public class PlayerData {

    private int health;
    private int armor;

    public PlayerData() {
        this.health = 5;
        this.armor = 0;
    }

    public boolean damage() {
        return true;
    }

    public int getHealth() {
        return this.health + this.armor;
    }

    public void skill(Level level) {

    }

}
