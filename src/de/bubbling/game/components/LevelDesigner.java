package de.bubbling.game.components;

import android.graphics.Color;
import de.bubbling.game.activities.R;
import de.bubbling.game.difficulty.DifficultyProperties;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Andreas
 * Date: 08.08.13
 * Time: 17:08
 * To change this template use File | Settings | File Templates.
 */
public class LevelDesigner {

    private static String LEVEL_ONE = "First Level";
    private static String LEVEL_TWO = "Second Level";
    private static String LEVEL_THREE = "Third Level";
    private static String LEVEL_FOUR = "Fourth Level";
    public static String LEVEL_SUDDENDEATH = "Sudden Death";


    public static int MAXIMAL_STAGE = 5;
    private static int RED = Color.rgb(255, 23, 56), LightSeaGreen = Color.rgb(32,178,170), LIGHT_ORANGE = Color.rgb(255, 255, 0);
    private static int PURPLE = Color.rgb(208, 5, 101), BLUE = Color.rgb(0, 167, 255), PINK = Color.rgb(255, 50, 255);
    private static int GREEN = Color.rgb(0, 232, 0), GOLD = Color.rgb(255, 185, 15), YELLOW = Color.rgb(255, 255, 0);
    private static int BLACK = Color.BLACK, GRAY = Color.GRAY, LIGHT_GRAY = Color.LTGRAY;
    private ArrayList<Level> level;
    private int nextLevel = -1;

