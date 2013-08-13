package de.bubbling.game.entities;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;

/**
 * Created with IntelliJ IDEA.
 * User: Andreas
 * Date: 26.07.13
 * Time: 15:43
 * To change this template use File | Settings | File Templates.
 */
public class Entity {
    protected int x;
    protected int y;
    private boolean visible;


    public Entity(int x, int y, boolean visible){
        this.x = x;
        this.y = y;
        this.visible = visible;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
