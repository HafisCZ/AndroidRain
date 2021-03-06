package eu.mar21.rain.core.scene.menu;

import android.graphics.Canvas;

import eu.mar21.rain.core.Application;
import eu.mar21.rain.core.graphics.Window;
import eu.mar21.rain.core.scene.Game;
import eu.mar21.rain.core.scene.Scene;
import eu.mar21.rain.core.ui.Panel;
import eu.mar21.rain.core.ui.Text;
import eu.mar21.rain.core.utils.Resources;

public class Menu extends Scene {

    // Constructor
    public Menu(Window w) {
        super(w);
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

        Panel[] panels = new Panel[5];
        for (int i = 0; i < panels.length; i++) {
            panels[i] = new Panel(this.view, 0.1f, 0.1f + 0.17f * i, 0.4f, 0.15f).setBackground(Resources.PAINT_0F8FBC8F);
        }

        new Text(panels[0], "PLAY").setForeground(Resources.PAINT_M_W_0050_L).setPosition(0.1f, 0.7f);
        new Text(panels[1], "SHOP").setForeground(Resources.PAINT_M_W_0050_L).setPosition(0.1f, 0.7f);
        new Text(panels[2], "STATISTICS").setForeground(Resources.PAINT_M_W_0050_L).setPosition(0.1f, 0.7f);
        new Text(panels[3], "OPTIONS").setForeground(Resources.PAINT_M_W_0050_L).setPosition(0.1f, 0.7f);
        new Text(panels[4], "EXIT").setForeground(Resources.PAINT_M_W_0050_L).setPosition(0.1f, 0.7f);

        panels[0].onClick(v -> window.request(Game.class));
        panels[1].onClick(v -> window.request(Shop.class));
        panels[2].onClick(v -> window.request(Stat.class));
        panels[3].onClick(v -> window.request(Settings.class));
        panels[4].onClick(v -> Application.get().finish());

        this.view.setListener(this.window.getListener());
    }

}
