package de.bubbling.game.views.messages;

import de.bubbling.game.entities.Entity;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Andreas
 * Date: 29.07.13
 * Time: 15:35
 * To change this template use File | Settings | File Templates.
 */
public class GameViewUpdate implements MessageID {
    private CopyOnWriteArrayList<Entity> bubbles;
    private boolean clearMarks;

    public CopyOnWriteArrayList<Entity> getEntities() {
        return bubbles;
    }

    public GameViewUpdate(CopyOnWriteArrayList<Entity> bubbles, boolean clearMarks) {
        this.bubbles = bubbles;
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
