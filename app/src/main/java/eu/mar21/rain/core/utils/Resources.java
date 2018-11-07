package eu.mar21.rain.core.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import eu.mar21.rain.core.R;

public class Resources {

    public static Bitmap BACKGROUND[];

    public static Bitmap LOGO;

    public static Paint FONT;
    public static Paint ALPHA_ONLY;

    public static double SCREEN_WIDTH;
    public static double SCREEN_HEIGHT;

    public static boolean DONE = false;

    public static Bitmap PLAYER[] = new Bitmap[9];
    public static Bitmap ACID[] = new Bitmap[4];
    public static Bitmap STAR, ENERGY, SHIELD;

    private static Rect RECTANGLE;

    public static Rect RECTANGLE(double x, double y, double w, double h) {
        RECTANGLE.set((int) x, (int) y, (int) (x + w), (int) (y + h));
        return RECTANGLE;
    }

    public static void preload(android.content.res.Resources c) {
        LOGO = BitmapFactory.decodeResource(c, R.drawable.logo);
    }

    public static void loadAll(android.content.res.Resources c, WindowManager wm) {
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getRealMetrics(dm);

        SCREEN_HEIGHT = dm.heightPixels;
        SCREEN_WIDTH = dm.widthPixels;

        BACKGROUND = new Bitmap[8];
        for (int i = 0; i < 8; i++) {
            Bitmap temp = BitmapFactory.decodeResource(c, R.drawable.b0 + i);
            BACKGROUND[i] = Bitmap.createScaledBitmap(temp, (int) (dm.widthPixels * 1.1), dm.heightPixels, true);
        }

        FONT = new Paint();
        FONT.setTextSize(30.0f);
        FONT.setColor(Color.WHITE);

        for (int i = 0; i < 9; i++) {
            PLAYER[i] = BitmapFactory.decodeResource(c, R.drawable.player0 + i);
        }

        for (int i = 0; i < 4; i++) {
            ACID[i] = BitmapFactory.decodeResource(c, R.drawable.acid0 + i);
        }

        STAR = BitmapFactory.decodeResource(c, R.drawable.star);
        SHIELD = BitmapFactory.decodeResource(c, R.drawable.armor);
        ENERGY = BitmapFactory.decodeResource(c, R.drawable.energy);

        ALPHA_ONLY = new Paint();

        RECTANGLE = new Rect();

        DONE = true;
    }
}
