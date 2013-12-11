package de.bubbling.game.entities;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;

/**
 * Created with IntelliJ IDEA.
 * User: Andreas
 * Date: 04.08.13
 * Time: 14:21
 * To change this template use File | Settings | File Templates.
 */
public class TextAnimation extends Entity implements CustomAnimation {

    private String text;
    private int textSize, alpha;
    private Paint painter;

    public TextAnimation(int x, int y, boolean visible, String text, int color, int textSize) {
        super(x, y, textSize, textSize,color, visible);
        this.text = text;
        this.textSize = textSize;
        this.alpha = 200;


        painter = new TextPaint();
        painter.setColor(color);
        painter.setTextSize(textSize);
        painter.setFakeBoldText(true);
        painter.setTextAlign(Paint.Align.CENTER);
    }

    public void setTextAlignmentLeft(){
        painter.setTextAlign(Paint.Align.LEFT);
    }

    @Override
    public void draw(Canvas c) {
        c.drawText(text, x, y, painter);
    }

    @Override
    public void moveUpDown(int yVelocity) {
        y = y + yVelocity;
    }

    public void fadeOut() {
        alpha--;
        painter.setAlpha(alpha);
    }

    @Override
    public void moveLeftRight(int xVelocity) {
        x = x + xVelocity;
    }


}
