package de.bubbling.game.components;

/**
 * Created with IntelliJ IDEA.
 * User: Andreas
 * Date: 29.07.13
 * Time: 12:53
 * To change this template use File | Settings | File Templates.
 */
public class Stage {

    public static int TWO_ENTITIES = 2;
    public static int THREE_ENTITIES = 3;
    public static int FOUR_ENTITIES = 4;
    public static int FIVE_ENTITIES = 5;
    public static int SIX_ENTITIES = 6;

    private int pointLimit, combination, id;
    private double timePerPerfectStroke;
    private long timeForPerfectStroke;
    private double pointsFactor;

    private Integer[] possibleColors;
    //public enum MovingBubbles { BubblesConst, BubblesMoving};

    public Stage(int pointLimit, int combination, double pointsFactor,
                 double timePerPerfectStroke, long timeForPerfectStroke, int id, Integer... possibleColors) {
        this.pointLimit = pointLimit;
        this.combination = combination;
        this.timePerPerfectStroke = timePerPerfectStroke;
        this.pointsFactor = pointsFactor;
        this.timeForPerfectStroke = timeForPerfectStroke;
        this.id = id;
        this.possibleColors = possibleColors;
    }

    public int getPointLimit() {
        return pointLimit;
    }

    public int getCombination() {
        return combination;
    }

    public double getTimePerPerfectStroke() {
        return timePerPerfectStroke;
    }

    public double getPointsFactor() {
        return pointsFactor;
    }

    public long getTimeForPerfectStroke() {
        return timeForPerfectStroke;
    }

    public int getId() {
        return id;
    }

    public Integer[] getPossibleColors() {
        return possibleColors;
    }
}

