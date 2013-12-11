package de.bubbling.game.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import de.bubbling.game.activities.R;
import de.bubbling.game.components.BubblingGameMaster;
import de.bubbling.game.components.DrawingQueue;
import de.bubbling.game.entities.*;
import de.bubbling.game.views.messages.*;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Andreas
 * Date: 28.07.13
 * Time: 10:58
 * To change this template use File | Settings | File Templates.
 */
public class GameView extends View implements Observer {
    public static int HEIGHT_DIVISOR = 6;
    public static int HEIGHT_MULTIPLIKATOR = 5;

    private int width, height;
    private CopyOnWriteArrayList<Entity> entities;
    private CustomAnimation gainedPoints, strokeUpdate;

    private DrawingQueue queueToDraw;

    boolean collapse;
    private String sPerfect, sGood;
    private Paint bubblingPainter;

    public GameView(Context context, int width, int height) {
        super(context);
        this.width = width;
        this.height = height / HEIGHT_DIVISOR * HEIGHT_MULTIPLIKATOR;
        entities = new CopyOnWriteArrayList<Entity>();
        sPerfect = context.getString(R.string.game_board_perfect);
        sGood = context.getString(R.string.game_board_good);
        queueToDraw = new DrawingQueue();
        bubblingPainter = new Paint();
        bubblingPainter.setFakeBoldText(true);
        bubblingPainter.setColor(Color.rgb(76,76,76));
        bubblingPainter.setTextAlign(Paint.Align.CENTER);
        bubblingPainter.setTextSize(height/10);

        doAnimations();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawText("Bubbling", width/2, height- height/20, bubblingPainter);
        queueToDraw.drawElements(canvas);
    }
    public void clearBoard(){
        queueToDraw.clearQueue();
    }

    @Override
    public void update(Observable observable, Object data) {
        MessageID id = (MessageID) data;
        if(id.getMessageID()!= MessageIDs.STROKE_UPDATE  &&
                id.getMessageID() != MessageIDs.GAMEVIEW_UPDATE) return;
        Entity lastEntity = getLastHitBubble();
        if (data instanceof GameViewUpdate) {
            GameViewUpdate update = (GameViewUpdate) data;
            collapse = false;
            ListIterator<Entity> it = update.getEntities().listIterator();
            entities.clear();
            while (it.hasNext()){
                Entity e = it.next();
                entities.add(e);
                queueToDraw.addElement(e);
            }

        } else if (data instanceof StrokeUpdate) {
            StrokeUpdate update = (StrokeUpdate) data;
            int TEXT_SIZE = width / 10;
            TextAnimation animation;
            switch (update.getType()) {
                case Perfect:
                    animation   = new TextAnimation(width / 2, 0 + TEXT_SIZE, true, sPerfect + update.getPerfectTimes(),
                            Color.rgb(0, 232, 0), TEXT_SIZE);
                    animation.fadeOut(1);
                    animation.setState(EnumDrawingState.STATE_FADE_OUT);
                    queueToDraw.addElement(animation);
                    break;
                case Good:
                    animation   = new TextAnimation(width / 2, 0 + TEXT_SIZE, true, sGood,
                            Color.rgb(0, 232, 0), TEXT_SIZE);
                    animation.fadeOut(1);
                    animation.setState(EnumDrawingState.STATE_FADE_OUT);
                    queueToDraw.addElement(animation);
                    break;
            }
            if(lastEntity != null){
                TextAnimation point  =   new TextAnimation(lastEntity.getX(), lastEntity.getY(), true, "+" + update.getPointsGained(),
                        Color.RED, TEXT_SIZE);
                point.setTextAlignmentLeft();
                collapse = true;
                point.moveUpDownVelocity(-2);
                point.setState(EnumDrawingState.STATE_MOVE_UP);
                queueToDraw.addElement(point);
                queueToDraw.collapseAnimation();
            }
        }
        postInvalidate();
    }

    private void doAnimations(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    long timeBefore = System.currentTimeMillis();
                    queueToDraw.doSomeAnimationsBeforeDraw();
                    postInvalidate();
                    long timeAfter = System.currentTimeMillis();
                    try {
                        Thread.sleep(3 - (timeAfter-timeBefore)/1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                }
            }
        }).start();

    }
    public boolean checkHit(MotionEvent event) {
        float xCord = event.getX();
        float yCord = event.getY();

        ArrayList<NearHitInformation> hitInformations = new ArrayList<NearHitInformation>();
        for (Entity b : entities) {
            NearHitInformation hit =
                    b.checkNearHit(xCord, yCord, height / HEIGHT_MULTIPLIKATOR, height / BubblingGameMaster.BUBBLE_RAD_DEVISOR / 5);
            if (hit.getHit() == NearHitInformation.Hit.Hitted) {
                hitInformations.add(hit);
            }
        }
        if (hitInformations.size() >= 1) {
            NearHitInformation info = hitInformations.get(0);
            double distance = Math.pow(info.getxDistance(), 2) + Math.pow(info.getyDistance(), 2);
            distance = Math.sqrt(distance);
            for (NearHitInformation n : hitInformations) {
                double distance2 = Math.pow(n.getxDistance(), 2) + Math.pow(n.getyDistance(), 2);
                distance2 = Math.sqrt(distance2);
                if (distance2 < distance)
                    info = n;
            }
            info.getEntity().setHitted(getNextFreeNumber());
            postInvalidate();
            return true;
        }

        return false;
    }

    private Entity getLastHitBubble() {
        for (Entity b : entities) {
            if (b.getNumberHit() == getNextFreeNumber() - 1) {
                return b;
            }
        }
        return null;
    }

    private int getNextFreeNumber() {
        int nextNumber = 0;
        boolean numberUnUsed = false;

        while (!numberUnUsed) {
            numberUnUsed = true;
            for (Entity b : entities) {
                if (nextNumber == b.getNumberHit()) {
                    numberUnUsed = false;
                    nextNumber++;
                    break;
                }
            }
        }
        return nextNumber;
    }
}
