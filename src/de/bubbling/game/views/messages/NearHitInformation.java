package de.bubbling.game.views.messages;

import de.bubbling.game.entities.Entity;

/**
 * Created with IntelliJ IDEA.
 * User: Andreas
 * Date: 14.08.13
 * Time: 15:10
 * To change this template use File | Settings | File Templates.
 */
public class NearHitInformation {

    public enum Hit {Hitted, NoHit}

    private float xDistance, yDistance;
    private Entity entity;
    private Hit isHit;

    public NearHitInformation(float xDistance, float yDistance, Entity entity, Hit hit) {
        this.xDistance = xDistance;
        this.yDistance = yDistance;
        this.entity = entity;
        isHit = hit;
    }

    public Entity getEntity() {
        return entity;
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
