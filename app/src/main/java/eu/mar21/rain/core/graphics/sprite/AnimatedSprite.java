package eu.mar21.rain.core.graphics.sprite;

import android.graphics.Bitmap;

public class AnimatedSprite extends Sprite {

    private final Bitmap[] frames;
    private final int frameTime;

    private int[][] indexes;

    private int groupIndex = 0;
    private int frameIndex = 0;
    private int currentTime = 0;

    private boolean running = false;

    public AnimatedSprite(Bitmap[] images, int frameTime, int[] ... frameGroups) {
        super(images[0], 1, 1);

        this.frameTime = frameTime;
        this.frames = images;
        this.indexes = frameGroups;
    }

    @Override
    public void selectTile(int group, int frame) {
        this.frameIndex = frame;
        this.groupIndex = group;

        super.image = this.frames[indexes[group][frame]];
    }

    public void play(int group) {
        if (!this.running) {
            this.running = true;

            this.frameIndex = 0;
            this.groupIndex = group;
            this.currentTime = 0;

            selectTile(group, 0);
        } else if (this.groupIndex != group) {
            this.groupIndex = group;
        }
    }

    public void stop() {
        if (this.running) {
            this.running = false;
            selectTile(0, 0);
        }
    }

    public void tick() {
        if (this.running) {
            if (++this.currentTime >= this.frameTime) {
                if (++this.frameIndex >= this.indexes[this.groupIndex].length) {
                    this.frameIndex = 0;
                }

                this.currentTime = 0;

                selectTile(this.groupIndex, this.frameIndex);
            }
        }
    }

}