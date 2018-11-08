package eu.mar21.rain.core.utils;

import android.view.MotionEvent;
import android.view.View;

public class Input implements View.OnTouchListener {

    private Integer presses[][] = new Integer[5][2];

    @SuppressWarnings("any")
    @Override
    public boolean onTouch(View v, MotionEvent e) {
        for (int i = 0; i < 5; i++) {
            if (i < e.getPointerCount()) {
                switch (e.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_POINTER_DOWN:
                    case MotionEvent.ACTION_MOVE:
                    {
                        presses[i][0] = (int) e.getX(i);
                        presses[i][1] = (int) e.getY(i);
                        break;
                    }
                    default:
                    {
                        presses[i][0] = null;
                        presses[i][1] = null;
                    }
                }
            } else {
                presses[i][0] = null;
                presses[i][1] = null;
            }
        }

        return true;
    }

    public enum ZONE {

        LEFT_UP_QUAD(0, 0, Resources.SCREEN_WIDTH / 2, Resources.SCREEN_HEIGHT / 2),
        LEFT_DOWN_QUAD(0, Resources.SCREEN_HEIGHT / 2, Resources.SCREEN_WIDTH / 2, Resources.SCREEN_HEIGHT),
        RIGHT_UP_QUAD(Resources.SCREEN_WIDTH / 2, 0, Resources.SCREEN_WIDTH, Resources.SCREEN_HEIGHT / 2),
        RIGHT_DOWN_QUAD(Resources.SCREEN_WIDTH / 2, Resources.SCREEN_HEIGHT, Resources.SCREEN_WIDTH, Resources.SCREEN_HEIGHT),
        LEFT_HALF(0, 0, Resources.SCREEN_WIDTH / 2, Resources.SCREEN_HEIGHT),
        RIGHT_HALF(Resources.SCREEN_WIDTH / 2, 0, Resources.SCREEN_WIDTH, Resources.SCREEN_HEIGHT),
        FIRST_ROW(0, 0, Resources.SCREEN_WIDTH, Resources.SCREEN_HEIGHT / 4),
        SECOND_ROW(0, Resources.SCREEN_HEIGHT / 4, Resources.SCREEN_WIDTH, Resources.SCREEN_HEIGHT / 2),
        THIRD_ROW(0, Resources.SCREEN_HEIGHT / 2, Resources.SCREEN_WIDTH, 3 * Resources.SCREEN_HEIGHT / 4),
        FOURTH_ROW_L(0, 3 * Resources.SCREEN_HEIGHT / 4, Resources.SCREEN_WIDTH / 2, Resources.SCREEN_HEIGHT),
        FOURTH_ROW_R(Resources.SCREEN_WIDTH / 2, 3 * Resources.SCREEN_HEIGHT / 4, Resources.SCREEN_WIDTH, Resources.SCREEN_HEIGHT);

        private final int bx;
        private final int by;
        private final int ex;
        private final int ey;

        private boolean lock = false;

        ZONE(double bx, double by, double ex, double ey) {
            this.bx = (int) bx;
            this.by = (int) by;
            this.ex = (int) ex;
            this.ey = (int) ey;
        }

        public boolean inside(int x, int y) {
            return x > bx && x < ex && y > by && y < ey;
        }

    }

    private boolean inside(ZONE zone) {
        boolean is = false;
        for (int i = 0; i < 5; i++) {
            if (this.presses[i][0] == null) {
                continue;
            } else {
                is |= zone.inside(this.presses[i][0], this.presses[i][1]);
            }
        }

        return is;
    }

    public boolean held(ZONE zone) {
        return inside(zone);
    }

    public boolean pressed(ZONE zone) {
        boolean lk = zone.lock;
        zone.lock = inside(zone);
        return zone.lock && !lk;
    }

}
