package de.bubbling.game.views.messages;

import de.bubbling.game.components.ActiveCombinationContainer;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Andreas
 * Date: 03.08.13
 * Time: 19:15
 * To change this template use File | Settings | File Templates.
 */
public class InformationViewNextCombination implements  MessageID {

    private CopyOnWriteArrayList<ActiveCombinationContainer> containers;

    public InformationViewNextCombination(CopyOnWriteArrayList<ActiveCombinationContainer> containers) {
        this.containers = containers;
    }

    public CopyOnWriteArrayList<ActiveCombinationContainer> getContainers() {
        return containers;
    }

    @Override
    public int getMessageID() {
        return MessageIDs.INFO_VIEW_NEXT_COMB;
    }
}
