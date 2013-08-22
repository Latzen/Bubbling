package de.bubbling.game.difficulty;

/**
 * Created with IntelliJ IDEA.
 * User: Andreas
 * Date: 29.07.13
 * Time: 13:06
 * To change this template use File | Settings | File Templates.
 */
public class DifficultyProperties {

    public enum Difficulty {
        easy,
        normal,
        hard
    }

    public static String[] difficulties = {"easy", "normal", "hard"};

    private int hearts, chanceForNewHeart;
    private double pointsPerCombination;
    private Difficulty difficulty;

    public DifficultyProperties(Difficulty difficulty) {
        switch (difficulty) {
            case easy:
                this.hearts = 3;
                this.chanceForNewHeart = 5;
                this.pointsPerCombination = 100;
                break;
            case normal:
                this.hearts = 2;
                this.chanceForNewHeart = 3;
                this.pointsPerCombination = 300;
                break;
            case hard:
                this.hearts = 1;
                this.pointsPerCombination = 200;
                break;
        }
        this.difficulty = difficulty;
    }

    public double getPointsPerCombination() {
        return pointsPerCombination;
    }

    public static Difficulty matchWithString(String diff) {
        if (diff.equals(difficulties[0]))
            return Difficulty.easy;
        if (diff.equals(difficulties[1]))
            return Difficulty.normal;

        return Difficulty.hard;
    }

    public int getChanceForNewHeart() {
        return chanceForNewHeart;
    }

    public int getHearts() {
        return hearts;
    }
}
