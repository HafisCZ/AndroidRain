package eu.mar21.rain.core.scene;

import android.graphics.Canvas;

import eu.mar21.rain.core.graphics.Renderer;
import eu.mar21.rain.core.level.Level;
import eu.mar21.rain.core.scene.menu.Menu;
import eu.mar21.rain.core.utils.Input;

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

        if (this.level.isExiting()) {
            this.level.reset();
            this.renderer.requestScene(Menu.class);
        }
    }

    public void draw(Canvas c) {
        this.level.draw(c);
    }

    @Override
    public Input getDedicatedListener() {
        return this.input;
    }

    public void begin() {
        this.level.reset();
    }

    public void end() {

    }

    public void init() {

    }

}
