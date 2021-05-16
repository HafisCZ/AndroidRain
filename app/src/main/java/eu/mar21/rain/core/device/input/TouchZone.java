package eu.mar21.rain.core.device.input;

import eu.mar21.rain.core.utils.Resources;

public enum TouchZone {

    ANY(0, 0, Resources.WIDTH, Resources.HEIGHT),

    QUAD_LU(0, 0, Resources.WIDTH / 3, Resources.HEIGHT / 2),
    QUAD_CU(Resources.WIDTH / 3, 0, Resources.WIDTH / 3, Resources.HEIGHT / 2),
    QUAD_RU(2 * Resources.WIDTH / 3, 0, Resources.WIDTH / 3, Resources.HEIGHT / 2),

    QUAD_LD(0, Resources.HEIGHT / 2, Resources.WIDTH / 2, Resources.HEIGHT / 2),
    QUAD_RD(Resources.WIDTH / 2, Resources.HEIGHT / 2, Resources.WIDTH / 2, Resources.HEIGHT / 2),

    ROW_1(0, 0, Resources.WIDTH, Resources.HEIGHT / 4),
    ROW_2(0, Resources.HEIGHT / 4, Resources.WIDTH, Resources.HEIGHT / 4),
    ROW_3(0, Resources.HEIGHT / 2, Resources.WIDTH, Resources.HEIGHT / 4),
    ROW_4(0, 3 * Resources.HEIGHT / 4, Resources.WIDTH, Resources.HEIGHT / 4),

    HALP_UP(0, 0, Resources.WIDTH, Resources.HEIGHT / 2),
    HALF_DOWN(0, Resources.HEIGHT / 2, Resources.WIDTH, Resources.HEIGHT / 2);

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
