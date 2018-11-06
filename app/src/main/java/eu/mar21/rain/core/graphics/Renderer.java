package eu.mar21.rain.core.graphics;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import eu.mar21.rain.core.scene.Scene;
import eu.mar21.rain.core.utils.Resources;

@SuppressWarnings("unused")
public class Renderer extends View {

    private Scene scene = null;
    private Scene overlay = null;

    private boolean requested = false;
    private Class<? extends Scene> requestedOverlay;
    private Class<? extends Scene> requestedScene;

    private final Map<Class<? extends Scene>, Object> scenes = new HashMap<>();

    public Renderer(Context context) {
        super(context);
    }

    public Scene getActiveScene() {
        return this.scene;
    }

    public Scene getActiveOverlay() {
        return this.overlay;
    }

    public void registerScene(Class<? extends Scene> ... scenes) {
        try {
            for (Class<? extends Scene> scene : scenes) {
                this.scenes.put(scene, scene.getConstructor(Renderer.class).newInstance(this));
            }
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException exception) {
            Log.i("AppSceneRegFail", Log.getStackTraceString(exception));
        }
    }

    public void requestScene(Class<? extends Scene> scene) {
        this.requested = true;
        this.requestedScene = scene;
    }

    public void requestOverlay(Class<? extends Scene> overlay) {
        this.requested = true;
        this.requestedOverlay = overlay;
    }

    private void setScene(Class<? extends Scene> scene) {
        if (this.scene != null) {
            this.scene.end();
            this.scene = null;
        }

        if (scene != null) {
            this.scene = (Scene) this.scenes.get(scene);
            this.scene.begin();

            setOnTouchListener(this.scene.getDedicatedListener());
        } else {
            setOnTouchListener(null);
        }
    }

    private void setOverlay(Class<? extends Scene> overlay) {
        if (this.overlay != null) {
            this.overlay.end();
            this.overlay = null;
        }

        if (overlay != null) {
            this.overlay = (Scene) this.scenes.get(overlay);
            this.overlay.begin();
        }
    }

    private void processRequests() {
        if (this.requested) {
            if (this.scene != this.scenes.get(this.requestedScene)) {
                setScene(this.requestedScene);
            }

            if (this.overlay != this.scenes.get(this.requestedOverlay)) {
                setScene(this.requestedOverlay);
            }

            this.requested = false;
        }
    }

    @Override
    protected void onDraw(Canvas c) {
        long ubegin = System.currentTimeMillis();

        if (this.scene != null) this.scene.update(null);
        if (this.overlay != null) this.overlay.update(this.scene);

        long dbegin = System.currentTimeMillis();

        c.drawColor(0xFF000000);

        if (this.scene != null) this.scene.draw(c);
        if (this.overlay != null) this.overlay.draw(c);

        long end = System.currentTimeMillis();

        c.drawText("U" + Long.toString(dbegin - ubegin), 10, 30, Resources.FONT);
        c.drawText("D" + Long.toString(end - dbegin), 10, 60, Resources.FONT);

        processRequests();
        invalidate();
    }
}
