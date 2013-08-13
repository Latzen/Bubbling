package de.bubbling.game.views.messages;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Andreas
 * Date: 03.08.13
 * Time: 19:15
 * To change this template use File | Settings | File Templates.
 */
public class InformationViewNextCombination {

    private ArrayList<Integer> combination;

    public InformationViewNextCombination(ArrayList<Integer> combination) {
        this.combination = combination;
    }

    public ArrayList<Integer> getCombination() {
        return combination;
    }
}
