package de.bubbling.game.views.messages;

import de.bubbling.game.entities.Bubble;
import de.bubbling.game.entities.Entity;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Andreas
 * Date: 29.07.13
 * Time: 15:35
 * To change this template use File | Settings | File Templates.
 */
public class GameViewUpdate implements MessageID {
    private ArrayList<Entity> bubbles;
    private boolean clearMarks;

    public ArrayList<Entity> getEntities() {
        return bubbles;
    }

    public GameViewUpdate(ArrayList<Entity> bubbles, boolean clearMarks) {
        this.bubbles = (ArrayList<Entity>) bubbles.clone();
        this.clearMarks = clearMarks;
    }

    public boolean isClearMarks() {
        return clearMarks;
    }

    @Override
    public int getMessageID() {
        return MessageIDs.GAMEVIEW_UPDATE;
    }
}
