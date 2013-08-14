package de.bubbling.game.components;

/**
 * Created with IntelliJ IDEA.
 * User: Andreas
 * Date: 06.08.13
 * Time: 16:04
 * To change this template use File | Settings | File Templates.
 */
public class GameProgress {
    long score;
    long timePlayed;
    int perfectStrokes;
    int stokesOverall, strokesInARow;
    int stageReached;


    public GameProgress(long score, long timePlayed, int perfectStrokes, int strokesOverall, int strokesInARow, int stageReached) {
        this.score = score;
        this.timePlayed = timePlayed;
        this.perfectStrokes = perfectStrokes;
        this.stokesOverall = strokesOverall;
        this.strokesInARow = strokesInARow ;
        this.stageReached = stageReached;
    }

    public int getStageReached() {
        return stageReached;
    }

    public int getStokesOverall() {
        return stokesOverall;
    }

    public int getPerfectStrokes() {
        return perfectStrokes;
    }

    public long getTimePlayed() {
        return timePlayed;
    }

    public long getScore() {
        return score;
    }

    public int getStrokesInARow() {
        return strokesInARow;
    }
}
