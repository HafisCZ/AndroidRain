package eu.mar21.rain.core.entity;

import eu.mar21.rain.core.utils.Resources;

public class Crate extends Entity {

    private boolean t;

    public Crate(int x, int y) {
        super(x, y, Resources.TILE_BOX);
        this.t = false;
    }

    public void setTarget(boolean t) {
        if (t) {
            this.tile = Resources.TILE_BOXOK;
        } else {
            this.tile = Resources.TILE_BOX;
        }

        this.t = t;
    }

    public boolean getTarget() {
        return this.t;
    }

}
