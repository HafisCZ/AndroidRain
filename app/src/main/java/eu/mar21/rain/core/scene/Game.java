package eu.mar21.rain.core.scene;

import android.graphics.Canvas;

import eu.mar21.rain.core.Level.Level;
import eu.mar21.rain.core.graphics.Renderer;
import eu.mar21.rain.core.utils.Resources;

public class Game extends Scene {

    private Level level;

    public Game(Renderer r) {
        super(r);

        this.level = new Level();
    }

    public void update(Scene s) {
        this.level.tick();
    }

    public void draw(Canvas c) {
        for (int i = 0; i < 8; i++) {
            c.drawBitmap(Resources.BACKGROUND[i], 0,0, null);
        }

        this.level.draw(c);
    }

    public void begin() {

    }

    public void end() {

    }

}
