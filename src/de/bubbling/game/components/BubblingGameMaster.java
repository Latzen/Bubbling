package de.bubbling.game.components;

import android.graphics.Rect;
import android.os.Debug;
import android.util.Log;
import de.bubbling.game.difficulty.DifficultyProperties;
import de.bubbling.game.entities.Bubble;
import de.bubbling.game.entities.BubbleTriangle;
import de.bubbling.game.entities.Entity;
import de.bubbling.game.entities.Rectangle;
import de.bubbling.game.views.messages.*;


import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Andreas
 * Date: 26.07.13
 * Time: 15:33
 * To change this template use File | Settings | File Templates.
 */
public class BubblingGameMaster implements IGameMaster, Runnable {

    public static int BUBBLE_RAD_DEVISOR = 7;
    private int lives;
    private double countDown;
    private int score, perfectStrokes, strokes, perfectInRowBest, perfectInRow;
    private long timeCombinationGen, timePlayed;
    private CopyOnWriteArrayList<Entity> entities;

    private Stage currentStage;
    private ArrayList<Stage> stages;
    private DifficultyProperties difficultyProperties;
    //private ArrayList<Integer> activeCombination;
    private CopyOnWriteArrayList<ActiveCombinationContainer> activeCombinationContainer;
    private Thread timer;
    private int gameWidth, gameHeight, bubbleRad;
    boolean combinationActive, stopGame;
    private LevelDesigner levelDesigner;

