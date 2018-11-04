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

import eu.mar21.rain.core.scene.EmptyScene;
import eu.mar21.rain.core.scene.Intro;
import eu.mar21.rain.core.scene.Scene;
import eu.mar21.rain.core.utils.Resources;

public class Renderer extends View {

    private Scene activeScene;
    private Scene activeOverlay;

    private Activity parent;
    private GestureDetector gestureDetector;

    private Map<Class<? extends Scene>, Object> scenes;

    private Object threadLock;

    private long lastFrameMs;
    {
        lastFrameMs = System.currentTimeMillis();
        scenes = new HashMap<>();

        activeOverlay = null;
        activeScene = null;

        threadLock = new Object();
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

        registerScene(EmptyScene.class);
        registerScene(Intro.class);

        setActiveScene(EmptyScene.class);
        setActiveOverlay(Intro.class);
    }

    private <T extends GestureDetector.SimpleOnGestureListener> void setGestureListener(T listener) {
        this.gestureDetector = new GestureDetector(this.parent, listener);
        setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent e) {
                gestureDetector.onTouchEvent(e);
                return true;
            }
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

    public void setActiveScene(Class<? extends Scene> scene) {
        synchronized (threadLock) {
            Log.i("AppSetScene", "" + scene);
            if (this.activeScene != null) {
                this.activeScene.end();
            }

            this.activeScene = (Scene) this.scenes.get(scene);
            this.activeScene.begin();

            if (this.activeOverlay == null) {
                setGestureListener(this.activeScene);
            }
        }
    }

    public void setActiveOverlay(Class<? extends Scene> overlay) {
        synchronized (threadLock) {
            Log.i("AppSetOverlay", "" + overlay);
            if (overlay == null) {
                this.activeOverlay.end();
                if (this.activeScene != null) {
                    this.activeScene.begin();
                    setGestureListener(this.activeScene);
                }

                this.activeOverlay = null;
            } else {
                this.activeOverlay = (Scene) this.scenes.get(overlay);
                this.activeOverlay.begin();
                setGestureListener(this.activeOverlay);

                if (this.activeScene != null) {
                    this.activeScene.end();
                }
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int ow, int oh) {
        super.onSizeChanged(w, h, ow, oh);
    }

    @Override
    protected void onDraw(Canvas c) {
        c.drawRGB(0, 0, 0);

        synchronized (threadLock) {
            if (this.activeScene != null) {
                this.activeScene.update(null);
                this.activeScene.draw(c);
            }

            if (this.activeOverlay != null) {
                this.activeOverlay.update(this.activeScene);
                this.activeOverlay.draw(c);
            }
        }

        final long thisFrameMs = System.currentTimeMillis();
        c.drawText("" + (thisFrameMs - lastFrameMs), 10, 30, Resources.FONT);

        lastFrameMs = thisFrameMs;

        invalidate();
    }
}
