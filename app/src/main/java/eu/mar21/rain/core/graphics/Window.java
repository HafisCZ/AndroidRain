package eu.mar21.rain.core.graphics;

import android.content.Context;
import android.graphics.Canvas;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.View;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import eu.mar21.rain.core.Application;
import eu.mar21.rain.core.device.input.InputListener;
import eu.mar21.rain.core.graphics.utils.FrameCounter;
import eu.mar21.rain.core.scene.Scene;
import eu.mar21.rain.core.utils.Logger;
import eu.mar21.rain.core.utils.Resources;

public class Window extends View {

    // Params
    private final Map<Class<? extends Scene>, Object> scenes = new HashMap<>();

    private Scene scene;

    private boolean requested;
    private Class<? extends Scene> request;

    private FrameCounter meter;
    private InputListener listener;

    // Constructor
    public Window(Context c) {
        super(c);

        this.requested = false;

        this.meter = new FrameCounter();
        this.listener = new InputListener();

        setOnTouchListener(this.listener);

        SensorManager sm = (SensorManager) Application.get().getSystemService(Context.SENSOR_SERVICE);
        if (sm != null) {
            sm.registerListener(this.listener, sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
            sm.registerListener(this.listener, sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    // Methods
    public void register(Class<? extends Scene> scene) {
        try {
            Scene inst = scene.getConstructor(Window.class).newInstance(this);
            inst.init();

            this.scenes.put(scene, inst);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException exception) {
            Log.i("AppSceneRegFail", Log.getStackTraceString(exception));
        }

        Logger.log("Registered scene " + scene.getCanonicalName());
    }

    public InputListener getListener() {
        return this.listener;
    }

    public void request(Class<? extends Scene> scene) {
        this.requested = true;
        this.request = scene;
    }

    private void setScene(Class<? extends Scene> scene) {
        this.listener.reset();

        if (this.scene != null) {
            this.scene.end();
            this.scene = null;
        }

        if (scene != null) {
            this.scene = (Scene) this.scenes.get(scene);

            if (this.scene != null) {
                this.scene.begin();
            }
        }
    }

    @Override
    protected void onDraw(Canvas c) {
        meter.sample(System.nanoTime());

        c.drawColor(0xFF000000);

        if (this.scene != null) {
            this.scene.update(null);
            this.scene.draw(c);
        }

        c.drawText("" + (int) meter.getAverageFPS(), 10, getHeight() - 15, Resources.PAINT_DEBUG);

        if (this.requested) {
            if (this.scene != this.scenes.get(this.request)) {
                setScene(this.request);
            }

            this.requested = false;
        }

        invalidate();
    }
}
