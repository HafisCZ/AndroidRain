package eu.mar21.rain.core.scene;

import android.graphics.Canvas;

import eu.mar21.rain.core.level.Level;
import eu.mar21.rain.core.graphics.Renderer;
import eu.mar21.rain.core.utils.Input;
import eu.mar21.rain.core.utils.Resources;

public class Game extends Scene {

    private Level level;
    private Input input;

    public Game(Renderer r) {
        super(r);

        this.input = new Input();
        this.level = new Level(input);
    }

    public void update(Scene s) {
        this.level.tick();
    }

    public void draw(Canvas c) {
        this.level.draw(c);
    }

    @Override
    public Input getDedicatedListener() {
        return this.input;
    }

    public void begin() {

    }

    public void end() {

    }

}
