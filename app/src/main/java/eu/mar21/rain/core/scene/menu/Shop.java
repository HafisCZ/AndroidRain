package eu.mar21.rain.core.scene.menu;

import android.graphics.Canvas;

import eu.mar21.rain.core.graphics.Window;
import eu.mar21.rain.core.level.data.Statistics;
import eu.mar21.rain.core.scene.Scene;
import eu.mar21.rain.core.ui.Button;
import eu.mar21.rain.core.ui.Image;
import eu.mar21.rain.core.ui.List;
import eu.mar21.rain.core.ui.Panel;
import eu.mar21.rain.core.ui.Text;
import eu.mar21.rain.core.utils.Resources;
import eu.mar21.rain.core.utils.input.InputListener;

public class Shop extends Scene {

    // Params
    private Panel title;
    private Panel[] panels = new Panel[2];
    private Panel[] skills = new Panel[4];
    private Button[] stars = new Button[4];

    // Constructor
    public Shop(Window w) {
        super(w);

        this.listener = new InputListener();
    }

    // Methods
    @Override
    public void init() {
        this.view = new Panel(0, 0, 1, 1).setBackground(Resources.PAINT_0);

        this.title = (Panel) new Panel(this.view, 0, 0, 1, 0.1f).setBackground(Resources.PAINT_0F8FBC8F).onClick(v -> window.requestScene(Menu.class));
        new Text(this.title, "SHOP").setPosition(0.5f, 0.8f).setForeground(Resources.PAINT_M_W_0050_C);
        new Text(this.title, "<<").setPosition(0.01f, 0.8f).setForeground(Resources.PAINT_M_LGRAY_0050_L);

        List list = new List(this.view, 0, 0.1f, 1, 0.9f);
        Panel listItem0 = list.add().setBackground(Resources.PAINT_0);
        Panel listItem1 = list.add().setBackground(Resources.PAINT_0);

        new Button(this.view, "NEXT", 0.85f, 0.9f, 0.15f, 0.1f).setBackground(Resources.PAINT_0F8FBC8F).setForeground(Resources.PAINT_M_W_0030_C).onClick(v -> list.next());
        new Button(this.view, "PREV", 0, 0.9f, 0.15f, 0.1f).setBackground(Resources.PAINT_0F8FBC8F).setForeground(Resources.PAINT_M_W_0030_C).onClick(v -> list.prev());

        new Text(listItem0, "GENERAL").setPosition(0.5f, 0.1f).setForeground(Resources.PAINT_M_W_0030_C);
        new Text(listItem1, "STAR UPGRADES").setPosition(0.5f, 0.1f).setForeground(Resources.PAINT_M_W_0030_C);

        String[] starsLabels = { "INSTANT XP", "SHIELD", "XP BOOST", "FREE POINT" };
        for (int i = 0; i < 4; i++) {
            this.stars[i] = (Button) new Button(listItem1, starsLabels[i], 0.05f + 0.25f * i, 0.2f, 0.15f, 0.15f).setBackground(Resources.PAINT_0).setForeground(Resources.PAINT_M_W_0030_C).onClick(v -> {
                if (Statistics.PLAYER_POINTS.get() > 0 && Statistics.UPGRADE_RANDOM.get() < 5) {
                    Statistics.PLAYER_POINTS.add(-1);
                    Statistics.UPGRADE_RANDOM.add(1);
                    Statistics.save();

                    begin();
                }
            });
        }

        panels[0] = new Panel(listItem0, 0.1f, 0.15f, 0.8f, 0.1f).setBackground(Resources.PAINT_0);
        panels[1] = new Panel(listItem0,0.1f, 0.30f, 0.8f, 0.1f).setBackground(Resources.PAINT_0);

        for (int i = 0; i < 4; i++) {
            skills[i] = new Panel(listItem0, 0.1f + 0.3f * (i % 3), 0.5f + (float) (i / 3) * 0.25f, 0.2f, 0.2f * listItem0.getScale()).setBackground(Resources.PAINT_0);
        }

        panels[0].onClick(v -> {
            if (Statistics.PLAYER_POINTS.get() > 0) {
                if (Statistics.PLAYER_HEALTH.get() < 10) {
                    Statistics.PLAYER_POINTS.add(-1);
                    Statistics.PLAYER_HEALTH.add(1);
                    Statistics.save();

                    begin();
                }
            }
        });

        panels[1].onClick(v -> {
            if (Statistics.PLAYER_POINTS.get() > 0) {
                if (Statistics.PLAYER_SHIELDS.get() < Statistics.PLAYER_HEALTH.get()) {
                    Statistics.PLAYER_POINTS.add(-1);
                    Statistics.PLAYER_SHIELDS.add(1);
                    Statistics.save();

                    begin();
                }
            }
        });

        skills[0].onClick(v -> {
            if (Statistics.PLAYER_POINTS.get() > 0) {
                if (Statistics.UPGRADE_SKILL_SHOCK.get() < 1) {
                    Statistics.PLAYER_POINTS.add(-1);
                    Statistics.UPGRADE_SKILL_SHOCK.add(1);
                    Statistics.save();

                    begin();
                }
            }
        });

        skills[1].onClick(v -> {
            if (Statistics.PLAYER_POINTS.get() > 0) {
                if (Statistics.UPGRADE_SKILL_SHIELD.get() < 1) {
                    Statistics.PLAYER_POINTS.add(-1);
                    Statistics.UPGRADE_SKILL_SHIELD.add(1);
                    Statistics.save();

                    begin();
                }
            }
        });

        skills[2].onClick(v -> {
            if (Statistics.PLAYER_POINTS.get() > 0) {
                if (Statistics.UPGRADE_SKILL_XPBOOST.get() < 1) {
                    Statistics.PLAYER_POINTS.add(-1);
                    Statistics.UPGRADE_SKILL_XPBOOST.add(1);
                    Statistics.save();

                    begin();
                }
            }
        });

        skills[3].onClick(v -> {
            if (Statistics.PLAYER_POINTS.get() > 0) {
                if (Statistics.UPGRADE_ENERGY_MULT.get() < 1) {
                    Statistics.PLAYER_POINTS.add(-1);
                    Statistics.UPGRADE_ENERGY_MULT.add(1);
                    Statistics.save();

                    begin();
                }
            }
        });

        new Text(panels[0], "HEALTH").setPosition(0.1f, 0.8f).setForeground(Resources.PAINT_M_W_0030_C);
        new Text(panels[1], "SHIELD").setPosition(0.1f, 0.8f).setForeground(Resources.PAINT_M_W_0030_C);

        new Text(skills[0], "SHOCK").setPosition(0.5f, 0.2f).setForeground(Resources.PAINT_M_W_0030_C);
        new Text(skills[1], "BONUS SHIELD").setPosition(0.5f, 0.2f).setForeground(Resources.PAINT_M_W_0030_C);
        new Text(skills[2], "XP BOOST").setPosition(0.5f, 0.2f).setForeground(Resources.PAINT_M_W_0030_C);
        new Text(skills[3], "DOUBLE POWER").setPosition(0.5f, 0.2f).setForeground(Resources.PAINT_M_W_0030_C);

        new Image(skills[0], Resources.SKILLS[0]).setPosition(0.5f, 0.7f).center(true);
        new Image(skills[1], Resources.SKILLS[1]).setPosition(0.5f, 0.7f).center(true);
        new Image(skills[2], Resources.SKILLS[2]).setPosition(0.5f, 0.7f).center(true);

        this.view.setListener(this.listener);
    }

