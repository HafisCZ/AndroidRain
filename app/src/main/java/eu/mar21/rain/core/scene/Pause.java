package eu.mar21.rain.core.scene;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import eu.mar21.rain.core.graphics.Renderer;

public class Pause extends Scene {

    public static Paint GREEN, BLACK;
    static {
        GREEN = new Paint();
        GREEN.setARGB(255, 51, 102, 0);

        BLACK = new Paint();
        BLACK.setColor(Color.BLACK);
        BLACK.setStyle(Paint.Style.FILL);
        BLACK.setTextAlign(Paint.Align.CENTER);
        BLACK.setTextSize(60);
    }

    public Pause(Renderer r) {
        super(r);
    }

    @Override
    public void draw(Canvas c) {
        c.drawRoundRect(5, c.getHeight() / 2 - 40, c.getWidth() - 5, c.getHeight() / 2 + 40, 10, 10, GREEN);
        c.drawText("PAUSED", c.getWidth() / 2, c.getHeight() / 2 + 25, BLACK);
    }

    @Override
    public void update(Scene s) {

    }

    @Override
    public void onLongPress(MotionEvent e) {
        final Game game = (Game) renderer.getActiveScene();
        game.reset();
        renderer.setActiveOverlay(null);
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        renderer.setActiveOverlay(null);
        return true;
    }

    @Override
    public void end() {

    }

    @Override
    public void begin() {

    }

}
