package eu.mar21.rain.core.device.input;

import eu.mar21.rain.core.utils.Resources;

public enum TouchZone {

    ANY(0, 0, Resources.SCREEN_WIDTH, Resources.SCREEN_HEIGHT),

    QUAD_LU(0, 0, Resources.SCREEN_WIDTH / 3, Resources.SCREEN_HEIGHT / 2),
    QUAD_CU(Resources.SCREEN_WIDTH / 3, 0, Resources.SCREEN_WIDTH / 3, Resources.SCREEN_HEIGHT / 2),
    QUAD_RU(2 * Resources.SCREEN_WIDTH / 3, 0, Resources.SCREEN_WIDTH / 3, Resources.SCREEN_HEIGHT / 2),

    QUAD_LD(0, Resources.SCREEN_HEIGHT / 2, Resources.SCREEN_WIDTH / 2, Resources.SCREEN_HEIGHT / 2),
    QUAD_RD(Resources.SCREEN_WIDTH / 2, Resources.SCREEN_HEIGHT / 2, Resources.SCREEN_WIDTH / 2, Resources.SCREEN_HEIGHT / 2),

    ROW_1(0, 0, Resources.SCREEN_WIDTH, Resources.SCREEN_HEIGHT / 4),
    ROW_2(0, Resources.SCREEN_HEIGHT / 4, Resources.SCREEN_WIDTH, Resources.SCREEN_HEIGHT / 4),
    ROW_3(0, Resources.SCREEN_HEIGHT / 2, Resources.SCREEN_WIDTH, Resources.SCREEN_HEIGHT / 4),
    ROW_4(0, 3 * Resources.SCREEN_HEIGHT / 4, Resources.SCREEN_WIDTH, Resources.SCREEN_HEIGHT / 4),

    HALP_UP(0, 0, Resources.SCREEN_WIDTH, Resources.SCREEN_HEIGHT / 2),
    HALF_DOWN(0, Resources.SCREEN_HEIGHT / 2, Resources.SCREEN_WIDTH, Resources.SCREEN_HEIGHT / 2);

    // Params
    public final double x;
    public final double y;
    public final double w;
    public final double h;

    // Constructor
    TouchZone(double x, double y, double w, double h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    // Methods
    public boolean inside(double x, double y) {
        return x > this.x && y > this.y && x < this.x + this.w && y < this.y + this.h;
    }

}
