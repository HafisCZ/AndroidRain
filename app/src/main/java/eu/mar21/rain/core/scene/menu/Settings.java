package eu.mar21.rain.core.scene.menu;

import android.graphics.Canvas;

import eu.mar21.rain.core.device.input.InputListener;
import eu.mar21.rain.core.device.input.InputStyle;
import eu.mar21.rain.core.graphics.Window;
import eu.mar21.rain.core.level.data.Award;
import eu.mar21.rain.core.level.data.Data;
import eu.mar21.rain.core.level.data.Upgrade;
import eu.mar21.rain.core.scene.Scene;
import eu.mar21.rain.core.ui.Button;
import eu.mar21.rain.core.ui.Panel;
import eu.mar21.rain.core.ui.Text;
import eu.mar21.rain.core.utils.Resources;

public class Settings extends Scene {

    // Params
    private Button[] buttons= new Button[4];

    // Constructor
    public Settings(Window w) {
        super(w);
    }

    // Methods
    @Override
    public void init() {
        this.view = new Panel(0, 0, 1, 1).setBackground(Resources.PAINT_0);

        Panel title = (Panel) new Panel(0, 0, 1, 0.1f).setBackground(Resources.PAINT_0F8FBC8F).onClick(v -> window.request(Menu.class));
        title.add(new Text("OPTIONS").setPosition(0.5f, 0.8f).setForeground(Resources.PAINT_M_W_0050_C));
        title.add(new Text("<<").setPosition(0.01f, 0.8f).setForeground(Resources.PAINT_M_LGRAY_0050_L));
        this.view.add(title);

        Panel sub0 = new Panel(0.1f, 0.15f, 0.8f, 0.3f).setBackground(Resources.PAINT_0);
        sub0.add(new Text("CONTROL SCHEME").setPosition(0.05f, 0.2f).setForeground(Resources.PAINT_M_W_0030_L));
        this.view.add(sub0);

        buttons[0] = (Button) new Button(sub0, "TAP", 0 * 1.0f / 3.0f + 0.05f, 0.5f, 1.0f / 3.0f - 0.05f, 0.5f).setBackground(Resources.PAINT_0F8FBC8F).setForeground(Resources.PAINT_M_W_0050_C).onClick(v -> {
            InputListener.setDirectionSource(InputStyle.TAP);
            begin();
        });

        buttons[1] = (Button) new Button(sub0, "SWIPE", 1 * 1.0f / 3.0f + 0.05f, 0.5f, 1.0f / 3.0f - 0.05f, 0.5f).setBackground(Resources.PAINT_0F8FBC8F).setForeground(Resources.PAINT_M_W_0050_C).onClick(v -> {
            InputListener.setDirectionSource(InputStyle.SWIPE);
            begin();
        });

        buttons[2] = (Button) new Button(sub0, "SENSOR", 2 * 1.0f / 3.0f + 0.05f, 0.5f, 1.0f / 3.0f - 0.05f, 0.5f).setBackground(Resources.PAINT_0F8FBC8F).setForeground(Resources.PAINT_M_W_0050_C).onClick(v -> {
            InputListener.setDirectionSource(InputStyle.SENSOR);
            begin();
        });

        Panel sub1 = new Panel(0.1f, 0.6f, 0.8f, 0.3f).setBackground(Resources.PAINT_0);
        sub1.add(new Text("DEBUG").setPosition(0.05f, 0.2f).setForeground(Resources.PAINT_M_W_0030_L));
        this.view.add(sub1);

        buttons[3] = (Button) new Button(sub1, "SHOW LOG", 0.05f, 0.5f, 1.0f / 3.0f - 0.05f, 0.5f).setBackground(Resources.PAINT_2FFFBC8F).setForeground(Resources.PAINT_M_W_0030_C).onClick(v -> {
            Data.DEBUG.set(Data.DEBUG.get() == 1 ? 0 : 1);
            Data.save();
            begin();
        });

        new Button(sub1, "AWARDS", 0.05f + 1.0f / 3.0f, 0.5f, 0.5f / 3.0f - 0.05f, 0.5f).setBackground(Resources.PAINT_2FFFBC8F).setForeground(Resources.PAINT_M_W_0030_C).onClick(v -> {
            Award.reset();
            Award.save();
        });

        new Button(sub1, "SHOP", 0.05f + 1.5f / 3.0f, 0.5f, 0.5f / 3.0f - 0.05f, 0.5f).setBackground(Resources.PAINT_2FFFBC8F).setForeground(Resources.PAINT_M_W_0030_C).onClick(v -> {
            Upgrade.reset();
            Upgrade.save();
        });

        new Button(sub1, "CLEAR DATA", 0.05f + 2.0f / 3.0f, 0.5f, 1.0f / 3.0f - 0.05f, 0.5f).setBackground(Resources.PAINT_2FFFBC8F).setForeground(Resources.PAINT_M_W_0030_C).onClick(v -> {
            Data.reset();
            Data.save();
            Upgrade.reset();
            Upgrade.save();
            Award.reset();
            Award.save();
        });

        this.view.setListener(this.window.getListener());
    }

    @Override
    public void begin() {
        this.buttons[0].setBackground(InputListener.getDirectionSource() == InputStyle.TAP ? Resources.PAINT_2F8FBC8F : Resources.PAINT_0F8FBC8F);
        this.buttons[1].setBackground(InputListener.getDirectionSource() == InputStyle.SWIPE ? Resources.PAINT_2F8FBC8F : Resources.PAINT_0F8FBC8F);
        this.buttons[2].setBackground(InputListener.getDirectionSource() == InputStyle.SENSOR ? Resources.PAINT_2F8FBC8F : Resources.PAINT_0F8FBC8F);
        this.buttons[3].setBackground(Data.DEBUG.get() == 1 ? Resources.PAINT_2F8FBC8F : Resources.PAINT_2FFFBC8F);
    }

    @Override
    public void draw(Canvas c) {
        for (int i = 0; i < Resources.BACKGROUND.length; i++) {
            c.drawBitmap(Resources.BACKGROUND[i], - 50, 0, null);
        }

        this.view.show(c);
    }
}
