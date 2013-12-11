package de.bubbling.game.entities;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import de.bubbling.game.views.messages.NearHitInformation;

import static android.graphics.BitmapFactory.decodeResource;

/**
 * Created with IntelliJ IDEA.
 * User: Andreas
 * Date: 26.07.13
 * Time: 15:30
 * To change this template use File | Settings | File Templates.
 */
public class Bubble extends Entity {

    private int radius;

    public Bubble(int x, int y, int color, int radius, boolean visible) {
        super(x, y, radius, radius, color, visible);
        this.radius = radius;
        type = Entity.BUBBLE_TYPE;
    }
    @Override
    public void draw(Canvas c) {
        if (marked) {
            paint.setColor(getPressedColor());
            c.drawCircle(getX() + radius / 2, getY() + radius / 2, radius / 2, paint);
            paint.setColor(color);
            c.drawCircle(getX() + radius / 2, getY() + radius / 2, radius / 2 - radius / 20, paint);

            int textSize = radius / 3;
            if (textSize < 5){
                currentState = EnumDrawingState.STATE_DISMISS;
                return;
            }
            paintNumber.setTextSize(textSize);
            c.drawText(Integer.toString(numberHit + 1), getX() + radius / 2, getY() + radius / 2 + textSize / 2, paintNumber);

        } else {
            paint.setColor(color);
            c.drawCircle(getX() + radius / 2, getY() + radius / 2, radius / 2, paint);
        }
    }
    @Override
    public void collapseAnimation(int velocity) {
        // marked = false;
        x = x + velocity / 2;
        y = y + velocity / 2;
        radius = radius - velocity;
    }

    @Override
    public NearHitInformation checkNearHit(float x, float y, int gameViewTop, int maximum) {
        if (getX() - maximum <= x && getX() + radius + maximum >= x) {
            if (getY() + gameViewTop - maximum <= y && getY() + gameViewTop + radius + maximum >= y) {
                return new NearHitInformation(getX() + radius - x, getY() + radius - y, this, NearHitInformation.Hit.Hitted);
            }
        }
        return new NearHitInformation(getX() + radius - x, getY() + radius - y, this, NearHitInformation.Hit.NoHit);
    }
}
