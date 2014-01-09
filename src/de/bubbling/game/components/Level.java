package de.bubbling.game.components;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Andreas
 * Date: 16.08.13
 * Time: 11:29
 * To change this template use File | Settings | File Templates.
 */
public class Level {

    private Scene scene;
    private ArrayList<Stage> stages;

    public enum UsedTypes {Triangle, Bubbles, Rectangle, TriangleBubbles, TriangleBubblesRectangle}

    private UsedTypes usedType;
    private int id;

    public Level(Scene scene, UsedTypes usedType, int id) {
        this.scene = scene;
        this.id = id;
        this.usedType = usedType;
        stages = new ArrayList<Stage>();
    }

    public Scene getScene() {
        return scene;
    }

    public ArrayList<Stage> getStages() {
        return stages;
    }

    public void addStage(Stage s) {
        stages.add(s);
    }

    public int stages() {
        return stages.size();
    }

    public UsedTypes getUsedType() {
        return usedType;
    }

    public int getId() {
        return id;
    }
}
