package de.bubbling.game.views.messages;

import de.bubbling.game.secure.SecureScore;

/**
 * Created with IntelliJ IDEA.
 * User: Andreas
 * Date: 29.07.13
 * Time: 14:26
 * To change this template use File | Settings | File Templates.
 */
public class InformationViewUpdate implements MessageID {
    private SecureScore secureScore;
    private int lives;
    private double countDown;

    public InformationViewUpdate(double countDown, SecureScore points, int lives) {
        this.countDown = countDown;
        this.secureScore = points;
        this.lives = lives;
    }

    public double getCountDown() {
        return countDown;
    }

    public long getPoints() {
        return secureScore.getRealScore();
    }

    public int getLives() {
        return lives;
    }

    @Override
    public int getMessageID() {
        return MessageIDs.INFO_VIEW_UPDATE;
    }
}
