package de.bubbling.game.components;

/**
 * Created with IntelliJ IDEA.
 * User: Andreas
 * Date: 17.08.13
 * Time: 19:30
 * To change this template use File | Settings | File Templates.
 */
public class ActiveCombinationContainer {

    private int color;
    private int entityType;

    public ActiveCombinationContainer(int color, int entityType) {
        this.color = color;
        this.entityType = entityType;
    }

    public int getColor() {
        return color;
    }

    public int getType() {
        return entityType;
    }
}
