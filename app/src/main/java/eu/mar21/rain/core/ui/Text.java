package eu.mar21.rain.core.ui;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Text extends View {

    // Params
    private String text;

    // Constructor
    public Text(String text) {
        super(0, 0, 0, 0);

        this.text = text;
    }

    public Text(View parent, String text) {
        this(text);

        parent.add(this);
    }

    // Methods
    public Text setForeground(Paint paint) {
        this.fg = paint;
        return this;
    }

    public Text setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }

    @Override
    public void draw(Canvas c, float x, float y, float w, float h) {
        c.drawText(this.text, x, y, this.fg);
    }

}