    @Override
    public void begin() {
        this.listener.reset();

        this.title.get().subList(2, this.title.get().size()).clear();
        new Text(this.title, Integer.toString(Statistics.PLAYER_POINTS.get())).setPosition(0.95f, 0.8f).setForeground(Resources.PAINT_M_Y_0050_R);

        for (Panel p : this.panels) {
            p.get().subList(1, p.get().size()).clear();
        }

        ((Text) this.panels[0].get().get(0)).setForeground(Statistics.PLAYER_HEALTH.get() < 10 && Statistics.PLAYER_POINTS.get() > 0 ? Resources.PAINT_M_Y_0030_C : (Statistics.PLAYER_HEALTH.get() == 10 ? Resources.PAINT_M_GRAY_0030_C : Resources.PAINT_M_W_0030_C));
        ((Text) this.panels[1].get().get(0)).setForeground(Statistics.PLAYER_SHIELDS.get() < 10 && Statistics.PLAYER_POINTS.get() > 0 ? Resources.PAINT_M_Y_0030_C : (Statistics.PLAYER_SHIELDS.get() == 10 ? Resources.PAINT_M_GRAY_0030_C : Resources.PAINT_M_W_0030_C));
        ((Text) this.skills[0].get().get(0)).setForeground(Statistics.UPGRADE_SKILL_SHOCK.get() < 1 && Statistics.PLAYER_POINTS.get() > 0 ? Resources.PAINT_M_Y_0030_C : (Statistics.UPGRADE_SKILL_SHOCK.get() == 1 ? Resources.PAINT_M_GRAY_0030_C : Resources.PAINT_M_W_0030_C));
        ((Text) this.skills[1].get().get(0)).setForeground(Statistics.UPGRADE_SKILL_SHIELD.get() < 1 && Statistics.PLAYER_POINTS.get() > 0 ? Resources.PAINT_M_Y_0030_C : (Statistics.UPGRADE_SKILL_SHIELD.get() == 1 ? Resources.PAINT_M_GRAY_0030_C : Resources.PAINT_M_W_0030_C));
        ((Text) this.skills[2].get().get(0)).setForeground(Statistics.UPGRADE_SKILL_XPBOOST.get() < 1 && Statistics.PLAYER_POINTS.get() > 0 ? Resources.PAINT_M_Y_0030_C : (Statistics.UPGRADE_SKILL_XPBOOST.get() == 1 ? Resources.PAINT_M_GRAY_0030_C : Resources.PAINT_M_W_0030_C));
        ((Text) this.skills[3].get().get(0)).setForeground(Statistics.UPGRADE_ENERGY_MULT.get() < 1 && Statistics.PLAYER_POINTS.get() > 0 ? Resources.PAINT_M_Y_0030_C : (Statistics.UPGRADE_ENERGY_MULT.get() == 1 ? Resources.PAINT_M_GRAY_0030_C : Resources.PAINT_M_W_0030_C));

        for (int i = 0; i < 4; i++) {
            this.stars[i].setForeground(Statistics.UPGRADE_RANDOM.get() >= i + 2 ? Resources.PAINT_M_GRAY_0030_C : (Statistics.UPGRADE_RANDOM.get() >= i + 1 && Statistics.PLAYER_POINTS.get() > 0 ? Resources.PAINT_M_Y_0030_C : Resources.PAINT_M_W_0030_C));
        }

        for (int i = 0; i < Statistics.PLAYER_HEALTH.get(); i++) {
            new Image(this.panels[0], Resources.ICONS[0]).setPosition(0.25f + 0.075f * i, 0.5f).center(true);
        }

        for (int i = 0; i < Statistics.PLAYER_SHIELDS.get(); i++) {
            new Image(this.panels[1], Resources.SHIELD).setPosition(0.25f + 0.075f * i, 0.5f).center(true);
        }
    }

    @Override
    public void draw(Canvas c) {
        for (int i = 0; i < Resources.BACKGROUND.length; i++) {
            c.drawBitmap(Resources.BACKGROUND[i], - 50, 0, null);
        }

        this.view.show(c);
    }

}
