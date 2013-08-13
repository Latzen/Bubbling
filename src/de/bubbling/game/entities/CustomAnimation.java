package de.bubbling.game.entities;

import android.graphics.Canvas;

/**
 * Created with IntelliJ IDEA.
 * User: Andreas
 * Date: 08.08.13
 * Time: 17:59
 * To change this template use File | Settings | File Templates.
 */
public interface CustomAnimation {
    void draw(Canvas c);
    void moveUpDown(int yVelocity);
    void fadeOut();
    void moveLeftRight(int xVelocity);
}
