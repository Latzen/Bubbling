package de.bubbling.game.views;

import android.content.Context;
import android.graphics.*;
import android.util.Log;
import android.view.SurfaceView;
import android.widget.LinearLayout;
import de.bubbling.game.activities.R;
import de.bubbling.game.components.ActiveCombinationContainer;
import de.bubbling.game.entities.Entity;
import de.bubbling.game.views.messages.InformationViewNextCombination;
import de.bubbling.game.views.messages.InformationViewTimeUpdate;
import de.bubbling.game.views.messages.InformationViewUpdate;

import java.text.DecimalFormat;
import java.util.ListIterator;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.CopyOnWriteArrayList;

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
    private long score, lives;
    private double countDown;
    private int textSizeDev4;
    private Paint linePainter, timePainter, scorePainter, simpleTextPainter, gainedPainter;
    private String timeGained;
    private CopyOnWriteArrayList<ActiveCombinationContainer> activeCombinationContainer;
    private Context context;
    private Bitmap heart;
    boolean update = false;
    private DecimalFormat df;


    Point point1_draw, point2_draw, point3_draw;
    Path path;

    private String sLives, sScore, sTime;

    public InformationView(Context context, int width, int height) {
        super(context);
        this.width = width;
        this.height = height / HEIGHT_DEVISOR;
        setLayoutParams(new LinearLayout.LayoutParams(width, this.height));
        setBackgroundColor(Color.WHITE);
        this.context = context;
        textSizeDev4 = this.height / 4;
        timeGained = "";
        initializePainter();
        sLives = context.getString(R.string.info_board_live);
        sScore = context.getString(R.string.info_board_score);
        sTime = context.getString(R.string.info_board_time);
        df = new DecimalFormat("##.#");
    }

    private void initializePainter() {
        //Text
        simpleTextPainter = new Paint();
        simpleTextPainter.setColor(Color.BLACK);
        simpleTextPainter.setTextSize(height / 5);

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
        gainedPainter.setColor(Color.rgb(0, 232, 0));
        gainedPainter.setFakeBoldText(true);
        gainedPainter.setTextSize(height / 6);

        heart = BitmapFactory.decodeResource(context.getResources(), R.drawable.live);
        int bitmapSize = height / 3;
        heart = Bitmap.createScaledBitmap(heart, bitmapSize, bitmapSize, true);

        //triangle
        point1_draw = new Point();
        point2_draw = new Point();
        point3_draw = new Point();

        path = new Path();

    }

    @Override
    public synchronized void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawRect(0, height - 2, width, height, linePainter);
        //Score
        simpleTextPainter.setTextAlign(Paint.Align.CENTER);
        scorePainter.setTextAlign(Paint.Align.CENTER);
        gainedPainter.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(sScore, width / 2, height / 5, simpleTextPainter);
        canvas.drawText(Long.toString(score), width / 2, height / 5 * 2, scorePainter);

        //time
        simpleTextPainter.setTextAlign(Paint.Align.RIGHT);
        gainedPainter.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText(sTime, width, height / 5, simpleTextPainter);
        if (countDown < 10) {
            timePainter.setColor(Color.RED);
        } else {
            timePainter.setColor(Color.BLACK);
        }
        canvas.drawText(toDoubleString(countDown), width, height / 5 * 2, timePainter);

        canvas.drawText(timeGained, width, height / 5 * 3, gainedPainter);

        //lives
        simpleTextPainter.setTextAlign(Paint.Align.LEFT);
        scorePainter.setTextAlign(Paint.Align.LEFT);
        canvas.drawText(sLives, 5, height / 5, simpleTextPainter);

        if (activeCombinationContainer != null) {
            redrawAfterUpdate(canvas);
        }

        int startX = 5;
        int startY = height / 5;
        for (int i = 0; i < lives; i++) {
            canvas.drawBitmap(heart, startX, startY, new Paint());
            startX += heart.getWidth();
        }
    }

    @Override
    public synchronized void update(Observable observable, Object data) {
        if (data instanceof InformationViewUpdate) {
            InformationViewUpdate update = (InformationViewUpdate) data;
            countDown = update.getCountDown();
            score = update.getPoints();
            lives = update.getLives();
            if (lives < 0) lives = 0;
        } else if (data instanceof InformationViewNextCombination) {
            InformationViewNextCombination update = (InformationViewNextCombination) data;
            this.update = true;
            if (activeCombinationContainer == null) {
                activeCombinationContainer = new CopyOnWriteArrayList<ActiveCombinationContainer>();
            }
            /*activeCombinationContainer.clear();
            for(ActiveCombinationContainer ac : update.getContainers()){
                activeCombinationContainer.add(ac);
            }*/
            activeCombinationContainer = update.getContainers();

        } else if (data instanceof InformationViewTimeUpdate) {
            showPerfectStrokeText(((InformationViewTimeUpdate) data).getTimeGained());
        }
        postInvalidate();
    }

    private synchronized void showPerfectStrokeText(final double time) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String s = "+";
                if (time < 0) {
                    gainedPainter.setColor(Color.RED);
                    s = "";
                } else {
                    gainedPainter.setColor(Color.rgb(0, 232, 0));
                }
                timeGained = s + toDoubleString(time);
                postInvalidate();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Log.d("Bubbling", e.toString());
                }
                timeGained = "";
                postInvalidate();
            }
        }).start();
    }

    private String toDoubleString(double d) {
        String buffer = df.format(d);
        if (d >= 10) {
            if (buffer.length() <= 2)
                buffer += ",0";
        } else {
            if (buffer.length() <= 1)
                buffer += ",0";
        }

        if (d <= 0 && d > -1) {
            buffer = "0,0";
        }
        return buffer;

    }

    private void redrawAfterUpdate(Canvas canvas) {
        int bubbleRad = textSizeDev4;
        int begrenzungsBalken = 2;
        int x = width / 2 - (bubbleRad * (activeCombinationContainer.size() - 1));
        int y = height - textSizeDev4 - begrenzungsBalken;
        ListIterator<ActiveCombinationContainer> iterator = activeCombinationContainer.listIterator();

        while (iterator.hasNext()) {
            ActiveCombinationContainer ac = iterator.next();
            Paint p = new Paint();
            p.setColor(ac.getColor());
            p.setAntiAlias(true);
            switch (ac.getType()) {
                case Entity.BUBBLE_TYPE:
                    canvas.drawCircle(x, y, bubbleRad, p);
                    break;
                case Entity.TRIANGLE_TYPE:
                    point1_draw.set(x, y - bubbleRad);
                    point2_draw.set(x + bubbleRad, y + bubbleRad);
                    point3_draw.set(x - bubbleRad, y + bubbleRad);

                    path.rewind();
                    path.setFillType(Path.FillType.EVEN_ODD);
                    path.moveTo(point1_draw.x, point1_draw.y);
                    path.lineTo(point2_draw.x, point2_draw.y);
                    path.lineTo(point3_draw.x, point3_draw.y);
                    path.close();
                    p.setStyle(Paint.Style.FILL);
                    canvas.drawPath(path, p);
                    break;
                case Entity.RECTANGLE_TYPE:
                    canvas.drawRect(x - bubbleRad + 2, y - bubbleRad, x + bubbleRad - 2, y + bubbleRad, p);
                    break;
            }
            x += height / 2;
        }
    }

}
