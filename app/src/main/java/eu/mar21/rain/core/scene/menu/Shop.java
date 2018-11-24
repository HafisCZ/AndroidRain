package eu.mar21.rain.core.scene.menu;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import eu.mar21.rain.core.graphics.Renderer;
import eu.mar21.rain.core.level.data.Statistics;
import eu.mar21.rain.core.scene.Scene;
import eu.mar21.rain.core.ui.Button;
import eu.mar21.rain.core.ui.Image;
import eu.mar21.rain.core.ui.List;
import eu.mar21.rain.core.ui.Panel;
import eu.mar21.rain.core.ui.Text;
import eu.mar21.rain.core.ui.View;
import eu.mar21.rain.core.utils.Resources;
import eu.mar21.rain.core.utils.input.InputListener;

public class Shop extends Scene {

    private InputListener input = new InputListener();

    /*
        Content containers
    */
    private View view;
    private Panel title;
    private Panel panels[] = new Panel[2];
    private Panel skills[] = new Panel[5];
    private Button stars[] = new Button[4];

    public Shop(Renderer r) {
        super(r);
    }

    @Override
    public void init() {
        this.view = new Panel(0, 0, 1, 1);

        /*
            Header & back button
        */
        title = (Panel) new Panel(0, 0, 1, 0.1f).setBackground(0x0F8FBC8F).onClick(v -> renderer.requestScene(Menu.class));
        title.addChild(new Text("SHOP").setPosition(0.5f, 0.8f).setFont(Typeface.MONOSPACE, Color.WHITE,0.05f, Paint.Align.CENTER));
        title.addChild(new Text("<<").setPosition(0.01f, 0.8f).setFont(Typeface.MONOSPACE, Color.LTGRAY, 0.05f, Paint.Align.LEFT));
        this.view.addChild(title);

        /*
            Creating list view
        */
        List list = new List(this.view, 0, 0.1f, 1, 0.9f);
        Panel listItem0 = list.newItem();
        Panel listItem1 = list.newItem();

        /*
            Adding buttons to control the list view
        */
        new Button(this.view, "NEXT", 0.85f, 0.9f, 0.15f, 0.1f).setBackground(0x0F8FBC8F).setFont(Typeface.MONOSPACE, Color.WHITE, 0.03f).onClick(v -> list.next());
        new Button(this.view, "PREV", 0, 0.9f, 0.15f, 0.1f).setBackground(0x0F8FBC8F).setFont(Typeface.MONOSPACE, Color.WHITE, 0.03f).onClick(v -> list.prev());

        /*
            Adding content to seconds list item
        */

        new Text(listItem0, "GENERAL").setPosition(0.5f, 0.1f).setFont(Typeface.MONOSPACE, Color.WHITE,0.03f, Paint.Align.CENTER);
        new Text(listItem1, "STAR UPGRADES").setPosition(0.5f, 0.1f).setFont(Typeface.MONOSPACE, Color.WHITE,0.03f, Paint.Align.CENTER);

        String starsLabels[] = { "INSTANT XP", "SHIELD", "XP BOOST", "FREE POINT" };
        for (int i = 0; i < 4; i++) {
            this.stars[i] = (Button) new Button(listItem1, starsLabels[i], 0.05f + 0.25f * i, 0.2f, 0.15f, 0.15f).setBackground(0).setFont(Typeface.MONOSPACE, Color.WHITE, 0.03f).onClick(v -> {
                if (Statistics.PLAYER_SPENDABLE_POINTS.get() > 0 && Statistics.PLAYER_UPGRADE_STAR.get() < 5) {
                    Statistics.PLAYER_SPENDABLE_POINTS.sub();
                    Statistics.PLAYER_UPGRADE_STAR.add();
                    Statistics.save();

                    begin();
                }
            });
        }

        /*
            Individual content panels
        */
        panels[0] = new Panel(listItem0, 0.1f, 0.15f, 0.8f, 0.1f);
        panels[1] = new Panel(listItem0,0.1f, 0.30f, 0.8f, 0.1f);

        for (int i = 0; i < 3; i++) {
            skills[i] = new Panel(listItem0, 0.1f + 0.3f * i, 0.5f, 0.2f, 0.2f * listItem0.getScale());
        }

        for (int i = 0; i < 2; i++) {
            skills[i + 3] = new Panel(listItem0, 0.1f + 0.3f * i, 0.75f, 0.2f, 0.2f * listItem0.getScale());
        }

        /*
            Adding input handles to panels
        */
        panels[0].onClick(v -> {
            if (Statistics.PLAYER_SPENDABLE_POINTS.get() > 0) {
                if (Statistics.PLAYER_MAX_HEALTH.get() < 10) {
                    Statistics.PLAYER_SPENDABLE_POINTS.add(-1);
                    Statistics.PLAYER_MAX_HEALTH.add(1);
                    Statistics.save();

                    begin();
                }
            }
        });

        panels[1].onClick(v -> {
            if (Statistics.PLAYER_SPENDABLE_POINTS.get() > 0) {
                if (Statistics.PLAYER_DEF_SHIELD.get() < Statistics.PLAYER_MAX_HEALTH.get()) {
                    Statistics.PLAYER_SPENDABLE_POINTS.add(-1);
                    Statistics.PLAYER_DEF_SHIELD.add(1);
                    Statistics.save();

                    begin();
                }
            }
        });

        skills[0].onClick(v -> {
            if (Statistics.PLAYER_SPENDABLE_POINTS.get() > 0) {
                if (Statistics.PLAYER_SKILL_SHOCKWAVE.get() < 1) {
                    Statistics.PLAYER_SPENDABLE_POINTS.add(-1);
                    Statistics.PLAYER_SKILL_SHOCKWAVE.add(1);
                    Statistics.save();

                    begin();
                }
            }
        });

        skills[1].onClick(v -> {
            if (Statistics.PLAYER_SPENDABLE_POINTS.get() > 0) {
                if (Statistics.PLAYER_SKILL_SHIELD.get() < 1) {
                    Statistics.PLAYER_SPENDABLE_POINTS.add(-1);
                    Statistics.PLAYER_SKILL_SHIELD.add(1);
                    Statistics.save();

                    begin();
                }
            }
        });

        skills[2].onClick(v -> {
            if (Statistics.PLAYER_SPENDABLE_POINTS.get() > 0) {
                if (Statistics.PLAYER_SKILL_DOUBLE_EXP.get() < 1) {
                    Statistics.PLAYER_SPENDABLE_POINTS.add(-1);
                    Statistics.PLAYER_SKILL_DOUBLE_EXP.add(1);
                    Statistics.save();

                    begin();
                }
            }
        });

        skills[3].onClick(v -> {
            if (Statistics.PLAYER_SPENDABLE_POINTS.get() > 0) {
                if (Statistics.PLAYER_UPGRADE_DMG_SHOCKWAVE.get() < 1) {
                    Statistics.PLAYER_SPENDABLE_POINTS.add(-1);
                    Statistics.PLAYER_UPGRADE_DMG_SHOCKWAVE.add(1);
                    Statistics.save();

                    begin();
                }
            }
        });

        skills[4].onClick(v -> {
            if (Statistics.PLAYER_SPENDABLE_POINTS.get() > 0) {
                if (Statistics.PLAYER_UPGRADE_BETTER_NODES.get() < 1) {
                    Statistics.PLAYER_SPENDABLE_POINTS.add(-1);
                    Statistics.PLAYER_UPGRADE_BETTER_NODES.add(1);
                    Statistics.save();

                    begin();
                }
            }
        });

        /*
            Adding constants
        */
        panels[0].addChild(new Text("HEALTH").setPosition(0.1f, 0.8f).setFont(Typeface.MONOSPACE, Color.WHITE, 0.03f, Paint.Align.CENTER));
        panels[1].addChild(new Text("SHIELD").setPosition(0.1f, 0.8f).setFont(Typeface.MONOSPACE, Color.WHITE, 0.03f, Paint.Align.CENTER));

        skills[0].addChild(new Text("SHOCKWAVE").setPosition(0.5f, 0.2f).setFont(Typeface.MONOSPACE, Color.WHITE, 0.03f, Paint.Align.CENTER));
        skills[1].addChild(new Text("SHIELD SPAWN").setPosition(0.5f, 0.2f).setFont(Typeface.MONOSPACE, Color.WHITE, 0.03f, Paint.Align.CENTER));
        skills[2].addChild(new Text("XP BOOST").setPosition(0.5f, 0.2f).setFont(Typeface.MONOSPACE, Color.WHITE, 0.03f, Paint.Align.CENTER));
        skills[3].addChild(new Text("AUTO-WAVE").setPosition(0.5f, 0.2f).setFont(Typeface.MONOSPACE, Color.WHITE, 0.03f, Paint.Align.CENTER));
        skills[4].addChild(new Text("DOUBLE POWER").setPosition(0.5f, 0.2f).setFont(Typeface.MONOSPACE, Color.WHITE, 0.03f, Paint.Align.CENTER));

        skills[0].addChild(new Image(Resources.SKILLS[0]).setPosition(0.5f, 0.7f).center(true));
        skills[1].addChild(new Image(Resources.SKILLS[1]).setPosition(0.5f, 0.7f).center(true));
        skills[2].addChild(new Image(Resources.SKILLS[2]).setPosition(0.5f, 0.7f).center(true));

        /*
            Add input listener to all views contained within root view
        */
        this.view.setListener(this.input);
    }

