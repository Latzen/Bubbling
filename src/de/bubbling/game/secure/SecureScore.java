package de.bubbling.game.secure;

/**
 * Created with IntelliJ IDEA.
 * User: Andreas
 * Date: 08.01.14
 * Time: 15:07
 * To change this template use File | Settings | File Templates.
 */
public class SecureScore {

    private long hashedPoints;
    private int randomMultiplicator;

    public SecureScore(long realPoints) {
        randomMultiplicator = (int) Math.random() * 53 + 1;
        hashedPoints = realPoints * randomMultiplicator;
    }

    public long getRealScore() {
        return hashedPoints / randomMultiplicator;
    }
}
