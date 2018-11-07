package eu.mar21.rain.core.graphics.sprite;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

public class Sprite {

    private final int rowCount;
    private final int colCount;

    private double tileWidth;
    private double tileHeight;

    protected Bitmap image;

    private static Rect sr = new Rect();
    private static Rect dr = new Rect();

    private int selectedRow = 0;
    private int selectedCol = 0;
    private int spanRow = 1;
    private int spanCol = 1;

    public Sprite(Bitmap image) {
        this(image, 1, 1);
    }

    public Sprite(Bitmap image, int rowCount, int colCount) {
        if (rowCount < 1 || colCount < 1) {
            throw new IllegalArgumentException("Sprite has to have at least 1 row and 1 column!");
        }

        this.image = image;
        this.rowCount = rowCount;
        this.colCount = colCount;

        this.tileWidth = ((double) image.getWidth()) / colCount;
        this.tileHeight = ((double) image.getHeight()) / rowCount;
    }

    public void draw(Canvas c, int x, int y) {
        sr.set(
            (int) (this.selectedCol * this.tileWidth),
            (int) (this.selectedRow * this.tileHeight),
            (int) (this.selectedCol * this.tileWidth + this.spanCol * this.tileWidth),
            (int) (this.selectedRow * this.tileHeight + this.spanRow * this.tileHeight)
        );

        dr.set(x, y, x + (int) (this.spanCol * this.tileWidth), y + (int) (this.spanRow * this.tileHeight));

        c.drawBitmap(this.image, sr, dr,null);
    }

    public void selectTile(int row, int col) {
        if (row >= 0 && row < this.rowCount && col >= 0 && col < this.colCount) {
            this.selectedRow = row;
            this.selectedCol = col;
        }
    }

    public void setSpan(int row, int col) {
        this.spanCol = col;
        this.spanRow = row;
    }
}
