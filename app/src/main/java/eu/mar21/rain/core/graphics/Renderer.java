package eu.mar21.rain.core.graphics;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.View;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import eu.mar21.rain.core.scene.Scene;
import eu.mar21.rain.core.utils.FrameCounter;
import eu.mar21.rain.core.utils.Resources;

@SuppressWarnings("unused")
public class Renderer extends View {

    private Scene scene = null;
    private Scene overlay = null;

    private boolean requested = false;
    private Class<? extends Scene> requestedOverlay;
    private Class<? extends Scene> requestedScene;

    private Activity activity;

    private final Map<Class<? extends Scene>, Object> scenes = new HashMap<>();

    private FrameCounter meter = new FrameCounter();

    public Renderer(Activity activity) {
        super(activity);

        this.activity = activity;
    }

    public Activity getParentActivity() {
        return this.activity;
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
        meter.sample(System.nanoTime());

        if (this.scene != null) this.scene.update(null);
        if (this.overlay != null) this.overlay.update(this.scene);

        c.drawColor(0xFF000000);
        if (this.scene != null) this.scene.draw(c);
        if (this.overlay != null) this.overlay.draw(c);

        c.drawText("" + (int) meter.getAverageFPS(), 5, c.getHeight() - 15, Resources.FONT_DEBUG);

        processRequests();
        invalidate();
    }
}
