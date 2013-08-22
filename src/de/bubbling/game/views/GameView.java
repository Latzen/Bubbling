package de.bubbling.game.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import de.bubbling.game.components.BubblingGameMaster;
import de.bubbling.game.entities.*;
import de.bubbling.game.views.messages.*;

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
    ArrayList<Entity> entities;
    CustomAnimation gainedPoints, strokeUpdate;
    public static int HEIGHT_DIVISOR = 6;
    public static int HEIGHT_MULTIPLIKATOR = 5;

    public GameView(Context context, int width, int height) {
        super(context);
        this.width = width;
        this.height = height / HEIGHT_DIVISOR * HEIGHT_MULTIPLIKATOR;
        entities = new ArrayList<Entity>();

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        for (Entity b : entities) {

            b.draw(canvas);
        }
        if (gainedPoints != null) {
            gainedPoints.draw(canvas);
        }
        if (strokeUpdate != null) {
            strokeUpdate.draw(canvas);
        }
    }

    @Override
    public void update(Observable observable, Object data) {
        MessageID id = (MessageID) data;
        if(id.getMessageID()!= MessageIDs.STROKE_UPDATE  && id.getMessageID() != MessageIDs.GAMEVIEW_UPDATE) return;

        Entity lastEntity = getLastHitBubble();
        if (data instanceof GameViewUpdate) {
            GameViewUpdate update = (GameViewUpdate) data;
            entities = update.getEntities();
            postInvalidate();
        } else if (data instanceof StrokeUpdate) {
            StrokeUpdate update = (StrokeUpdate) data;
            int TEXT_SIZE = width / 10;
            switch (update.getType()) {
                case Perfect:
                    strokeUpdate = new TextAnimation(width / 2, 0 + TEXT_SIZE, true, "Perfect" + update.getPerfectTimes(), Color.rgb(0, 232, 0), TEXT_SIZE);
                    showPerfectStrokeText(strokeUpdate);
                    break;
                case Good:
                    strokeUpdate = new TextAnimation(width / 2, 0 + TEXT_SIZE, true, "Good", Color.rgb(0, 232, 0), TEXT_SIZE);
                    showPerfectStrokeText(strokeUpdate);
                    break;
            }
              if(lastEntity != null){
                gainedPoints = new TextAnimation(lastEntity.getX(), lastEntity.getY(), true, "+" + update.getPointsGained(), Color.RED, TEXT_SIZE);
                pointsAnimation(gainedPoints, entities);
            }
        }
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

    private synchronized void showPerfectStrokeText(final CustomAnimation textAnimation) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int timeElapsed = 0;
                while (timeElapsed < 500) {
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

    private synchronized void pointsAnimation(final CustomAnimation animation, final ArrayList<Entity> bubbles) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int timeElapsed = 0;
                while (timeElapsed < 500) {
                    for (Entity entity : bubbles) {
                       entity.collapseAnimation(5);
                    }
                    animation.moveUpDown(-2);
                    animation.fadeOut();
                    try {
                        Thread.sleep(5);
                        timeElapsed += 5;
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
