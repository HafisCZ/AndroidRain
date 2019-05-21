package eu.mar21.rain.core.ui;

import android.graphics.Canvas;

public class List extends View {

    // Params
    private int selector = -1;

    // Constructor
    public List(float x, float y, float w, float h) {
        super(x, y, w, h);
    }

    public List(View parent, float x, float y, float w, float h) {
        this(x, y, w, h);

        parent.add(this);
    }

    // Methods
    public Panel add() {
        Panel p = new Panel(this, 0, 0, 1, 1);
        this.selector = 0;

        return p;
    }

    public void select(int id) {
        this.selector = id < this.views.size() ? id : -1;
    }

    @Override
    protected void show(Canvas c, float px, float py, float pw, float ph) {
        float rx = px + pw * this.x;
        float ry = py + ph * this.y;
        float rw = pw * this.w;
        float rh = ph * this.h;

        handle(rx, ry, rw, rh);

        if (this.selector != -1) {
            this.views.get(this.selector).show(c, rx, ry, rw, rh);
        }
    }

    public void next() {
        if (this.views.size() > 0) {
            this.selector++;
            if (this.selector >= this.views.size()) {
                this.selector = 0;
            }
        }
    }

    public void prev() {
        if (this.views.size() > 0) {
            this.selector--;
            if (this.selector < 0) {
                this.selector = this.views.size() - 1;
            }
        }
    }

    @Override
    public void draw(Canvas c, float x, float y, float w, float h) {

    }

}
