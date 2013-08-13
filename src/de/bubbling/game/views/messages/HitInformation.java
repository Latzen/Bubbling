package de.bubbling.game.views.messages;

/**
 * Created with IntelliJ IDEA.
 * User: Andreas
 * Date: 29.07.13
 * Time: 19:54
 * To change this template use File | Settings | File Templates.
 */
public class HitInformation {
    boolean hit, mark, unmark;

    public HitInformation(boolean hit, boolean mark, boolean unmark) {
        this.hit = hit;
        this.mark = mark;
        this.unmark = unmark;
    }

    public boolean isHit() {
        return hit;
    }

    public boolean isMark() {
        return mark;
    }

    public boolean isUnmark() {
        return unmark;
    }

}
