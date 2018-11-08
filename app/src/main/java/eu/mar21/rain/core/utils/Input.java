package eu.mar21.rain.core.utils;

import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class Input implements View.OnTouchListener {

    private List<Vec3d> coords = new ArrayList<>();

    @Override
    public boolean onTouch(View v, MotionEvent e) {
        this.coords.clear();

        for (int i = 0; i < e.getPointerCount(); i++) {
            switch (e.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_POINTER_DOWN:
                case MotionEvent.ACTION_MOVE:
                {
                    this.coords.add(new Vec3d(e.getX(i), e.getY(i)));
                }
            }
        }

        return true;
    }

    public static final int UP = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;
    public static final int SKILL = 4;

    public boolean isHeld(int action) {
        switch (action) {
            case UP:
                return isInside(0, Resources.SCREEN_HEIGHT / 2, Resources.SCREEN_WIDTH, 3 * Resources.SCREEN_HEIGHT / 4);
            case LEFT:
                return isInside(0, 3 * Resources.SCREEN_HEIGHT / 4, Resources.SCREEN_WIDTH / 2, Resources.SCREEN_HEIGHT);
            case RIGHT:
                return isInside(Resources.SCREEN_WIDTH / 2, 3 * Resources.SCREEN_HEIGHT / 4, Resources.SCREEN_WIDTH, Resources.SCREEN_HEIGHT);
            case SKILL:
                return isInside(0, 0, 200, 200);
            default: return false;
        }
    }

    public void drawZones(Canvas c) {
        // draw control zones
    }

    private boolean isInside(double x, double y, double w, double h) {
        boolean inside = false;

        for (Vec3d v : coords) {
            inside |= (v.x >= x && v.x <= w && v.y >= y && v.y <= h);
        }

        return inside;
    }


    public static Vec3d getSwipeDirection(double vx, double vy, float correction) {
        final double ax = Math.abs(vx);
        final double ay = Math.abs(vy);

        if (ax < correction && ay < correction) {
            return new Vec3d(0, 0);
        } else {
            if (Math.abs(vx) > Math.abs(vy)) {
                return new Vec3d(vx > 0 ? 1 : -1, 0);
            } else {
                return new Vec3d(0, vy > 0 ? 1 : -1);
            }
        }
    }

    public boolean isPressed(int x, int y, int w, int h) {
        return false;
    }

}
