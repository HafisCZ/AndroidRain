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

    private Scene activeScene;
    private Scene activeOverlay;

    private boolean sheduledAny;
    private Class<? extends Scene> sheduledOverlay;
    private Class<? extends Scene> sheduledScene;

    private Activity parent;
    private GestureDetector gestureDetector;

    private Map<Class<? extends Scene>, Object> scenes;

    private long beginFrameMs;
    {
        scenes = new HashMap<>();

        activeOverlay = null;
        activeScene = null;

        sheduledOverlay = null;
        sheduledScene = null;

        sheduledAny = false;
    }

    public Renderer(Context c) {
        super(c);
    }

    public Renderer(Context c, AttributeSet s) {
        super(c, s);
    }

    public Renderer(Context c, AttributeSet s, int d) {
        super(c, s, d);
    }

    public void setParentActivity(Activity parent) {
        this.parent = parent;
    }

    private void setTouchListener(Scene scene) {
        this.gestureDetector = null;
        setOnTouchListener(scene.getDedicatedListener());
    }

    @SuppressWarnings("all")
    private <T extends GestureDetector.SimpleOnGestureListener> void setGestureListener(T listener) {
        this.gestureDetector = new GestureDetector(this.parent, listener);
        setOnTouchListener((View v, MotionEvent e) -> {
            gestureDetector.onTouchEvent(e);
            return true;
        });
    }

    public Scene getActiveScene() {
        return this.activeScene;
    }

    public Scene getActiveOverlay() {
        return this.activeOverlay;
    }

    public void registerScene(Class<? extends Scene> scene) {
        try {
            scenes.put(scene, scene.getConstructor(Renderer.class).newInstance(this));
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException exception) {
            Log.i("AppSceneRegFail", Log.getStackTraceString(exception));
        }
    }

    public void sheduleActiveOverlay(Class<? extends Scene> overlay) {
        this.sheduledOverlay = overlay;
        this.sheduledAny = true;
    }

    public void sheduleActiveScene(Class<? extends Scene> scene) {
        this.sheduledScene = scene;
        this.sheduledAny = true;
    }

    private void setActiveScene(Class<? extends Scene> scene) {
        Log.i("AppSetScene", "" + scene);
        if (this.activeScene != null) {
            this.activeScene.end();
        }

        this.activeScene = (Scene) this.scenes.get(scene);
        this.activeScene.begin();

        if (this.activeOverlay == null) {
            if (this.activeScene.getDedicatedListener() == null) {
                setGestureListener(this.activeScene);
            } else {
                setTouchListener(this.activeScene);
            }
        }
    }

    private void setActiveOverlay(Class<? extends Scene> overlay) {
        Log.i("AppSetOverlay", "" + overlay);
        if (overlay == null) {
            this.activeOverlay.end();
            if (this.activeScene != null) {
                this.activeScene.begin();
                if (this.activeScene.getDedicatedListener() == null) {
                    setGestureListener(this.activeScene);
                } else {
                    setTouchListener(this.activeScene);
                }
            }

            this.activeOverlay = null;
        } else {
            this.activeOverlay = (Scene) this.scenes.get(overlay);
            this.activeOverlay.begin();

            if (this.activeOverlay.getDedicatedListener() == null) {
                setGestureListener(this.activeOverlay);
            } else {
                setTouchListener(this.activeOverlay);
            }

            if (this.activeScene != null) {
                this.activeScene.end();
            }
        }
    }

    private void updateSheduled() {
        if (this.sheduledAny) {
            if (this.activeOverlay != scenes.get(this.sheduledOverlay)) {
                setActiveOverlay(this.sheduledOverlay);
            }

            if (this.activeScene != scenes.get(this.sheduledScene)) {
                setActiveScene(this.sheduledScene);
            }

            this.sheduledAny = false;
        }
    }

    @Override
    protected void onDraw(Canvas c) {
        this.beginFrameMs = System.currentTimeMillis();

        c.drawRGB(0, 0, 0);

        if (this.activeScene != null) {
            this.activeScene.update(null);
            this.activeScene.draw(c);
        }

        if (this.activeOverlay != null) {
            this.activeOverlay.update(this.activeScene);
            this.activeOverlay.draw(c);
        }

        c.drawText("" +(System.currentTimeMillis() - beginFrameMs), 10, 30, Resources.FONT);


        updateSheduled();
        invalidate();
    }
}
