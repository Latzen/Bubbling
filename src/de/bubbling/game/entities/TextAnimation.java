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
    private int textSize, alpha, fadeOut, upDownVelocity;
    private Paint painter;

    private EnumDrawingState currentState;

    public TextAnimation(int x, int y, boolean visible, String text, int color, int textSize) {
        super(x, y, textSize, textSize,color, visible);
        this.text = text;
        this.textSize = textSize;
        this.alpha = 250;


        painter = new TextPaint();
        painter.setColor(color);
        painter.setTextSize(textSize);
        painter.setFakeBoldText(true);
        painter.setTextAlign(Paint.Align.CENTER);

        currentState = EnumDrawingState.STATE_DRAW;
    }

    public void setTextAlignmentLeft(){
        painter.setTextAlign(Paint.Align.LEFT);
    }

    @Override
    public void draw(Canvas c) {
        c.drawText(text, x, y, painter);
    }

    @Override
    public EnumDrawingState getState() {
        return currentState;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setState(EnumDrawingState state) {
        currentState = state;
    }

    @Override
    public void doAnimationBeforeDraw() {
        switch (this.currentState){
           case STATE_FADE_OUT:
                alpha = alpha-fadeOut;
                painter.setAlpha(alpha);
                if(alpha<25) currentState = EnumDrawingState.STATE_DISMISS;
                break;
            case STATE_MOVE_UP:
                y = y + upDownVelocity;
                break;
        }
    }


    @Override
    public void moveUpDownVelocity(int yVelocity) {
        upDownVelocity = yVelocity;
    }

    public void fadeOut(int i) {
        fadeOut = i;
    }

    @Override
    public void moveLeftRight(int xVelocity) {
        x = x + xVelocity;
    }


}