    public BubblingGameMaster(DifficultyProperties.Difficulty difficulty, int gameWidth, int gameHeight) {
        this.bubbleRad = gameHeight / BUBBLE_RAD_DEVISOR;
        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;
        this.difficultyProperties = new DifficultyProperties(difficulty);
        this.countDown = 60;
        this.lives = difficultyProperties.getHearts();

        initializeStages(difficulty);
        currentStage = stages.get(0);
        entities = new CopyOnWriteArrayList<Entity>();
       // activeCombination = new ArrayList<Integer>();
        activeCombinationContainer = new CopyOnWriteArrayList<ActiveCombinationContainer>();
        SceneController.sInstance.updateObservers(new InformationViewUpdate(countDown, score, lives));
        SceneController.sInstance.changeScene(levelDesigner.getCurrentLevel().getScene());
        SceneController.sInstance.updateObservers(levelDesigner.getCurrentLevel().getScene());
        timer = new Thread(new Runnable() {
            @Override
            public void run() {
                double second = 0;
                while (!checkLostCondition() && !stopGame) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    countDown -= 0.1;
                    second += 0.1;
                    if (second >= 1) {
                        second = 0;
                        timePlayed++;
                    }
                    SceneController.sInstance.updateObservers(new InformationViewUpdate(countDown, score, lives));
                }
            }
        });
    }

    private void initializeStages(DifficultyProperties.Difficulty difficulty) {
        levelDesigner = new LevelDesigner(difficulty);
        stages = levelDesigner.getNextLevel().getStages();
    }

    @Override
    public void run() {
        timer.start();
        while (!checkLostCondition() && !stopGame) {
            if (!combinationActive) {
                playNewRound();
                combinationActive = true;
            }
            boolean cleared = true;
            for (int i = 0; i < activeCombinationContainer.size(); i++) {
                if (cleared) {
                    cleared = false;
                    for (Entity b : entities) {
                        if (b.isMarked() && b.getNumberHit() == i &&
                                b.getColor() == activeCombinationContainer.get(i).getColor() &&
                                b.getType() == activeCombinationContainer.get(i).getType()) {
                            cleared = true;
                            break;
                        }
                    }
                }
            }

            if (cleared) {
                combinationActive = false;
                StrokeUpdate.StrokeType type = StrokeUpdate.StrokeType.Normal;
                double pointsGained;
                double timeGained;
                long neededTimeToSolve = System.currentTimeMillis() - timeCombinationGen;
                if (neededTimeToSolve < currentStage.getTimeForPerfectStroke()) {
                    pointsGained = difficultyProperties.getPointsPerCombination() * currentStage.getPointsFactor() * 1.5;
                    if (perfectInRow > 2) {
                        pointsGained += pointsGained * perfectInRow / 4;
                    }
                    timeGained = currentStage.getTimePerPerfectStroke();
                    type = StrokeUpdate.StrokeType.Perfect;
                    perfectStrokes++;
                    perfectInRow++;
                } else if (neededTimeToSolve < currentStage.getTimeForPerfectStroke() * 1.5) {
                    pointsGained = difficultyProperties.getPointsPerCombination() * currentStage.getPointsFactor();
                    timeGained = currentStage.getTimePerPerfectStroke() / 2;
                    type = StrokeUpdate.StrokeType.Good;
                    perfectInRow = 0;
                } else {
                    pointsGained = difficultyProperties.getPointsPerCombination() * currentStage.getPointsFactor() / 2;
                    timeGained = 0;
                    perfectInRow = 0;
                }

                //statistics
                if (perfectInRow > perfectInRowBest)
                    perfectInRowBest = perfectInRow;
                strokes++;

                score += pointsGained;
                countDown += timeGained;
                SceneController.sInstance.updateObservers(new StrokeUpdate((int) pointsGained, timeGained, type, perfectInRow));

            }
            //all marked but false Combination
            if (combinationActive) {
                boolean allMarked = true;
                for (Entity b : entities) {
                    if (!b.isMarked())
                        allMarked = false;
                }

                if (allMarked) {
                    lives -= 1;
                    countDown -= 5;
                    SceneController.sInstance.updateObservers(new GainedTimeUpdate(-5));
                    for (Entity b : entities) {
                        b.resetHitNumber();
                        b.resetMarked();
                    }
                    //reset Marks
                    SceneController.sInstance.updateObservers(new GameViewUpdate(entities, true));
                }
            }
            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Log.d("Bubbling",e.toString());
            }
        }
        if (!stopGame)
            SceneController.sInstance.lostGame(
                    new GameProgress(score, timePlayed, perfectStrokes, strokes, perfectInRowBest, currentStage.getId()));
        //End + Highscore
    }

    @Override
    public boolean checkLostCondition() {
        return lives < 0 || countDown <= 0;
    }

    public void stopGame() {
        stopGame = true;
    }

    @Override
    public void playNewRound() {
        checkCurrentStage();
        generateCombination();
        Log.d("Bubbling", "playing new Round");
        Log.d("Bubbling", "Level = "+currentStage.getId());
        SceneController.sInstance.updateObservers(new InformationViewNextCombination(activeCombinationContainer));
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.d("Bubbling",e.toString());
        }
        SceneController.sInstance.updateObservers(new GameViewUpdate(entities, true));
        timeCombinationGen = System.currentTimeMillis();


    }

    private void checkCurrentStage() {
        if (currentStage.getId()+1< stages.size()) {
            if (score > currentStage.getPointLimit()){
                currentStage = stages.get(currentStage.getId()+1);
            }
        }else if(!levelDesigner.isLastStage()&&score>currentStage.getPointLimit()){
            stages = levelDesigner.getNextLevel().getStages();
            if(levelDesigner.getCurrentLevel().getScene().getName().equals(LevelDesigner.LEVEL_SUDDENDEATH)){
                lives = 0;
            }
            currentStage = stages.get(0);
            SceneController.sInstance.changeScene(levelDesigner.getCurrentLevel().getScene());
            SceneController.sInstance.updateObservers(new GainedTimeUpdate(5));
            countDown+=5;
        }

    }

    private void generateCombination() {
        entities.clear();
        activeCombinationContainer.clear();
        for (int i = 0; i < currentStage.getCombination(); i++) {
            Level.UsedTypes type = levelDesigner.getCurrentLevel().getUsedType();
            int x = (int) (Math.random() * (gameWidth - bubbleRad) + 1);
            int y = (int) (Math.random() * (gameHeight - bubbleRad) + 1);
            while (!checkForFreePlace(x, y)) {
                x = (int) (Math.random() * (gameWidth - bubbleRad) + 1);
                y = (int) (Math.random() * (gameHeight - bubbleRad) + 1);
            }
            int randomColor = (int) (Math.random() * currentStage.getPossibleColors().length);
            if(type == Level.UsedTypes.TriangleBubbles || type == Level.UsedTypes.TriangleBubblesRectangle){
                int random = 2;
                if(type == Level.UsedTypes.TriangleBubblesRectangle){
                    random = 3;
                }
                int typeRandom = (int) (Math.random()*random+1);

                switch (typeRandom){
                    case Entity.BUBBLE_TYPE :
                        type = Level.UsedTypes.Bubbles;
                        break;
                    case Entity.TRIANGLE_TYPE:
                        type = Level.UsedTypes.Triangle;
                        break;
                    case Entity.RECTANGLE_TYPE:
                        type = Level.UsedTypes.Rectangle;
                        break;
                }
            }

            switch (type){
                case Bubbles:
                    Bubble bubble;
                    bubble = new Bubble(x, y, currentStage.getPossibleColors()[randomColor], bubbleRad, true);
                    entities.add(bubble);
                    activeCombinationContainer.add
                            (new ActiveCombinationContainer
                                    (currentStage.getPossibleColors()[randomColor],  Entity.BUBBLE_TYPE)) ;
                    break;
                case Triangle:
                    BubbleTriangle triangle =
                            new BubbleTriangle(x, y, bubbleRad,bubbleRad,currentStage.getPossibleColors()[randomColor], true);
                    entities.add(triangle);
                    activeCombinationContainer.add
                            (new ActiveCombinationContainer
                                    (currentStage.getPossibleColors()[randomColor], Entity.TRIANGLE_TYPE)) ;
                    break;
                case Rectangle:
                    Rectangle rect =
                            new Rectangle(x, y, bubbleRad,bubbleRad,currentStage.getPossibleColors()[randomColor], true);
                    entities.add(rect);
                    activeCombinationContainer.add
                            (new ActiveCombinationContainer
                                    (currentStage.getPossibleColors()[randomColor], Entity.RECTANGLE_TYPE)) ;
                    break;
            }
        }
    }

    private boolean checkForFreePlace(int x, int y) {
        Rect toCheck = new Rect(x, y, x + bubbleRad, y + bubbleRad);

        for (Entity b : entities) {
            Rect checkRec = new Rect(b.getX(), b.getY(), b.getX() + bubbleRad, b.getY() + bubbleRad);
            if (toCheck.intersect(checkRec)) {
                return false;
            }
        }
        return true;
    }
}
