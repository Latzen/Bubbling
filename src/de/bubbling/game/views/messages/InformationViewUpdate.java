package de.bubbling.game.views.messages;

import de.bubbling.game.entities.Bubble;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Andreas
 * Date: 29.07.13
 * Time: 14:26
 * To change this template use File | Settings | File Templates.
 */
public class InformationViewUpdate implements MessageID {
    private int points, lives;
    private double countDown;

    public InformationViewUpdate(double countDown, int points, int lives) {
        this.countDown = countDown;
        this.points = points;
        this.lives = lives;
    }

    public double getCountDown() {
        return countDown;
    }

    public int getPoints() {
        return points;
    }

    public int getLives() {
        return lives;
    }

    @Override
    public int getMessageID() {
        return MessageIDs.INFO_VIEW_UPDATE;
    }
}
