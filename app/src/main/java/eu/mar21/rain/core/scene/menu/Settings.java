package eu.mar21.rain.core.scene.menu;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import eu.mar21.rain.core.graphics.Renderer;
import eu.mar21.rain.core.level.data.Statistics;
import eu.mar21.rain.core.scene.Scene;
import eu.mar21.rain.core.ui.Button;
import eu.mar21.rain.core.ui.Panel;
import eu.mar21.rain.core.ui.Text;
import eu.mar21.rain.core.ui.View;
import eu.mar21.rain.core.utils.Resources;
import eu.mar21.rain.core.utils.input.InputListener;

public class Settings extends Scene {

    private InputListener input;

    private View view;
    private Button buttons[] = new Button[3];

    public Settings(Renderer r) {
        super(r);

        this.input = new InputListener();
    }

    @Override
    public void init() {
        this.view = new Panel(0, 0, 1, 1);

        /*
            Header & back button
        */
        Panel title = (Panel) new Panel(0, 0, 1, 0.1f).setBackground(0x0F8FBC8F).onClick(v -> renderer.requestScene(Menu.class));
        title.addChild(new Text("OPTIONS").setPosition(0.5f, 0.8f).setFont(Typeface.MONOSPACE, Color.WHITE,0.05f, Paint.Align.CENTER));
        title.addChild(new Text("<<").setPosition(0.01f, 0.8f).setFont(Typeface.MONOSPACE, Color.LTGRAY, 0.05f, Paint.Align.LEFT));
        this.view.addChild(title);

        Panel sub0 = new Panel(0.1f, 0.15f, 0.8f, 0.3f);
        sub0.addChild(new Text("CONTROL SCHEME").setPosition(0.05f, 0.2f).setFont(Typeface.MONOSPACE, Color.WHITE, 0.03f, Paint.Align.LEFT));
        this.view.addChild(sub0);

        buttons[0] = (Button) new Button(sub0, "TAP", 0 * 1.0f / 3.0f + 0.05f, 0.5f, 1.0f / 3.0f - 0.05f, 0.5f).setBackground(0x0F8FBC8F).setFont(Typeface.MONOSPACE, Color.WHITE, 0.05f).onClick(v -> {
            InputListener.setMode(InputListener.TouchMode.TAP);
            begin();
        });

        buttons[1] = (Button) new Button(sub0, "SWIPE", 1 * 1.0f / 3.0f + 0.05f, 0.5f, 1.0f / 3.0f - 0.05f, 0.5f).setBackground(0x0F8FBC8F).setFont(Typeface.MONOSPACE, Color.WHITE, 0.05f).onClick(v -> {
            InputListener.setMode(InputListener.TouchMode.SWIPE);
            begin();
        });

        buttons[2] = (Button) new Button(sub0, "SENSOR", 2 * 1.0f / 3.0f + 0.05f, 0.5f, 1.0f / 3.0f - 0.05f, 0.5f).setBackground(0x0F8FBC8F).setFont(Typeface.MONOSPACE, Color.WHITE, 0.05f).onClick(v -> {
            InputListener.setMode(InputListener.TouchMode.SENSOR);
            begin();
        });

        Panel sub1 = new Panel(0.1f, 0.6f, 0.8f, 0.3f);
        sub1.addChild(new Text("STATISTICS").setPosition(0.05f, 0.2f).setFont(Typeface.MONOSPACE, Color.WHITE, 0.03f, Paint.Align.LEFT));
        this.view.addChild(sub1);

        Panel gsub1 = new Panel(0.05f, 0.5f, 1.0f / 3.0f - 0.05f, 0.5f).setBackground(0x2FFFBC8F);
        sub1.addChild(gsub1);

        gsub1.addChild(new Text("CLEAR").setPosition(0.5f, 0.8f).setFont(Typeface.MONOSPACE, Color.WHITE, 0.05f, Paint.Align.CENTER));

        gsub1.onClick(v -> {
            Statistics.clear();
            Statistics.save();
            begin();
        });

        this.view.setListener(this.input);
    }

    @Override
    public void begin() {
        this.input.reset();

        this.buttons[0].setBackground(InputListener.getMode() == InputListener.TouchMode.TAP ? 0x2F8FFC8F : 0x0F8FBC8F);
        this.buttons[1].setBackground(InputListener.getMode() == InputListener.TouchMode.SWIPE ? 0x2F8FFC8F : 0x0F8FBC8F);
        this.buttons[2].setBackground(InputListener.getMode() == InputListener.TouchMode.SENSOR ? 0x2F8FFC8F : 0x0F8FBC8F);
    }

    @Override
    public InputListener getDedicatedListener() {
        return this.input;
    }

    @Override
    public void draw(Canvas c) {
        for (int i = 0; i < Resources.BACKGROUND.length; i++) {
            c.drawBitmap(Resources.BACKGROUND[i], - 50, 0, null);
        }

        this.view.draw(c);
    }
}
