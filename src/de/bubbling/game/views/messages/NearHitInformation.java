package de.bubbling.game.views.messages;

import de.bubbling.game.entities.Bubble;

/**
 * Created with IntelliJ IDEA.
 * User: Andreas
 * Date: 14.08.13
 * Time: 15:10
 * To change this template use File | Settings | File Templates.
 */
public class NearHitInformation {
    float xDistance, yDistance;
    public enum  Hit { Hitted, NoHit};
    Bubble bubble;
    Hit isHit;

    public NearHitInformation(float xDistance, float yDistance,Bubble bubble, Hit hit) {
        this.xDistance = xDistance;
        this.yDistance = yDistance;
        this.bubble = bubble;
        isHit = hit;
    }

    public Bubble getBubble() {
        return bubble;
    }

    public float getxDistance() {
        return xDistance;
    }

    public float getyDistance() {
        return yDistance;
    }

    public Hit getHit() {
        return isHit;
    }
}
