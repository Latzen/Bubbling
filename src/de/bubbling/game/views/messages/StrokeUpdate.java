package de.bubbling.game.views.messages;

/**
 * Created with IntelliJ IDEA.
 * User: Andreas
 * Date: 03.08.13
 * Time: 20:04
 * To change this template use File | Settings | File Templates.
 */
public class StrokeUpdate implements InformationViewTimeUpdate, MessageID {
    private int pointsGained, perfectTimes;
    private double timeGained;



    public enum StrokeType {Perfect, Good, Normal}
    StrokeType type;

    public StrokeUpdate(int pointsGained, double timeGained, StrokeType type, int perfectTimes) {
        this.pointsGained = pointsGained;
        this.timeGained = timeGained;
        this.type = type;
        this.perfectTimes = perfectTimes;
    }

    public int getPointsGained() {
        return pointsGained;
    }

    @Override
    public double getTimeGained() {
        return timeGained;
    }

    public StrokeType getType() {
        return type;
    }
    @Override
    public int getMessageID() {
        return MessageIDs.STROKE_UPDATE;
    }

    public String getPerfectTimes() {
        return perfectTimes > 2 ? " x" + perfectTimes : "";
    }
}
