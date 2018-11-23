package eu.mar21.rain.core.scene.menu;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import eu.mar21.rain.core.graphics.Renderer;
import eu.mar21.rain.core.scene.Game;
import eu.mar21.rain.core.scene.Scene;
import eu.mar21.rain.core.ui.Panel;
import eu.mar21.rain.core.ui.Text;
import eu.mar21.rain.core.ui.View;
import eu.mar21.rain.core.utils.input.InputListener;
import eu.mar21.rain.core.utils.Resources;

public class Menu extends Scene {

    private InputListener input;
    private View view;

    public Menu(Renderer r) {
        super(r);

        this.input = new InputListener();
    }

    @Override
    public void draw(Canvas c) {
        for (int i = 0; i < Resources.BACKGROUND.length; i++) {
            c.drawBitmap(Resources.BACKGROUND[i], - 50, 0, null);
        }

        this.view.draw(c);
    }

    @Override
    public void init() {
        this.view = new Panel(0, 0, 1, 1);

        Panel panels[] = new Panel[5];
        for (int i = 0; i < 5; i++) {
            panels[i] = new Panel(0.1f, 0.1f + 0.17f * i, 0.4f, 0.15f).setBackground(0x0F8FBC8F);
            this.view.addChild(panels[i]);
        }

        panels[0].addChild(new Text("PLAY").setFont(Typeface.MONOSPACE, Color.WHITE, 0.05f, Paint.Align.LEFT).setPosition(0.1f, 0.7f));
        panels[1].addChild(new Text("SHOP").setFont(Typeface.MONOSPACE, Color.WHITE, 0.05f, Paint.Align.LEFT).setPosition(0.1f, 0.7f));
        panels[2].addChild(new Text("STATISTICS").setFont(Typeface.MONOSPACE, Color.WHITE, 0.05f, Paint.Align.LEFT).setPosition(0.1f, 0.7f));
        panels[3].addChild(new Text("OPTIONS").setFont(Typeface.MONOSPACE, Color.WHITE, 0.05f, Paint.Align.LEFT).setPosition(0.1f, 0.7f));
        panels[4].addChild(new Text("EXIT").setFont(Typeface.MONOSPACE, Color.WHITE, 0.05f, Paint.Align.LEFT).setPosition(0.1f, 0.7f));

        panels[0].onClick(v -> renderer.requestScene(Game.class));
        panels[1].onClick(v -> renderer.requestScene(Shop.class));
        panels[2].onClick(v -> renderer.requestScene(Stat.class));
        panels[3].onClick(v -> renderer.requestScene(Settings.class));
        panels[4].onClick(v -> renderer.getParentActivity().finish());

        this.view.setListener(this.input);
    }

    @Override
    public void begin() {
        this.input.reset();
    }

    @Override
    public InputListener getDedicatedListener() {
        return this.input;
    }

}
