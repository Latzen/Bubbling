package de.bubbling.game.entities;

import android.graphics.Canvas;

/**
 * Created with IntelliJ IDEA.
 * User: Andreas
 * Date: 11.12.13
 * Time: 15:48
 * To change this template use File | Settings | File Templates.
 */
public interface DrawObject {
    void draw(Canvas c);
    EnumDrawingState getState();
    void setState(EnumDrawingState state);
    void doAnimationBeforeDraw();
}