    @Override
    public void begin() {
        this.input.reset();

        /*
            Show spendable points
        */
        this.title.getChildren().subList(2, this.title.getChildren().size()).clear();
        this.title.addChild(
            new Text(Integer.toString(Statistics.PLAYER_SPENDABLE_POINTS.get())).setPosition(0.99f, 0.8f).setFont(Typeface.MONOSPACE, Color.YELLOW, 0.05f, Paint.Align.RIGHT)
        );

        /*
            Remove all images
        */
        for (Panel p : this.panels) {
            p.getChildren().subList(1, p.getChildren().size()).clear();
        }

        /*
            Set font to yellow if can upgrade, else white
        */
        ((Text) this.panels[0].getChildren().get(0)).setColor(Statistics.PLAYER_MAX_HEALTH.get() < 10 && Statistics.PLAYER_SPENDABLE_POINTS.get() > 0 ? Color.YELLOW : (Statistics.PLAYER_MAX_HEALTH.get() == 10 ? Color.GRAY : Color.WHITE));
        ((Text) this.panels[1].getChildren().get(0)).setColor(Statistics.PLAYER_DEF_SHIELD.get() < 10 && Statistics.PLAYER_SPENDABLE_POINTS.get() > 0 ? Color.YELLOW : (Statistics.PLAYER_DEF_SHIELD.get() == 10 ? Color.GRAY : Color.WHITE));
        ((Text) this.skills[0].getChildren().get(0)).setColor(Statistics.PLAYER_SKILL_SHOCKWAVE.get() < 1 && Statistics.PLAYER_SPENDABLE_POINTS.get() > 0 ? Color.YELLOW : (Statistics.PLAYER_SKILL_SHOCKWAVE.get() == 1 ? Color.GRAY : Color.WHITE));
        ((Text) this.skills[1].getChildren().get(0)).setColor(Statistics.PLAYER_SKILL_SHIELD.get() < 1 && Statistics.PLAYER_SPENDABLE_POINTS.get() > 0 ? Color.YELLOW : (Statistics.PLAYER_SKILL_SHIELD.get() == 1 ? Color.GRAY : Color.WHITE));
        ((Text) this.skills[2].getChildren().get(0)).setColor(Statistics.PLAYER_SKILL_DOUBLE_EXP.get() < 1 && Statistics.PLAYER_SPENDABLE_POINTS.get() > 0 ? Color.YELLOW : (Statistics.PLAYER_SKILL_DOUBLE_EXP.get() == 1 ? Color.GRAY : Color.WHITE));
        ((Text) this.skills[3].getChildren().get(0)).setColor(Statistics.PLAYER_UPGRADE_DMG_SHOCKWAVE.get() < 1 && Statistics.PLAYER_SPENDABLE_POINTS.get() > 0 ? Color.YELLOW : (Statistics.PLAYER_UPGRADE_DMG_SHOCKWAVE.get() == 1 ? Color.GRAY : Color.WHITE));
        ((Text) this.skills[4].getChildren().get(0)).setColor(Statistics.PLAYER_UPGRADE_BETTER_NODES.get() < 1 && Statistics.PLAYER_SPENDABLE_POINTS.get() > 0 ? Color.YELLOW : (Statistics.PLAYER_UPGRADE_BETTER_NODES.get() == 1 ? Color.GRAY : Color.WHITE));

        for (int i = 0; i < 4; i++) {
            this.stars[i].setColor(Statistics.PLAYER_UPGRADE_STAR.get() >= i + 2 ? Color.GRAY : (Statistics.PLAYER_UPGRADE_STAR.get() >= i + 1 && Statistics.PLAYER_SPENDABLE_POINTS.get() > 0 ? Color.YELLOW : Color.WHITE));
        }

        /*
            Add all images
        */
        for (int i = 0; i < Statistics.PLAYER_MAX_HEALTH.get(); i++) {
            this.panels[0].addChild(new Image(Resources.ICONS[0]).setPosition(0.25f + 0.075f * i, 0.5f).center(true));
        }

        for (int i = 0; i < Statistics.PLAYER_DEF_SHIELD.get(); i++) {
            this.panels[1].addChild(new Image(Resources.SHIELD).setPosition(0.25f + 0.075f * i, 0.5f).center(true));
        }
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
