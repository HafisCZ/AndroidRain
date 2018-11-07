package eu.mar21.rain.core.graphics.sprite;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Sprite {

    private final int rowCount;
    private final int colCount;
    private int tileWidth;
    private int tileHeight;
    protected Bitmap image;

    private int selectedRow = 0;
    private int selectedCol = 0;

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

        this.tileWidth = image.getWidth() / colCount;
        this.tileHeight = image.getHeight() / rowCount;
    }

    public void draw(Canvas c, int x, int y) {
        final int sx = this.selectedCol * this.tileWidth;
        final int sy = this.selectedRow * this.tileHeight;
        final int sw = sx + this.tileWidth;
        final int sh = sy + this.tileHeight;
        c.drawBitmap(this.image, new Rect(sx, sy, sw, sh), new Rect(x, y, x + sw, y + sh), null);
    }

    public void selectTile(int row, int col) {
        if (row >= 0 && row < this.rowCount && col >= 0 && col < this.colCount) {
            this.selectedRow = row;
            this.selectedCol = col;
        }
    }
}
