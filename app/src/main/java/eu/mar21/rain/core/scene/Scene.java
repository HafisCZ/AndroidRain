package eu.mar21.rain.core.scene;

import android.view.GestureDetector;

import eu.mar21.rain.core.graphics.Drawable;
import eu.mar21.rain.core.graphics.Renderer;
import eu.mar21.rain.core.utils.InputListener;

public abstract class Scene extends GestureDetector.SimpleOnGestureListener implements Drawable {

    protected Renderer renderer;

    public Scene(Renderer r) {
        this.renderer = r;
    }

    public abstract void update(Scene s);

    public abstract void init();
    public abstract void begin();
    public abstract void end();

    public InputListener getDedicatedListener() {
        return null;
    }

}
