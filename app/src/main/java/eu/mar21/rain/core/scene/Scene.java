package eu.mar21.rain.core.scene;

import android.view.GestureDetector;

import eu.mar21.rain.core.graphics.Drawable;
import eu.mar21.rain.core.graphics.Renderer;
import eu.mar21.rain.core.utils.input.InputListener;

public abstract class Scene extends GestureDetector.SimpleOnGestureListener implements Drawable {

    protected Renderer renderer;

    public Scene(Renderer r) {
        this.renderer = r;
    }

    public void update(Scene s) {

    }

    public void init() {

    }

    public void begin() {

    }

    public void end() {

    }

    public InputListener getDedicatedListener() {
        return null;
    }

}
