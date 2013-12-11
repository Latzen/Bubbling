package de.bubbling.game.components;

import android.graphics.Canvas;
import de.bubbling.game.entities.*;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;


/**
 * Created with IntelliJ IDEA.
 * User: Andreas
 * Date: 11.12.13
 * Time: 15:37
 * To change this template use File | Settings | File Templates.
 */
public class DrawingQueue {

    private CopyOnWriteArrayList<DrawObject> elements;

    public DrawingQueue(){
        elements = new CopyOnWriteArrayList<DrawObject>();
    }

    public void drawElements(Canvas c){

        CopyOnWriteArrayList<DrawObject> dummyList = new CopyOnWriteArrayList<DrawObject>();
        for(DrawObject draw : elements){
           if(draw.getState() != EnumDrawingState.STATE_DISMISS){
               draw.draw(c);
           }
            else{
               elements.remove(draw);
           }
        }
    }

    public void clearQueue(){
        elements.clear();
    }

    public void addElement(DrawObject object){
        elements.add(object);
    }

    public void doSomeAnimationsBeforeDraw(){
        for(DrawObject draw : elements){
            draw.doAnimationBeforeDraw();
        }
    }

    public void collapseAnimation(){
        for(DrawObject draw : elements){
            if(!(draw instanceof TextAnimation)) {
                draw.setState(EnumDrawingState.STATE_COLLAPSE);
            }
        }
    }
}
