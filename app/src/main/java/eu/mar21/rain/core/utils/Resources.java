package eu.mar21.rain.core.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import eu.mar21.rain.core.R;

public class Resources {

    public static Bitmap TILE_EMPTY;
    public static Bitmap TILE_WALL;
    public static Bitmap TILE_BOX;
    public static Bitmap TILE_GOAL;
    public static Bitmap TILE_HERO;
    public static Bitmap TILE_BOXOK;
    public static Bitmap LOGO;

    public static Paint FONT;
    public static Paint ALPHA_ONLY_PAINT;

    private static Rect TILE;

    static {
        TILE = new Rect();

        ALPHA_ONLY_PAINT = new Paint();

        FONT = new Paint();
        FONT.setColor(Color.WHITE);
        FONT.setTextSize(30);
    }

    public static Rect tile(float x, float y, float w, float h) {
        return tile((int) x, (int) y, (int) w, (int) h);
    }

    public static Rect tile(double x, double y, double w, double h) {
        return tile((int) x, (int) y, (int) w, (int) h);
    }

    public static Rect tile(int x, int y, int w, int h) {
        TILE.set(x, y, x + w, y + h);
        return TILE;
    }

    private static Bitmap load(Context c, int r) {
        return BitmapFactory.decodeResource(c.getResources(), r);
    }

    public static void load(Context c) {
        TILE_EMPTY = load(c, R.drawable.empty);
        TILE_WALL = load(c, R.drawable.wall);
        TILE_BOX = load(c, R.drawable.box);
        TILE_GOAL = load(c, R.drawable.goal);
        TILE_HERO = load(c, R.drawable.hero);
        TILE_BOXOK = load(c, R.drawable.boxok);
        LOGO = load(c, R.drawable.logo);
    }

}
