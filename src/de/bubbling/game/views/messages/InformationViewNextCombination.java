package de.bubbling.game.views.messages;

import de.bubbling.game.components.ActiveCombinationContainer;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Andreas
 * Date: 03.08.13
 * Time: 19:15
 * To change this template use File | Settings | File Templates.
 */
public class InformationViewNextCombination implements  MessageID {

    private ArrayList<ActiveCombinationContainer> containers;

    public InformationViewNextCombination(ArrayList<ActiveCombinationContainer> containers) {
        this.containers = containers;
    }

    public ArrayList<ActiveCombinationContainer> getContainers() {
        return containers;
    }

    @Override
    public int getMessageID() {
        return MessageIDs.INFO_VIEW_NEXT_COMB;
    }
}
