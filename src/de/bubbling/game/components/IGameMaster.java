package de.bubbling.game.components;

/**
 * Created with IntelliJ IDEA.
 * User: Andreas
 * Date: 26.07.13
 * Time: 15:32
 * To change this template use File | Settings | File Templates.
 */
public interface IGameMaster {

    boolean checkLostCondition();

    void playNewRound();
}
