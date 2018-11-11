package eu.mar21.rain.core.utils;

import android.view.MotionEvent;
import android.view.View;

import java.util.ResourceBundle;

public class InputListener implements View.OnTouchListener {

    public enum TouchMode {
        TAP, SWIPE
    }

    private static final int MAX_CURSORS = 5;

    private Integer cursorsB[][] = new Integer[MAX_CURSORS][2];
    private Integer cursorsE[][] = new Integer[MAX_CURSORS][2];

    @SuppressWarnings("all")
    @Override
    public boolean onTouch(View v, MotionEvent e) {
        for (int i = 0; i < MAX_CURSORS; i++) {
            if (i < e.getPointerCount()) {
                switch (e.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_POINTER_DOWN:
                    {
                        this.cursorsB[i][0] = (int) e.getX(i);
                        this.cursorsB[i][1] = (int) e.getY(i);
                        break;
                    }
                    case MotionEvent.ACTION_MOVE:
                    {
                        this.cursorsE[i][0] = (int) e.getX(i);
                        this.cursorsE[i][1] = (int) e.getY(i);
                        break;
                    }
                    default:
                    {
                        this.cursorsB[i][0] = null;
                        this.cursorsB[i][1] = null;
                        this.cursorsE[i][0] = null;
                        this.cursorsE[i][1] = null;
                    }
                }
            } else {
                this.cursorsB[i][0] = null;
                this.cursorsB[i][1] = null;
                this.cursorsE[i][0] = null;
                this.cursorsE[i][1] = null;
            }
        }

        return true;
    }

    private TouchMode mode = TouchMode.TAP;
    public void setMode(TouchMode mode) {
        this.mode = mode;
    }

    public enum ControlZone {

        WHOLE_SCREEN(0, 0, Resources.SCREEN_WIDTH, Resources.SCREEN_HEIGHT),
        LU_QUAD(0, 0, Resources.SCREEN_WIDTH / 2, Resources.SCREEN_HEIGHT / 2),
        LD_QUAD(0, Resources.SCREEN_HEIGHT / 2, Resources.SCREEN_WIDTH / 2, Resources.SCREEN_HEIGHT / 2),
        RU_QUAD(Resources.SCREEN_WIDTH / 2, 0, Resources.SCREEN_WIDTH / 2, Resources.SCREEN_HEIGHT / 2),
        RD_QUAD(Resources.SCREEN_WIDTH / 2, Resources.SCREEN_HEIGHT / 2, Resources.SCREEN_WIDTH / 2, Resources.SCREEN_HEIGHT / 2),
        ROW3(0, Resources.SCREEN_HEIGHT / 2, Resources.SCREEN_WIDTH, Resources.SCREEN_HEIGHT / 4),
        ROW4(0, 3 * Resources.SCREEN_HEIGHT / 4, Resources.SCREEN_WIDTH, Resources.SCREEN_HEIGHT / 4);

        private final int x, y, xx, yy;
        private boolean lock;

        ControlZone(double x, double y, double w, double h) {
            this.x = (int) x;
            this.y = (int) y;
            this.xx = this.x + (int) w;
            this.yy = this.y + (int) h;
            this.lock = false;
        }

        public boolean inside(int x, int y) {
            return x > this.x && x < this.xx && y > this.y && y < this.yy;
        }
    }

    private boolean inside(int x, int y, int bx, int by, int ex, int ey) {
        return x > bx && x < ex && y > by && y < ey;
    }

    public boolean isHeld(ControlZone zone) {
        boolean held = false;

        for (int i = 0; i < MAX_CURSORS; i++) {
            if (this.cursorsB[i][1] != null) {
                held |= zone.inside(this.cursorsB[i][0], this.cursorsB[i][1]);
            }
        }

        return held;
    }

    public boolean isPressed(ControlZone zone) {
        final boolean lock = zone.lock;

        zone.lock = isHeld(zone);
        return zone.lock && !lock;
    }

    public boolean isPressed(int bx, int by, int ex, int ey) {
        return isHeld(bx, by, ex, ey);
    }

    public void reset() {
        for (int i = 0; i < MAX_CURSORS; i++) {
            this.cursorsB[i][0] = null;
            this.cursorsB[i][1] = null;
            this.cursorsE[i][0] = null;
            this.cursorsE[i][1] = null;
        }
    }

    public enum Direction {
        NONE,
        RIGHT, LEFT,
        UP, DOWN
    }

    private boolean isHeld(int bx, int by, int ex, int ey) {
        boolean held = false;

        for (int i = 0; i < MAX_CURSORS; i++) {
            if (this.cursorsB[i][1] != null) {
                held |= inside(this.cursorsB[i][0], this.cursorsB[i][1], bx, by, ex, ey);
            }
        }

        return held;
    }

    public Direction isTouch(ControlZone zone) {
        if (this.mode == TouchMode.TAP) {
            if (isHeld(zone.x + (zone.xx - zone.x) / 2, zone.y, zone.xx, zone.yy)) {
                return Direction.RIGHT;
            } else if (isHeld(zone.x, zone.y, (zone.xx - zone.x) / 2 + zone.x, zone.yy)) {
                return Direction.LEFT;
            } else {
                return Direction.NONE;
            }
        } else {
            return isSwipe(zone);
        }
    }

    public Direction isSwipe(ControlZone zone) {
        Direction direction = Direction.NONE;

        for (int i = 0; i < MAX_CURSORS; i++) {
            if (this.cursorsB[i][0] != null && this.cursorsE[i][0] != null) {
                if (zone.inside(this.cursorsB[i][0], this.cursorsB[i][1])) {
                    double sx = this.cursorsE[i][0] - this.cursorsB[i][0];
                    double sy = this.cursorsE[i][1] - this.cursorsB[i][1];

                    if (Math.abs(sx) > Math.abs(sy)) {
                        direction = sx > 0 ? Direction.RIGHT : Direction.LEFT;
                    } else {
                        direction = sy > 0 ? Direction.DOWN : Direction.UP;
                    }

                    return direction;
                }
            }
        }

        return direction;
    }

}
