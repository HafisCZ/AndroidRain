package eu.mar21.rain.core.scene;

import android.content.Context;
import android.graphics.Canvas;
import android.hardware.Sensor;
import android.hardware.SensorManager;

import eu.mar21.rain.core.Application;
import eu.mar21.rain.core.device.input.InputListener;
import eu.mar21.rain.core.graphics.Window;
import eu.mar21.rain.core.level.Level;
import eu.mar21.rain.core.scene.menu.Menu;
import eu.mar21.rain.core.ui.Panel;
import eu.mar21.rain.core.ui.Text;
import eu.mar21.rain.core.utils.Resources;

public class Game extends Scene {

    // Params
    private Level level;

    // Constructor
    public Game(Window w) {
        super(w);

        this.level = new Level(this.window.getListener());
    }

    // Methods
    @Override
    public void init() {
        this.view = new Panel(0, 0, 1, 1).setBackground(Resources.PAINT_0);
        new Text(this.view, "GAME PAUSED").setPosition(0.5f, 0.6f).setForeground(Resources.PAINT_M_W_0100_C);
        new Text(this.view,"CLICK BELOW TO UNPAUSE THE GAME").setPosition(0.5f, 0.7f).setForeground(Resources.PAINT_M_W_0020_C);
        new Text(this.view,"OR CLICK ABOVE TO RETURN TO MENU").setPosition(0.5f, 0.75f).setForeground(Resources.PAINT_M_W_0020_C);
    }

    @Override
    public void update(Scene s) {
        this.level.tick();

        if (this.level.isExiting()) {
            this.level.reset();
            this.window.request(Menu.class);
        }
    }

    @Override
    public void draw(Canvas c) {
        this.level.draw(c);

        if (this.level.isFrozen()) {
            this.view.show(c);
        }
    }

    @Override
    public void begin() {
        this.level.reset();
    }

}
