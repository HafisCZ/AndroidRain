package eu.mar21.rain.core.scene;

import android.content.Context;
import android.graphics.Canvas;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.util.Log;

import eu.mar21.rain.core.graphics.Renderer;
import eu.mar21.rain.core.level.Level;
import eu.mar21.rain.core.scene.menu.Menu;
import eu.mar21.rain.core.utils.input.InputListener;

public class Game extends Scene {

    private Level level;
    private InputListener input;

    @SuppressWarnings("all")
    public Game(Renderer r) {
        super(r);

        this.input = new InputListener();
        this.level = new Level(input);

        SensorManager sm = (SensorManager) this.renderer.getParentActivity().getSystemService(Context.SENSOR_SERVICE);
        try {
            sm.registerListener(this.input, sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
            sm.registerListener(this.input, sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_NORMAL);
        } catch (Exception e) {
            Log.e("SENSOR INPUT ERROR", "Sensor is NULL");
        }
    }

    public void update(Scene s) {
        this.level.tick();

        if (this.level.isExiting()) {
            this.level.reset();
            this.renderer.requestScene(Menu.class);
        }
    }

    public void draw(Canvas c) {
        this.level.draw(c);
    }

    @Override
    public InputListener getDedicatedListener() {
        return this.input;
    }

    public void begin() {
        this.level.reset();
    }

    public void end() {

    }

    public void init() {

    }

}
