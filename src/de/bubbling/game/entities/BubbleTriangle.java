package de.bubbling.game.entities;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import de.bubbling.game.views.messages.NearHitInformation;

/**
 * Created with IntelliJ IDEA.
 * User: Andreas
 * Date: 17.08.13
 * Time: 12:05
 * To change this template use File | Settings | File Templates.
 */
public class BubbleTriangle extends Entity implements ITile {
    Point point1_draw;
    Point point2_draw;
    Point point3_draw;


    Path path;
    Paint markedPain;
    Path pathMarked;
    boolean collapse;

    public BubbleTriangle(int x, int y, int width, int height, int color, boolean visible) {
        super(x, y, width, height, color, visible);
        type = Entity.TRIANGLE_TYPE;
        point3_draw = new Point();
        point2_draw = new Point();
        point1_draw = new Point();

        point1_draw.set(getX() + getWidth() / 2, y);
        point2_draw.set(getX() + getWidth(), y + getHeight());
        point3_draw.set(getX(), y + getHeight());

        path = new Path();
        path.setFillType(Path.FillType.EVEN_ODD);
        path.moveTo(point1_draw.x, point1_draw.y);
        path.lineTo(point2_draw.x, point2_draw.y);
        path.lineTo(point3_draw.x, point3_draw.y);
        path.close();

        markedPain = new Paint(Paint.ANTI_ALIAS_FLAG);
        markedPain.setColor(getPressedColor());
        markedPain.setStyle(Paint.Style.STROKE);
        markedPain.setStrokeWidth(width / 20);

        pathMarked = new Path();
        pathMarked.setFillType(Path.FillType.EVEN_ODD);
        pathMarked.moveTo(point1_draw.x, point1_draw.y);
        pathMarked.lineTo(point2_draw.x, point2_draw.y);
        pathMarked.lineTo(point3_draw.x, point3_draw.y);
        //pathMarked.lineTo(point1_draw.x, point1_draw.y);
        pathMarked.close();
    }

    @Override
    public void draw(Canvas canvas) {

        drawing = true;
        int textSize = getWidth() / 3;


        if ((textSize < 10 && collapse) || collapse) {
            currentState = EnumDrawingState.STATE_DISMISS;
            return;
        }

        canvas.drawPath(path, paint);

        if (isMarked()) {
            canvas.drawPath(pathMarked, markedPain);
            paintNumber.setTextSize(textSize);
            canvas.drawText
                    (Integer.toString(numberHit + 1), getX() + getWidth() / 2, getY() + getWidth() - textSize / 2, paintNumber);
        }

        drawing = false;


    }

    @Override
    public void collapseAnimation(int velocity) {
        collapse = true;
        /*
        if(drawing) return;
        calculating = true;
        collapse = true;
        //marked = false;
        x = x + velocity / 2;
        y = y + velocity / 2;
        width = width - velocity;
        height = height - velocity;

        point1_draw.set(getX()+getWidth()/2, y);
        point2_draw.set(getX()+getWidth(),y+getHeight());
        point3_draw.set(getX(), y+getHeight());

        path.rewind();
        path.moveTo(point1_draw.x,point1_draw.y);
        path.lineTo(point2_draw.x,point2_draw.y);
        path.lineTo(point3_draw.x,point3_draw.y);
        path.close();

        pathMarked.rewind();
        pathMarked.moveTo(point1_draw.x,point1_draw.y);
        pathMarked.lineTo(point2_draw.x,point2_draw.y);
        pathMarked.lineTo(point3_draw.x,point3_draw.y);
        pathMarked.close();

        calculating = false;         */
    }

    @Override
    public NearHitInformation checkNearHit(float x, float y, int gameViewTop, int maximum) {
        if (getX() - maximum <= x && getX() + width + maximum >= x) {
            if (getY() + gameViewTop - maximum <= y && getY() + gameViewTop + width + maximum >= y) {
                return new NearHitInformation(getX() + width - x, getY() + width - y, this, NearHitInformation.Hit.Hitted);
            }
        }
        return new NearHitInformation(getX() + width - x, getY() + width - y, this, NearHitInformation.Hit.NoHit);
    }
}
