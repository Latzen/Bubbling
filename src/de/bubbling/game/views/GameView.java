package de.bubbling.game.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import de.bubbling.game.entities.Bubble;
import de.bubbling.game.entities.CustomAnimation;
import de.bubbling.game.entities.TextAnimation;
import de.bubbling.game.views.messages.HitInformation;
import de.bubbling.game.views.messages.GameViewUpdate;
import de.bubbling.game.views.messages.StrokeUpdate;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created with IntelliJ IDEA.
 * User: Andreas
 * Date: 28.07.13
 * Time: 10:58
 * To change this template use File | Settings | File Templates.
 */
public class GameView extends View implements Observer {
    private int width, height;
    ArrayList<Bubble> bubbles;
    CustomAnimation gainedPoints, strokeUpdate;
    public static int HEIGHT_DIVISOR = 6;
    public static int HEIGHT_MULTIPLIKATOR = 5;
    public GameView(Context context, int width, int height ) {
        super(context);

        this.width = width;
        this.height = height/ HEIGHT_DIVISOR *HEIGHT_MULTIPLIKATOR;
        bubbles = new ArrayList<Bubble>();

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        for (Bubble b : bubbles) {
            b.draw(canvas, this);
        }
        if(gainedPoints!= null){
            gainedPoints.draw(canvas);
        }
        if(strokeUpdate!=null){
            strokeUpdate.draw(canvas);
        }
    }

    @Override
    public void update(Observable observable, Object data) {
        if(data instanceof GameViewUpdate){
            GameViewUpdate update =  (GameViewUpdate) data ;
            bubbles = update.getBubbles();
            postInvalidate();
        }
        else if (data instanceof StrokeUpdate) {
            StrokeUpdate update = (StrokeUpdate) data;
            int TEXT_SIZE = width/10;
            switch (update.getType()){
                case Perfect:
                    strokeUpdate = new TextAnimation(width/2, height/2, true, "Perfect"+update.getPerfectTimes(), Color.BLACK, TEXT_SIZE);
                    showPerfectStrokeText(strokeUpdate);
                    break;
                case Good:
                    strokeUpdate = new TextAnimation(width/2, height/2, true, "Good", Color.BLACK, TEXT_SIZE);
                    showPerfectStrokeText(strokeUpdate);
                    break;
            }
            Bubble lastBubble = getLastHitBubble();
            gainedPoints = new TextAnimation(lastBubble.getX(), lastBubble.getY(), true, "+"+update.getPointsGained(), Color.RED, TEXT_SIZE);
            pointsAnimation(gainedPoints, bubbles);
        }
    }

    public boolean checkHit(MotionEvent event){
        float xCord = event.getX();
        float yCord = event.getY();
        for (Bubble b : bubbles){
            HitInformation hit = b.checkHit(xCord,yCord, getNextFreeNumber(),height/ HEIGHT_MULTIPLIKATOR);
            if(hit.isHit()){
                postInvalidate();
                return true;
            }
        }
        return  false;
    }

    private Bubble getLastHitBubble(){
        for (Bubble b : bubbles){
            if(b.getNumberHit()==getNextFreeNumber()-1){
                return b;
            }
        }
        return null;
    }

    private int getNextFreeNumber(){
        int nextNumber=0;
        boolean numberUnUsed = false;

        while(!numberUnUsed){
            numberUnUsed = true;
            for(Bubble b : bubbles){
               if(nextNumber==b.getNumberHit()){
                   numberUnUsed = false;
                   nextNumber++;
                   break;
               }
            }
        }
        return nextNumber;
    }

    private synchronized void  showPerfectStrokeText(final CustomAnimation textAnimation){
        new Thread(new Runnable() {
            @Override
            public void run() {
                int timeElapsed=0;
                while (timeElapsed<500){
                    textAnimation.fadeOut();
                    postInvalidate();
                    try {
                        Thread.sleep(100);
                        timeElapsed += 100;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                strokeUpdate = null;
                postInvalidate();
            }
        }).start();
    }

    private synchronized void pointsAnimation(final CustomAnimation animation, final ArrayList<Bubble> bubbles ){
        new Thread(new Runnable() {
            @Override
            public void run() {
                int timeElapsed=0;
                while (timeElapsed<500){
                    for(Bubble b : bubbles) b.collapseAnimation(5);
                    animation.moveUpDown(-2);
                    animation.fadeOut();
                    try {
                        Thread.sleep(10);
                        timeElapsed += 10;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    postInvalidate();
                }
                gainedPoints = null;
            }
        }).start();
    }
}
