package eu.mar21.rain.core.ui;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;

import eu.mar21.rain.core.utils.Resources;
import eu.mar21.rain.core.utils.input.InputListener;

public abstract class View {

    // Params
    protected float x;
    protected float y;
    protected float w;
    protected float h;

    protected Paint fg;
    protected Paint bg;

    protected List<View> views;

    private Action action;
    private boolean handled;
    private InputListener listener;

    // Constructor
    protected View(float x, float y, float w, float h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;

        this.views = new ArrayList<>();
        this.handled = false;
        this.listener = null;
        this.action = null;

        this.fg = Resources.PAINT;
        this.bg = Resources.PAINT;
    }

    // Methods
    public void add(View v) {
        this.views.add(v);
    }

    public void remove(View v) {
        this.views.remove(v);
    }

    public List<View> get() {
        return this.views;
    }

    public float getScale() {
        return this.h / this.w;
    }

    public void setListener(InputListener l) {
        this.listener = l;

        for (View v : this.views) {
            v.setListener(l);
        }
    }

    public View onClick(Action action) {
        this.action = action;

        return this;
    }

    protected void handle(float rx, float ry, float rw, float rh) {
        if (this.listener != null && this.action != null) {
            boolean press = this.listener.isPressed((int) rx, (int) ry, (int) (rx + rw), (int) (ry + rh));
            if (press && !this.handled) {
                this.action.apply(this);
            }

            this.handled = press;
        }
    }

    public void show(Canvas c) {
        show(c, 0, 0, (float) Resources.SCREEN_WIDTH, (float) Resources.SCREEN_HEIGHT);
    }

    protected void show(Canvas c, float fx, float fy, float fw, float fh) {
        float rx = fx + fw * this.x;
        float ry = fy + fh * this.y;
        float rw = fw * this.w;
        float rh = fh * this.h;

        handle(rx, ry, rw, rh);
        draw(c, rx, ry, rw, rh);

        for (View v : this.views) {
            v.show(c, rx, ry, rw, rh);
        }
    }

    public abstract void draw(Canvas c, float x, float y, float w, float h);

}

