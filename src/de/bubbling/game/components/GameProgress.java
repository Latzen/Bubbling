package de.bubbling.game.components;

import de.bubbling.game.secure.SecureScore;

/**
 * Created with IntelliJ IDEA.
 * User: Andreas
 * Date: 06.08.13
 * Time: 16:04
 * To change this template use File | Settings | File Templates.
 */
public class GameProgress {
    SecureScore secureScore;
    long timePlayed;
    int perfectStrokes;
    int stokesOverall, strokesInARow;
    int stageReached;

    public GameProgress(SecureScore score, long timePlayed,
                        int perfectStrokes, int strokesOverall, int strokesInARow, int stageReached) {
        this.secureScore = score;
        this.timePlayed = timePlayed;
        this.perfectStrokes = perfectStrokes;
        this.stokesOverall = strokesOverall;
        this.strokesInARow = strokesInARow;
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
        return secureScore.getRealScore();
    }

    public int getStrokesInARow() {
        return strokesInARow;
    }

    public SecureScore getSecureScore() {
        return this.secureScore;
    }

}
