package de.bubbling.game.entities;

import android.graphics.Canvas;
import de.bubbling.game.views.messages.NearHitInformation;

/**
 * Created with IntelliJ IDEA.
 * User: Andreas
 * Date: 17.08.13
 * Time: 11:54
 * To change this template use File | Settings | File Templates.
 */
public interface ITile {
    public void draw (Canvas c);
    public NearHitInformation checkNearHit(float x, float y, int gameViewTop, int maximum);
}
