package de.bubbling.game.views.messages;

/**
 * Created with IntelliJ IDEA.
 * User: Andreas
 * Date: 18.08.13
 * Time: 11:22
 * To change this template use File | Settings | File Templates.
 */
public class GainedTimeUpdate implements InformationViewTimeUpdate, MessageID {
    private double timeGained;

    public GainedTimeUpdate(double timeGained) {
        this.timeGained = timeGained;
    }

    @Override
    public double getTimeGained() {
        return timeGained;
    }

    @Override
    public int getMessageID() {
        return MessageIDs.GAINED_TIME_UPDATE;
    }
}
