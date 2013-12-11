package de.bubbling.game.entities;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created with IntelliJ IDEA.
 * User: Andreas
 * Date: 22.08.13
 * Time: 16:31
 * To change this template use File | Settings | File Templates.
 */
public class Rectangle extends Entity {


    public Rectangle(int x, int y, int width, int height, int color, boolean visible) {
        super(x, y, width, height, color, visible);
        type = RECTANGLE_TYPE;
    }

    @Override
    public void draw(Canvas c) {

        if(isMarked()){
            int textSize = width/3;
            if (textSize < 5) {
                currentState = EnumDrawingState.STATE_DISMISS;
                return;
            }
            paint.setColor(getPressedColor());
            c.drawRect(getX(),getY(),getX()+getWidth(),getY()+getHeight(), paint);
            paint.setColor(color);
            c.drawRect(getX()+width/20, getY()+getHeight()/20, getX()+getWidth()-getWidth()/20,
                    getY()+getHeight()-getHeight()/20, paint);

            paintNumber.setTextSize(textSize);
            c.drawText
                    (Integer.toString(numberHit + 1), getX() +  getWidth()  / 2, getY() + + textSize / 2 + getWidth() / 2, paintNumber);

        }else{
            c.drawRect(getX(),getY(),getX()+getWidth(),getY()+getHeight(),paint);
        }
    }
    @Override
    public void collapseAnimation(int velocity) {
        // marked = false;
        x = x + velocity / 2;
        y = y + velocity / 2;
        width = width - velocity;
        height = height - velocity;
    }
}
