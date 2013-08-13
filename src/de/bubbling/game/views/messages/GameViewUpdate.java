package de.bubbling.game.views.messages;

import de.bubbling.game.entities.Bubble;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Andreas
 * Date: 29.07.13
 * Time: 15:35
 * To change this template use File | Settings | File Templates.
 */
public class GameViewUpdate {
    private ArrayList<Bubble> bubbles;
    private boolean clearMarks;
    public ArrayList<Bubble> getBubbles() {
        return bubbles;
    }

    public GameViewUpdate(ArrayList<Bubble> bubbles, boolean clearMarks) {
        if(bubbles!= this.bubbles)
         this.bubbles = (ArrayList<Bubble>) bubbles.clone();
        this.clearMarks = clearMarks;

    }
    public boolean isClearMarks() {
        return clearMarks;
    }
}
