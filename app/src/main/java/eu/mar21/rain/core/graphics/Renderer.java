package eu.mar21.rain.core.graphics;

import android.app.Activity;
import android.graphics.Canvas;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import eu.mar21.rain.core.error.SceneNotRegisteredException;
import eu.mar21.rain.core.graphics.utils.FrameCounter;
import eu.mar21.rain.core.scene.Scene;
import eu.mar21.rain.core.utils.Resources;

@SuppressWarnings("unused")
public class Renderer extends View {

    private final Map<Class<? extends Scene>, Object> scenes = new HashMap<>();

    private Scene scene = null;

    private boolean requested;
    private Class<? extends Scene> requestedScene;

    private Activity activity;

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

    public void registerScene(Class<? extends Scene> scene) {
        try {
            this.scenes.put(scene, scene.getConstructor(Renderer.class).newInstance(this));
            ((Scene) this.scenes.get(scene)).init();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException exception) {
            Log.i("AppSceneRegFail", Log.getStackTraceString(exception));
        }
    }

    public void requestScene(Class<? extends Scene> scene) {
        this.requestedScene = scene;
        this.requested = true;
    }

    private void setScene(Class<? extends Scene> scene) {
        if (this.scene != null) {
            this.scene.end();
            this.scene = null;
        }

        if (scene != null) {
            this.scene = (Scene) this.scenes.get(scene);

            if (this.scene == null) {
                try {
                    throw new SceneNotRegisteredException(scene);
                } catch (SceneNotRegisteredException e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            this.scene.begin();

            setOnTouchListener(this.scene.getDedicatedListener());
        } else {
            setOnTouchListener(null);
        }
    }

    private void processRequests() {
        if (this.requested) {
            if (this.scene != this.scenes.get(this.requestedScene)) {
                setScene(this.requestedScene);
            }

            this.requested = false;
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

        c.drawText("" + (int) meter.getAverageFPS(), 5, getHeight() - 15, Resources.FONT_DEBUG);

        processRequests();
        invalidate();
    }
}
