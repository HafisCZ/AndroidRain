package eu.mar21.rain.core.ui;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Panel extends View {

    // Constructor
    public Panel(float x, float y, float w, float h) {
        super(x, y, w, h);
    }

    public Panel(View parent, float x, float y, float w, float h) {
        this(x, y, w, h);

        parent.add(this);
    }

    // Methods
    public Panel setBackground(Paint paint) {
        this.bg = paint;
        return this;
    }

    @Override
    public void draw(Canvas c, float x, float y, float w, float h) {
        c.drawRect(x, y, x + w, y + h, this.bg);
    }

}
