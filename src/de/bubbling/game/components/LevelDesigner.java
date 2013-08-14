package de.bubbling.game.components;

import android.graphics.Color;
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

    public static int MAXIMAL_STAGE = 7;

    private static int RED = Color.rgb(255,23,56), LIGHT_RED = Color.rgb(238,44,44), LIGHT_ORANGE = Color.rgb(255,255,0);
    private static int PURPLE = Color.rgb(208,5,101), BLUE = Color.rgb(0,167,255), PINK = Color.rgb(255,50,255);
    private static int GREEN =  Color.rgb(0,232,0),GOLD = Color.rgb(255,185,15), YELLOW = Color.rgb(255,255,0);

    private ArrayList<Stage> stages;

    public LevelDesigner(DifficultyProperties.Difficulty difficulty) {
        stages = new ArrayList<Stage>();
        switch (difficulty){
            case easy:
            case normal:
            case hard:
                stages.add(new Stage(5000, Stage.TWO_BUBBLES, 1.5, 0.5, 800, stages.size(), RED, GREEN, BLUE ));
                stages.add(new Stage(12000, Stage.THREE_BUBBLES, 2, 1.1, 1280, stages.size(), RED, GREEN, BLUE, LIGHT_ORANGE, GOLD ));
                stages.add(new Stage(18000, Stage.THREE_BUBBLES, 2, 1.1, 1280, stages.size(), RED, GREEN, BLUE, PURPLE, LIGHT_ORANGE, GOLD ));
                stages.add(new Stage(24000, Stage.FOUR_BUBBLES, 2.5, 1.6, 1750, stages.size(), RED, GREEN, BLUE, PURPLE,LIGHT_ORANGE, GOLD ));
                stages.add(new Stage(30000, Stage.FOUR_BUBBLES, 2.5, 1.6, 1750, stages.size(), RED, GREEN, BLUE, PURPLE,PINK,LIGHT_ORANGE, GOLD ));
                stages.add(new Stage(35000, Stage.FIVE_BUBBLES, 3, 2.1, 2250, stages.size(), RED, GREEN, BLUE, PURPLE,PINK,LIGHT_ORANGE, GOLD ));
                stages.add(new Stage(40000, Stage.FIVE_BUBBLES, 3, 2.1, 2250, stages.size(), RED, GREEN, BLUE, PURPLE,PINK,LIGHT_ORANGE, YELLOW, GOLD));
                stages.add(new Stage(50000, Stage.SIX_BUBBLES, 3.5, 2.6, 2750, stages.size(), RED, GREEN, BLUE, PURPLE,PINK,LIGHT_ORANGE,YELLOW, GOLD));

             /* case easy:
                  stages.add(new Stage(1000, Stage.TWO_BUBBLES, 1, 1.2, 1000, stages.size(), RED, GREEN, BLUE, PURPLE ));
                  stages.add(new Stage(6000, Stage.THREE_BUBBLES, 1.8, 1.7, 1500,stages.size(), RED, GREEN, BLUE, PURPLE, LIGHT_ORANGE, GOLD ));
                  stages.add(new Stage(10000, Stage.FOUR_BUBBLES, 1.8, 2.2, 2000,stages.size(), RED, GREEN, BLUE, PURPLE,LIGHT_ORANGE, GOLD ));
                  stages.add(new Stage(14000, Stage.FOUR_BUBBLES, 1.5, 2.2, 2000,stages.size(), RED, GREEN, BLUE, PURPLE,PINK,LIGHT_ORANGE, GOLD ));
                  stages.add(new Stage(25000, Stage.FIVE_BUBBLES, 2, 2.4, 2200, stages.size(), RED, GREEN, BLUE, PURPLE,PINK,LIGHT_ORANGE, GOLD ));
                  stages.add(new Stage(30000, Stage.FIVE_BUBBLES, 2, 2.4, 2200,stages.size(), RED, GREEN, BLUE, PURPLE,PINK,LIGHT_ORANGE,YELLOW, GOLD));
                  stages.add(new Stage(50000, Stage.SIX_BUBBLES, 2, 2.6, 2600,stages.size(), RED, GREEN, BLUE, PURPLE,PINK,LIGHT_ORANGE,YELLOW, GOLD));
                  break;
              case normal:
                  stages.add(new Stage(1000, Stage.TWO_BUBBLES, 1.1, 1.2, 750, stages.size(), RED, GREEN, BLUE, PURPLE ));
                  stages.add(new Stage(6000, Stage.THREE_BUBBLES, 1.9, 1.4, 1250, stages.size(), RED, GREEN, BLUE, PURPLE, LIGHT_ORANGE, GOLD ));
                  stages.add(new Stage(10000, Stage.FOUR_BUBBLES, 2.1, 1.9, 1750,stages.size(), RED, GREEN, BLUE, PURPLE,LIGHT_ORANGE, GOLD ));
                  stages.add(new Stage(14000, Stage.FOUR_BUBBLES, 2.1, 1.9, 1750, stages.size(), RED, GREEN, BLUE, PURPLE,PINK,LIGHT_ORANGE, GOLD ));
                  stages.add(new Stage(25000, Stage.FIVE_BUBBLES, 2.4, 2.3, 2000, stages.size(), RED, GREEN, BLUE, PURPLE,PINK,LIGHT_ORANGE, GOLD ));
                  stages.add(new Stage(30000, Stage.FIVE_BUBBLES, 2.4, 2.3, 2000, stages.size(), RED, GREEN, BLUE, PURPLE,PINK,LIGHT_ORANGE, YELLOW, GOLD));
                  stages.add(new Stage(50000, Stage.SIX_BUBBLES, 3, 2.6, 2500,stages.size(), RED, GREEN, BLUE, PURPLE,PINK,LIGHT_ORANGE,YELLOW, GOLD));
                  break;
              case hard:
                  stages.add(new Stage(1000, Stage.TWO_BUBBLES, 1.4, 1, 500, stages.size(), RED, GREEN, BLUE, PURPLE ));
                  stages.add(new Stage(6000, Stage.THREE_BUBBLES, 1.9, 1.2, 1000, stages.size(), RED, GREEN, BLUE, PURPLE, LIGHT_ORANGE, GOLD ));
                  stages.add(new Stage(10000, Stage.FOUR_BUBBLES, 2.5, 2, 1500, stages.size(), RED, GREEN, BLUE, PURPLE,PINK,LIGHT_ORANGE, GOLD ));
                  stages.add(new Stage(14000, Stage.FOUR_BUBBLES, 2.5, 2, 1750, stages.size(), RED, GREEN, BLUE, PURPLE,PINK,LIGHT_ORANGE, GOLD ));
                  stages.add(new Stage(25000, Stage.FIVE_BUBBLES, 4, 2.4, 1750, stages.size(), RED, GREEN, BLUE, PURPLE,PINK,LIGHT_ORANGE, GOLD ));
                  stages.add(new Stage(30000, Stage.FIVE_BUBBLES, 4, 2.4, 1750, stages.size(), RED, GREEN, BLUE, PURPLE,PINK,LIGHT_ORANGE, YELLOW, GOLD));
                  stages.add(new Stage(50000, Stage.SIX_BUBBLES, 5, 2.8, 2200,stages.size(), RED, GREEN, BLUE, PURPLE,PINK,LIGHT_ORANGE,YELLOW, GOLD));
                  break;    */
        }

    }

    public ArrayList<Stage> getStages() {
        return stages;
    }
}
