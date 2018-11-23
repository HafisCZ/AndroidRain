package eu.mar21.rain.core.ui;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;

import eu.mar21.rain.core.utils.Resources;

public class Text extends View {

    private String text;
    private Paint font = new Paint();

    public Text(String text) {
        this.text = text;
    }

    public Text setFont(Typeface typeface, int color, float size, Paint.Align align) {
        this.font.setTypeface(typeface);
        this.font.setTextSize(size * (float) Resources.SCREEN_WIDTH);
        this.font.setTextAlign(align);
        this.font.setColor(color);
        return this;
    }

    public Text setColor(int color) {
        this.font.setColor(color);
        return this;
    }

    public Text setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }

    @Override
    public void drawView(Canvas c, float x, float y, float w, float h) {
        c.drawText(this.text, x, y, this.font);
    }

}