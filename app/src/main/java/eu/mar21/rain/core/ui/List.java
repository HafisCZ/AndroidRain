package eu.mar21.rain.core.ui;

import android.graphics.Canvas;

public class List extends View {

    private int selector = -1;

    public List(View parent, float x, float y, float w, float h) {
        this(x, y, w, h);
        parent.addChild(this);
    }

    public List(float x, float y, float w, float h) {
        super(x, y, w, h);
    }

    public Panel newItem() {
        Panel panel = new Panel(0, 0, 1, 1);
        addItem(panel);
        return panel;
    }

    public void selectPanel(int id) {
        this.selector = id < this.getChildren().size() ? id : -1;
    }

    public void addItem(Panel panel) {
        addChild(panel);
        this.selector = 0;
    }

    @Override
    protected void draw(Canvas c, float px, float py, float pw, float ph) {
        float rx = px + pw * this.x;
        float ry = py + ph * this.y;
        float rw = pw * this.w;
        float rh = ph * this.h;

        drawView(c, rx, ry, rw, rh);

        if (this.selector != -1) {
            this.getChildren().get(this.selector).draw(c, rx, ry, rw, rh);
        }
    }

    public void next() {
        if (this.getChildren().size() > 0) {
            this.selector++;
            if (this.selector >= this.getChildren().size()) {
                this.selector = 0;
            }
        }
    }

    public void prev() {
        if (this.getChildren().size() > 0) {
            this.selector--;
            if (this.selector < 0) {
                this.selector = this.getChildren().size() - 1;
            }
        }
    }

    @Override
    public void drawView(Canvas c, float x, float y, float w, float h) {
        /*
            DEBUG CODE

            Paint pt = new Paint();
            pt.setColor(Color.GREEN);
            pt.setStyle(Paint.Style.STROKE);
            c.drawRect(x, y, x + w, y + h, pt);
        */
    }

}
