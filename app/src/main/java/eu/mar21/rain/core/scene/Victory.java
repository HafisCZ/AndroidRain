package eu.mar21.rain.core.scene;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import eu.mar21.rain.core.graphics.Renderer;

public class Victory extends Scene {

    public static Paint YELLOW, BLACK;
    static {
        YELLOW = new Paint();
        YELLOW.setColor(Color.YELLOW);

        BLACK = new Paint();
        BLACK.setColor(Color.BLACK);
        BLACK.setStyle(Paint.Style.FILL);
        BLACK.setTextAlign(Paint.Align.CENTER);
        BLACK.setTextSize(60);
    }

    public Victory(Renderer r) {
        super(r);
    }

    @Override
    public void update(Scene s) {

    }

    @Override
    public void draw(Canvas c) {
        c.drawRoundRect(5, c.getHeight() / 2 - 40, c.getWidth() - 5, c.getHeight() / 2 + 40, 10, 10, YELLOW);
        c.drawText("VICTORY", c.getWidth() / 2, c.getHeight() / 2 + 25, BLACK);
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        renderer.setActiveOverlay(null);

        if (renderer.getActiveScene() != null && renderer.getActiveScene() instanceof Game) {
            ((Game) renderer.getActiveScene()).reset();
        }
        return true;
    }

    @Override
    public void end() {

    }

    @Override
    public void begin() {

    }

}