    public LevelDesigner(DifficultyProperties.Difficulty difficulty) {
        level = new ArrayList<Level>();
        int levelID=1;
        switch (difficulty) {
            case easy:
            case normal:
            case hard:
                Level levelOne  = new Level(new Scene(R.drawable.backgroundgray, LEVEL_ONE), Level.UsedTypes.Bubbles, levelID);
                levelOne.addStage(new Stage(4500, Stage.TWO_ENTITIES, 1.8, 0.6, 800, levelOne.stages(), RED, GREEN, BLUE ));
                levelOne.addStage(new Stage(15000, Stage.THREE_ENTITIES, 2.2, 1.1, 1280, levelOne.stages(), RED, GREEN, BLUE, LIGHT_ORANGE, GOLD, LightSeaGreen));
                levelOne.addStage(new Stage(20000, Stage.THREE_ENTITIES, 2.2, 1.1, 1280, levelOne.stages(), RED, GREEN, BLUE, PURPLE, LIGHT_ORANGE, GOLD,LightSeaGreen));
                levelOne.addStage(new Stage(29000, Stage.FOUR_ENTITIES, 2.7, 1.7, 1750, levelOne.stages(), RED, GREEN, BLUE, PURPLE, LIGHT_ORANGE, GOLD));
                levelOne.addStage(new Stage(35000, Stage.FOUR_ENTITIES, 2.7, 1.7, 1750, levelOne.stages(), RED, GREEN, BLUE, PURPLE, PINK, LIGHT_ORANGE, GOLD));
                levelOne.addStage(new Stage(44000, Stage.FIVE_ENTITIES, 3.2, 2.2, 2250, levelOne.stages(), RED, GREEN, BLUE, PURPLE, PINK, LIGHT_ORANGE, GOLD,LightSeaGreen));
                levelOne.addStage(new Stage(50000, Stage.FIVE_ENTITIES, 3.2, 2.2, 2250, levelOne.stages(), RED, GREEN, BLUE, PURPLE, PINK, LIGHT_ORANGE, YELLOW, GOLD));
                levelOne.addStage(new Stage(60000, Stage.SIX_ENTITIES, 3.7, 2.8, 2750, levelOne.stages(), RED, GREEN, BLUE, PURPLE, PINK, LIGHT_ORANGE, YELLOW, GOLD, LightSeaGreen));
                level.add(levelOne);

                Level two  = new Level(new Scene(R.drawable.backgroundgray, LEVEL_TWO), Level.UsedTypes.Triangle,  levelID++);
                two.addStage(new Stage(65000, Stage.THREE_ENTITIES, 2.4, 1.1, 1280, two.stages(), RED, GREEN, BLUE, LightSeaGreen));
                two.addStage(new Stage(72000, Stage.THREE_ENTITIES, 2.4, 1.1, 1280, two.stages(), RED, GREEN, BLUE, LIGHT_ORANGE));
                two.addStage(new Stage(82000, Stage.FOUR_ENTITIES, 2.7, 1.7, 1750, two.stages(),RED, GREEN,BLUE, PURPLE, LIGHT_ORANGE, GOLD));
                two.addStage(new Stage(90000, Stage.FIVE_ENTITIES, 3.2, 2.2, 2250, two.stages(),RED, GREEN,BLUE, PURPLE, LIGHT_ORANGE, GOLD,YELLOW, LightSeaGreen));
                level.add(two);

                Level three  = new Level(new Scene(R.drawable.backgroundgray, LEVEL_THREE), Level.UsedTypes.TriangleBubbles,  levelID++);
                three.addStage(new Stage(95000, Stage.THREE_ENTITIES, 2.4, 1.1, 1280, three.stages(), RED, GREEN, BLUE, LightSeaGreen));
                three.addStage(new Stage(100000, Stage.THREE_ENTITIES, 2.4, 1.1, 1280, three.stages(), RED, GREEN, BLUE, LIGHT_ORANGE, GOLD));
                three.addStage(new Stage(105000, Stage.THREE_ENTITIES, 2.4, 1.1, 1280, three.stages(),RED, GREEN,BLUE, PURPLE, LIGHT_ORANGE, GOLD));
                three.addStage(new Stage(115000, Stage.FOUR_ENTITIES, 2.9, 1.8, 1750, three.stages(),RED, GREEN,BLUE, PURPLE, LIGHT_ORANGE, GOLD, LightSeaGreen));
                three.addStage(new Stage(125000, Stage.FOUR_ENTITIES, 2.9, 1.8, 1750, three.stages(),RED, GREEN,BLUE, PURPLE, LIGHT_ORANGE, GOLD,YELLOW,PINK ));
                three.addStage(new Stage(138000, Stage.FIVE_ENTITIES, 3.3, 2.3, 2250, three.stages(),RED, GREEN,BLUE, PURPLE, LIGHT_ORANGE, GOLD,YELLOW,PINK ));
                three.addStage(new Stage(150000, Stage.SIX_ENTITIES, 3.7, 2.8, 2750, three.stages(),RED, GREEN,BLUE, PURPLE, LIGHT_ORANGE, GOLD,YELLOW,PINK, LightSeaGreen ));
                level.add(three);

                Level four  = new Level(new Scene(R.drawable.backgroundgray, LEVEL_FOUR), Level.UsedTypes.TriangleBubblesRectangle,  levelID++);
                four.addStage(new Stage(160000, Stage.THREE_ENTITIES, 2.4, 1.1, 1280, four.stages(), RED, GREEN, BLUE, LightSeaGreen));
                four.addStage(new Stage(166000, Stage.THREE_ENTITIES, 2.4, 1.1, 1280, four.stages(), RED, GREEN, BLUE, LIGHT_ORANGE, GOLD));
                four.addStage(new Stage(170000, Stage.THREE_ENTITIES, 2.4, 1.1, 1280, four.stages(),RED, GREEN,BLUE, PURPLE, LIGHT_ORANGE, GOLD));
                four.addStage(new Stage(178000, Stage.FOUR_ENTITIES, 2.9, 1.8, 1750, four.stages(),RED, GREEN,BLUE, PURPLE, LIGHT_ORANGE, GOLD, LightSeaGreen));
                four.addStage(new Stage(185000, Stage.FOUR_ENTITIES, 2.9, 1.8, 1750, four.stages(),RED, GREEN,BLUE, PURPLE, LIGHT_ORANGE, GOLD,YELLOW,PINK ));
                four.addStage(new Stage(190000, Stage.FIVE_ENTITIES, 3.3, 2.3, 2250, four.stages(),RED, GREEN,BLUE, PURPLE, LIGHT_ORANGE, GOLD,YELLOW,PINK, LightSeaGreen ));
                four.addStage(new Stage(200000, Stage.SIX_ENTITIES, 3.7, 2.8, 2750, four.stages(),RED, GREEN,BLUE, PURPLE, LIGHT_ORANGE, GOLD,YELLOW,PINK ));
                level.add(four);

                Level suddenDeath = new Level(new Scene(R.drawable.backgroundgray, LEVEL_SUDDENDEATH), Level.UsedTypes.TriangleBubblesRectangle,levelID++);
                //highscore block
                suddenDeath.addStage(new Stage(300000, Stage.SIX_ENTITIES, 3.7, 0.0, 2750, four.stages(),RED, GREEN,BLUE, PURPLE, LIGHT_ORANGE, GOLD,YELLOW,PINK, LightSeaGreen));

                level.add(suddenDeath);

        }
    }

    public Level getNextLevel(){
        if(!isLastStage()) {
            nextLevel++;
            return level.get(nextLevel);
        }
        return null;
    }
    public Level getCurrentLevel(){
        return level.get(nextLevel);
    }

    public boolean isLastStage(){
        return nextLevel == level.size()-1;
    }
}
