package de.bubbling.game.components;

import android.graphics.Color;
import android.graphics.Rect;
import de.bubbling.game.difficulty.DifficultyProperties;
import de.bubbling.game.entities.Bubble;
import de.bubbling.game.views.messages.GameViewUpdate;
import de.bubbling.game.views.messages.InformationViewNextCombination;
import de.bubbling.game.views.messages.InformationViewUpdate;
import de.bubbling.game.views.messages.StrokeUpdate;


import java.util.ArrayList;

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
    private ArrayList<Bubble> bubbles;

    private Stage currentStage;
    private ArrayList<Stage> stages;
    private DifficultyProperties difficultyProperties;
    private ArrayList<Integer> activeCombination;
    private Thread timer;
    private int gameWidth, gameHeight, bubbleRad;
    boolean combinationActive, stopGame;
    private LevelDesigner levelDesigner;


    public BubblingGameMaster(DifficultyProperties.Difficulty difficulty, int gameWidth, int gameHeight){
        this.bubbleRad = gameHeight/BUBBLE_RAD_DEVISOR;
        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;
        this.difficultyProperties = new DifficultyProperties(difficulty);
        this.countDown = 60;
        this.lives = difficultyProperties.getHearts();

        initializeStages(difficulty);
        currentStage = stages.get(0);
        bubbles = new ArrayList<Bubble>();
        activeCombination = new ArrayList<Integer>();
        SceneController.sInstance.updateObservers(new InformationViewUpdate(countDown, score, lives));

        timer = new Thread(new Runnable() {
            @Override
            public void run() {
                double second = 0;
                while (!checkLostCondition()&&!stopGame){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    countDown -= 0.1;
                    second += 0.1;
                    if (second>=1){
                        second = 0;
                        timePlayed++;
                    }
                    SceneController.sInstance.updateObservers(new InformationViewUpdate(countDown, score, lives));
                }
            }
        });
    }

    private void initializeStages(DifficultyProperties.Difficulty difficulty){
        levelDesigner = new LevelDesigner(difficulty);
        stages = levelDesigner.getStages();
    }

    @Override
    public void run() {
        timer.start();
        while(!checkLostCondition()&&!stopGame){
            if(!combinationActive){
                playNewRound();
                combinationActive = true;
            }
            boolean cleared = true;
            for(int i = 0; i<activeCombination.size(); i++){
                if(cleared){
                    cleared = false;
                    for (Bubble b : bubbles){
                        if(b.marked && b.getNumberHit()== i && b.getColor()==activeCombination.get(i)){
                            cleared = true;
                            break;
                        }
                    }
                }
            }

            if(cleared){
                combinationActive = false;
                StrokeUpdate.StrokeType type = StrokeUpdate.StrokeType.Normal;
                double pointsGained;
                double timeGained;
                long neededTimeToSolve = System.currentTimeMillis()-timeCombinationGen;
                if(neededTimeToSolve<currentStage.getTimeForPerfectStroke()) {
                    pointsGained =  difficultyProperties.getPointsPerCombination()*currentStage.getPointsFactor()*1.5;
                    if(perfectInRow>2){
                        pointsGained += pointsGained*perfectInRow/5;
                    }
                    timeGained = currentStage.getTimePerPerfectStroke();
                    type = StrokeUpdate.StrokeType.Perfect;
                    perfectStrokes++;
                    perfectInRow++;
                }else if(neededTimeToSolve<currentStage.getTimeForPerfectStroke()*1.5){
                    pointsGained = difficultyProperties.getPointsPerCombination()*currentStage.getPointsFactor();
                    timeGained = currentStage.getTimePerPerfectStroke()/2;
                    type = StrokeUpdate.StrokeType.Good;
                    perfectInRow = 0;
                } else{
                    pointsGained = difficultyProperties.getPointsPerCombination()*currentStage.getPointsFactor()/2;
                    timeGained = 0;
                    perfectInRow = 0;
                }

                //statistics
                if(perfectInRow>perfectInRowBest)
                    perfectInRowBest = perfectInRow ;
                strokes++;

                score +=  pointsGained;
                countDown += timeGained;
                SceneController.sInstance.updateObservers(new StrokeUpdate((int)pointsGained, timeGained, type, perfectInRow));

            }
            //all marked but false Combination
            if(combinationActive){
                boolean allMarked = true;
                for (Bubble b : bubbles){
                    if(!b.marked)
                        allMarked = false;
                }

                if(allMarked){
                    lives-=1;
                    countDown -= 2;
                    for (Bubble b : bubbles){
                        b.marked = false;
                        b.resetHitNumber();
                    }
                    //reset Marks
                    SceneController.sInstance.updateObservers(new GameViewUpdate(bubbles, true));
                }
            }
            try {
                Thread.sleep(25);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            SceneController.sInstance.updateObservers(new InformationViewUpdate(countDown, score, lives));
        }
        if(!stopGame)
            SceneController.sInstance.lostGame(
                    new GameProgress(score,timePlayed,perfectStrokes, strokes, perfectInRowBest, currentStage.getId()));
        //End + Highscore
    }

    @Override
    public boolean checkLostCondition() {
        return lives < 0 || countDown <= 0;
    }

    public void stopGame(){
        stopGame = true;
    }

    @Override
    public void playNewRound() {
        checkCurrentStage();
        generateCombination();
        SceneController.sInstance.updateObservers(new InformationViewNextCombination(activeCombination));
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        SceneController.sInstance.updateObservers(new GameViewUpdate(bubbles, true));
        timeCombinationGen = System.currentTimeMillis();

    }

    private void checkCurrentStage(){
        if(currentStage.getId()+1<stages.size()&&score>currentStage.getPointLimit()){
             currentStage = stages.get(currentStage.getId()+1);
        }
    }
    private void generateCombination(){
        bubbles.clear();
        activeCombination.clear();
        for(int i = 0; i< currentStage.getCombination(); i++){
           int x = (int) (Math.random()*(gameWidth- bubbleRad)+1);
           int y = (int) (Math.random()*(gameHeight-bubbleRad)+1);
           while (!checkForFreePlace(x,y)){
               x = (int) (Math.random()*(gameWidth- bubbleRad)+1);
               y = (int) (Math.random()*(gameHeight-bubbleRad)+1);
           }
           int randomColor = (int) (Math.random()*currentStage.getPossibleColors().length);
           Bubble bubble;
           bubble =  new Bubble(x,y, currentStage.getPossibleColors()[randomColor], bubbleRad,true);
           bubbles.add(bubble);
           activeCombination.add(currentStage.getPossibleColors()[randomColor]);
        }
    }

    private boolean checkForFreePlace(int x, int y){
        Rect toCheck = new Rect(x,y,x+ bubbleRad,y+ bubbleRad);

        for (Bubble b : bubbles){
            Rect checkRec = new Rect(b.getX(),b.getY(),b.getX()+ bubbleRad, b.getY()+ bubbleRad);
            if(toCheck.intersect(checkRec)){;
                return false;
            }
        }
        return true;
    }
}
