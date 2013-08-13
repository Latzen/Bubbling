package de.bubbling.game.entities;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import de.bubbling.game.views.messages.HitInformation;

import static android.graphics.BitmapFactory.decodeResource;

/**
 * Created with IntelliJ IDEA.
 * User: Andreas
 * Date: 26.07.13
 * Time: 15:30
 * To change this template use File | Settings | File Templates.
 */
public class Bubble extends Entity {

    int radius, color, numberHit;
    public boolean marked;
    public Bubble(int x, int y, int color, int radius, boolean visible) {
        super(x, y, visible);
        this.numberHit=-1;
        this.radius = radius;
        this.color = color;
        marked = false;
    }

    public void draw(Canvas c,View view){
        Paint cyclePaint = new Paint();
        cyclePaint.setAntiAlias(true);
        if(marked){
            cyclePaint.setColor(Color.YELLOW);
            c.drawCircle(getX()+radius / 2,getY()+ radius / 2,radius / 2, cyclePaint);
            cyclePaint.setColor(color);
            c.drawCircle(getX() + radius / 2, getY() + radius / 2, radius / 2 - radius/20, cyclePaint);

            int textSize = radius/3;
            if(textSize<5) return;
            Paint paintNumber = new Paint();
            paintNumber.setTextSize(textSize);
            paintNumber.setTextAlign(Paint.Align.CENTER);
            paintNumber.setFakeBoldText(true);
            c.drawText(Integer.toString(numberHit + 1), getX() + radius / 2, getY() + radius / 2 + textSize / 2, paintNumber);

        }else{
            cyclePaint.setColor(color);
            c.drawCircle(getX()+radius / 2,getY()+radius / 2, radius / 2, cyclePaint);
        }
    }

    public void collapseAnimation(int velocity){
      // marked = false;
       x = x+velocity/2;
       y = y+velocity/2;
       radius = radius-velocity;
    }

    public HitInformation checkHit(float x, float y, int numberHit, int gameViewTop){
        HitInformation hitInformation;
        if(x>=getX()&&x<=getX()+radius){
            if (y>=getY()+gameViewTop&&y<=getY()+(radius +gameViewTop)){
                marked = !marked;
                if(marked){
                    this.numberHit = numberHit;
                    hitInformation = new HitInformation(true, true, false);
                }else{
                    resetHitNumber();
                    hitInformation = new HitInformation(true, false, true);
                }
                return hitInformation;
            }
        }
        hitInformation = new HitInformation(false, false, false);
        return hitInformation;
    }

    public int getNumberHit() {
        return numberHit;
    }

    public int getColor() {
        return color;
    }

    public void resetHitNumber(){
        numberHit = -1;
    }
}
