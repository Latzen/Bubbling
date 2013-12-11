package de.bubbling.game.entities;

import android.graphics.Canvas;

/**
 * Created with IntelliJ IDEA.
 * User: Andreas
 * Date: 08.08.13
 * Time: 17:59
 * To change this template use File | Settings | File Templates.
 */
public interface CustomAnimation extends DrawObject {

    void moveUpDownVelocity(int yVelocity);

    void fadeOut(int i);

    void moveLeftRight(int xVelocity);
}
