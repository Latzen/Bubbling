package de.bubbling.game.components;

/**
 * Created with IntelliJ IDEA.
 * User: Andreas
 * Date: 16.08.13
 * Time: 10:51
 * To change this template use File | Settings | File Templates.
 */
public class Scene {
    private int background;
    private String name;

    public Scene(int background, String name) {
        this.background = background;
        this.name = name;
    }

    public int getBackground() {
        return background;
    }

    public String getName() {
        return name;
    }
}
