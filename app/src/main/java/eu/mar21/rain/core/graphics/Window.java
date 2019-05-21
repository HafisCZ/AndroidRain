package eu.mar21.rain.core.graphics;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.View;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import eu.mar21.rain.core.graphics.utils.FrameCounter;
import eu.mar21.rain.core.scene.Scene;
import eu.mar21.rain.core.utils.Resources;

public class Window extends View {

    // Scene data
    private final Map<Class<? extends Scene>, Object> scenes = new HashMap<>();
    private Scene scene;
    private boolean requested;
    private Class<? extends Scene> requestedScene;

    // Utils
    private FrameCounter meter = new FrameCounter();

    // Constructor
    public Window(Context c) {
        super(c);
    }

    // Methods
    public void registerScene(Class<? extends Scene> scene) {
        try {
            Scene inst = scene.getConstructor(Window.class).newInstance(this);
            inst.init();

            this.scenes.put(scene, inst);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException exception) {
            Log.i("AppSceneRegFail", Log.getStackTraceString(exception));
        }
    }

    public void requestScene(Class<? extends Scene> scene) {
        this.requested = true;
        this.requestedScene = scene;
    }

    private void setScene(Class<? extends Scene> scene) {
        if (this.scene != null) {
            this.scene.end();
            this.scene = null;
        }

        if (scene != null) {
            this.scene = (Scene) this.scenes.get(scene);

            if (this.scene != null) {
                this.scene.begin();

                setOnTouchListener(this.scene.getListener());
            }
        } else {
            setOnTouchListener(null);
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

        c.drawText("" + (int) meter.getAverageFPS(), 5, getHeight() - 15, Resources.PAINT_DEBUG);

        if (this.requested) {
            if (this.scene != this.scenes.get(this.requestedScene)) {
                setScene(this.requestedScene);
            }

            this.requested = false;
        }

        invalidate();
    }
}
