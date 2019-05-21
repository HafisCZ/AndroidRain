package eu.mar21.rain.core.scene;

import eu.mar21.rain.core.graphics.Drawable;
import eu.mar21.rain.core.graphics.Window;
import eu.mar21.rain.core.ui.View;
import eu.mar21.rain.core.utils.input.InputListener;

public abstract class Scene implements Drawable {

    // Params
    protected View view;
    protected Window window;
    protected InputListener listener;

    // Constructor
    public Scene(Window r) {
        this.window = r;
    }

    // Methods
    public void update(Scene s) {}
    public void init() {}
    public void begin() {}
    public void end() {}

    public InputListener getListener() {
        return this.listener;
    }

    public View getView() {
        return this.view;
    }

}
