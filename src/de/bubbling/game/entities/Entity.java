package de.bubbling.game.entities;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import de.bubbling.game.components.Level;
import de.bubbling.game.views.messages.NearHitInformation;

/**
 * Created with IntelliJ IDEA.
 * User: Andreas
 * Date: 26.07.13
 * Time: 15:43
 * To change this template use File | Settings | File Templates.
 */
public class Entity implements ITile {

    public static final int BUBBLE_TYPE = 1;
    public static final int TRIANGLE_TYPE = 2;
    public static final int RECTANGLE_TYPE = 3;

    protected int x;
    protected int y;
    protected int width, height;
    protected boolean marked;
    protected int numberHit, color;
    protected int type;
    private boolean visible;
    protected Paint paint;
    protected Paint paintNumber;

    public Entity(int x, int y, int width, int height, int color, boolean visible) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.marked = false;
        this.numberHit = -1;
        this.color = color;
        this.visible = visible;

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(2);
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);

        paintNumber = new Paint();
        paintNumber.setTextAlign(Paint.Align.CENTER);
        paintNumber.setFakeBoldText(true);
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

    public void setHitted(int number) {
        if (!marked) {
            numberHit = number;
        } else {
            resetHitNumber();
        }
        marked = !marked;
    }

    public int getNumberHit() {
        return numberHit;
    }

    public int getColor() {
        return color;
    }

    public void resetHitNumber() {
        numberHit = -1;

    }
    protected int getPressedColor(){
      int yellow = Color.rgb(255, 255, 0);

      return getColor()==yellow ? Color.DKGRAY : Color.YELLOW;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isMarked() {
        return marked;
    }
    public void resetMarked(){
        marked = false;
    }

    public void collapseAnimation(int velocity){
        // marked = false;
        /*x = x + velocity / 2;
        y = y + velocity / 2;
        width = width - velocity;
        height = height - velocity; */
    }
    @Override
    public void draw(Canvas c) {
    }

    public int getType() {
        return type;
    }

    @Override
    public NearHitInformation checkNearHit(float x, float y, int gameViewTop, int maximum) {
        if (getX() - maximum <= x && getX() + width + maximum >= x) {
            if (getY() + gameViewTop - maximum <= y && getY() + gameViewTop + width + maximum >= y) {
                return new NearHitInformation(getX() + width - x, getY() + width - y, this, NearHitInformation.Hit.Hitted);
            }
        }
        return new NearHitInformation(getX() + width - x, getY() + width - y, this, NearHitInformation.Hit.NoHit);
    }
}

