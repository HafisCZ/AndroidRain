package eu.mar21.rain.core.scene.menu;

import android.graphics.Canvas;

import eu.mar21.rain.core.Application;
import eu.mar21.rain.core.graphics.Window;
import eu.mar21.rain.core.scene.Game;
import eu.mar21.rain.core.scene.Scene;
import eu.mar21.rain.core.ui.Panel;
import eu.mar21.rain.core.ui.Text;
import eu.mar21.rain.core.utils.Resources;
import eu.mar21.rain.core.utils.input.InputListener;

public class Menu extends Scene {

    // Constructor
    public Menu(Window w) {
        super(w);

        this.listener = new InputListener();
    }

    // Methods
    @Override
    public void draw(Canvas c) {
        for (int i = 0; i < Resources.BACKGROUND.length; i++) {
            c.drawBitmap(Resources.BACKGROUND[i], -50, 0, null);
        }

        this.view.show(c);
    }

    @Override
    public void init() {
        this.view = new Panel(0, 0, 1, 1).setBackground(Resources.PAINT_0);

        Panel panels[] = new Panel[5];
        for (int i = 0; i < 5; i++) {
            panels[i] = new Panel(0.1f, 0.1f + 0.17f * i, 0.4f, 0.15f).setBackground(Resources.PAINT_0F8FBC8F);
            this.view.add(panels[i]);
        }

        panels[0].add(new Text("PLAY").setForeground(Resources.PAINT_M_W_0050_L).setPosition(0.1f, 0.7f));
        panels[1].add(new Text("SHOP").setForeground(Resources.PAINT_M_W_0050_L).setPosition(0.1f, 0.7f));
        panels[2].add(new Text("STATISTICS").setForeground(Resources.PAINT_M_W_0050_L).setPosition(0.1f, 0.7f));
        panels[3].add(new Text("OPTIONS").setForeground(Resources.PAINT_M_W_0050_L).setPosition(0.1f, 0.7f));
        panels[4].add(new Text("EXIT").setForeground(Resources.PAINT_M_W_0050_L).setPosition(0.1f, 0.7f));

        panels[0].onClick(v -> window.requestScene(Game.class));
        panels[1].onClick(v -> window.requestScene(Shop.class));
        panels[2].onClick(v -> window.requestScene(Stat.class));
        panels[3].onClick(v -> window.requestScene(Settings.class));
        panels[4].onClick(v -> Application.get().finish());

        this.view.setListener(this.listener);
    }

    @Override
    public void begin() {
        this.listener.reset();
    }

}
