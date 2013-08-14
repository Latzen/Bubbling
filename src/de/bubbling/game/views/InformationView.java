package de.bubbling.game.views;

import android.content.Context;
import android.graphics.*;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import de.bubbling.game.activities.R;
import de.bubbling.game.views.messages.InformationViewNextCombination;
import de.bubbling.game.views.messages.InformationViewUpdate;
import de.bubbling.game.views.messages.StrokeUpdate;

import java.text.DecimalFormat;
import java.util.Observable;
import java.util.Observer;

/**
 * Created with IntelliJ IDEA.
 * User: Andreas
 * Date: 28.07.13
 * Time: 10:47
 * To change this template use File | Settings | File Templates.
 */
public class InformationView extends SurfaceView implements Observer {


    private static int HEIGHT_DEVISOR = 6;

    private int width, height;
    private int score, lives;
    private double countDown;
    private int textSizeDev4;
    Paint linePainter,timePainter, scorePainter, simpleTextPainter, gainedPainter;
    String  timeGained ;
    private int [] activeCombination;
    private Context context;
    Bitmap heart;
    DecimalFormat df;
    public InformationView(Context context, int width, int height) {
        super(context);
        this.width = width;
        this.height = height/HEIGHT_DEVISOR;
        setLayoutParams(new LinearLayout.LayoutParams(width, this.height));
        setBackgroundColor(Color.WHITE);
        this.context = context;
        textSizeDev4 = this.height/4;
        timeGained = "";
        initializePainter();

        df = new DecimalFormat("##.#");
    }

    private void initializePainter(){
        //Text
        simpleTextPainter = new Paint();
        simpleTextPainter.setColor(Color.BLACK);
        simpleTextPainter.setTextSize(height/5);

        //Border
        linePainter = new Paint();
        linePainter.setColor(Color.GRAY);

        //Time
        timePainter = new Paint();
        timePainter.setColor(Color.BLACK);
        timePainter.setStyle(Paint.Style.FILL);
        timePainter.setTextSize(textSizeDev4);
        timePainter.setFakeBoldText(true);
        timePainter.setTextAlign(Paint.Align.RIGHT);

        //Score
        scorePainter = new Paint();
        scorePainter.setColor(Color.BLACK);
        scorePainter.setTextSize(textSizeDev4);

        gainedPainter = new Paint();
        gainedPainter.setColor( Color.rgb(0,232,0));
        gainedPainter.setFakeBoldText(true);
        gainedPainter.setTextSize(height/6);

        heart = BitmapFactory.decodeResource(context.getResources(), R.drawable.live);
        int bitmapSize = height/3;
        heart = Bitmap.createScaledBitmap(heart, bitmapSize, bitmapSize, true);

    }
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawRect(0, height - 2, width, height, linePainter);

        //Score
        simpleTextPainter.setTextAlign(Paint.Align.CENTER);
        scorePainter.setTextAlign(Paint.Align.CENTER);
        gainedPainter.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("score:", width / 2, height/5, simpleTextPainter);
        canvas.drawText(""+score, width/2, height/5*2, scorePainter);

        //time
        simpleTextPainter.setTextAlign(Paint.Align.RIGHT);
        gainedPainter.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText("time:", width, height/5, simpleTextPainter);
        if(countDown <10){
            timePainter.setColor(Color.RED);
        } else{
            timePainter.setColor(Color.BLACK);
        }
        canvas.drawText(""+toDoubleString(countDown),width, height/5*2, timePainter);

        canvas.drawText(timeGained, width, height/5*3, gainedPainter);

        //lives
        simpleTextPainter.setTextAlign(Paint.Align.LEFT);
        scorePainter.setTextAlign(Paint.Align.LEFT);
        canvas.drawText("lives:", 5, height/5, simpleTextPainter);

        int startX = 5;
        int startY = height/5;
        for(int i = 0; i< lives;i++){
            canvas.drawBitmap(heart, startX, startY, new Paint());
            startX += heart.getWidth();
        }

        if(activeCombination != null){
            int bubbleRad = textSizeDev4;
            int x = width/2 - (bubbleRad*(activeCombination.length-1));
            int y = height-textSizeDev4;
            for(int i = 0; i<activeCombination.length;i++){
                Paint p = new Paint();
                p.setColor(activeCombination[i]);
                p.setAntiAlias(true);
                canvas.drawCircle(x,y,bubbleRad,p);
                x+=height/2;
            }
        }
    }

    @Override
    public void update(Observable observable, Object data) {
        if(data instanceof InformationViewUpdate){
            InformationViewUpdate update = (InformationViewUpdate) data;
            countDown = update.getCountDown();


            score = update.getPoints();
            lives = update.getLives();
            if(lives <0) lives = 0;
            postInvalidate();
        } else if(data instanceof InformationViewNextCombination){
            InformationViewNextCombination update = (InformationViewNextCombination) data;
            activeCombination = new int[update.getCombination().size()];
            for(int i = 0; i<activeCombination.length;i++){
                activeCombination[i] = update.getCombination().get(i);
            }
            postInvalidate();
        } else if(data instanceof StrokeUpdate){
            showPerfectStrokeText((StrokeUpdate)data);
        }
    }

    private synchronized void  showPerfectStrokeText(final StrokeUpdate stroke){
        new Thread(new Runnable() {
            @Override
            public void run() {
                timeGained = "+"+toDoubleString(stroke.getTimeGained());
                postInvalidate();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                timeGained = "";
                postInvalidate();
            }
        }).start();
    }

    private String toDoubleString(double d){
        String buffer = df.format(d);
        if(d>=10){
            if(buffer.length()<=2)
                buffer +=",0";
        }else{
            if(buffer.length()<=1)
                buffer +=",0";
        }

        if(d<=0){
            buffer = "0,0";
        }
        return buffer;

    }


}
