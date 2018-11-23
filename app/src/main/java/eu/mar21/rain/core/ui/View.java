package eu.mar21.rain.core.ui;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.List;

import eu.mar21.rain.core.utils.Resources;
import eu.mar21.rain.core.utils.input.InputListener;

public abstract class View {

    protected float x = 0.0f;
    protected float y = 0.0f;
    protected float w = 0.0f;
    protected float h = 0.0f;

    private List<View> children = new ArrayList<>();

    private Action action;
    private boolean actionHandled = false;
    private InputListener listener;

    public void addChild(View view) {
        this.children.add(view);
    }

    public void removeChild(View view) {
        this.children.remove(view);
    }

    public float getScale() {
        return this.h / this.w;
    }

    public List<View> getChildren() {
        return this.children;
    }

    public void setListener(InputListener listener) {
        this.listener = listener;
        for (View v : this.children) {
            v.setListener(listener);
        }
    }

    public View onClick(Action action) {
        this.action = action;
        return this;
    }

    private void handleListener(float x, float y, float w, float h) {
        if (this.listener != null && this.action != null) {
            boolean press = this.listener.isPressed((int) x, (int) y, (int) (x + w), (int) (y + h));
            if (press && !this.actionHandled) {
                this.action.apply(this);
            }

            this.actionHandled = press;
        }
    }

    public void draw(Canvas c) {
        draw(c, 0, 0, (float) Resources.SCREEN_WIDTH, (float) Resources.SCREEN_HEIGHT);
    }

    private void draw(Canvas c, float px, float py, float pw, float ph) {
        float rx = px + pw * this.x;
        float ry = py + ph * this.y;
        float rw = pw * this.w;
        float rh = ph * this.h;

        handleListener(rx, ry, rw, rh);
        drawView(c, rx, ry, rw, rh);
        for (View v : this.children) {
            v.draw(c, rx, ry, rw, rh);
        }
    }

    public abstract void drawView(Canvas c, float x, float y, float w, float h);

}

