package de.bubbling.game.entities;

import android.graphics.*;
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
    public BubbleTriangle(int x, int y, int width, int height, int color, boolean visible) {
        super(x, y, width, height, color, visible);
        type = Entity.TRIANGLE_TYPE;
        point3_draw = new Point();
        point2_draw = new Point();
        point1_draw = new Point();

        point1_draw.set(getX()+getWidth()/2, y);
        point2_draw.set(getX()+getWidth(),y+getHeight());
        point3_draw.set(getX(), y+getHeight());
        path = new Path();
        path.setFillType(Path.FillType.EVEN_ODD);
        path.moveTo(point1_draw.x,point1_draw.y);
        path.lineTo(point2_draw.x,point2_draw.y);
        path.lineTo(point3_draw.x,point3_draw.y);
        path.close();
    }

    @Override
    public void draw(Canvas canvas) {
        int textSize = getWidth() / 3;
        if (textSize < 5) return;
        //if (point1_draw.x <point3_draw.x) return;
        canvas.drawPath(path, paint);
        if(isMarked()){
            Paint markedPain = new Paint(Paint.ANTI_ALIAS_FLAG);
            markedPain.setColor(getPressedColor());
            markedPain.setStyle(Paint.Style.STROKE);
            markedPain.setStrokeWidth(width/20);
            Path pathMarked = new Path();
            pathMarked.setFillType(Path.FillType.EVEN_ODD);
            pathMarked.moveTo(point1_draw.x,point1_draw.y);
            pathMarked.lineTo(point2_draw.x,point2_draw.y);
            pathMarked.lineTo(point3_draw.x,point3_draw.y);
            pathMarked.lineTo(point1_draw.x, point1_draw.y);
            pathMarked.close();
            canvas.drawPath(pathMarked, markedPain);
            //int textSize = getWidth() / 3;
           // if (textSize < 5) return;
            paintNumber.setTextSize(textSize);
            canvas.drawText
                    (Integer.toString(numberHit + 1), getX() +  getWidth()  / 2, getY() +  getWidth()  - textSize / 2, paintNumber);
        }

    }
    @Override
    public void collapseAnimation(int velocity) {
        // marked = false;
        x = x + velocity / 2;
        y = y + velocity / 2;
        width = width - velocity;
        height = height - velocity;

        point1_draw.set(getX()+getWidth()/2, y);
        point2_draw.set(getX()+getWidth(),y+getHeight());
        point3_draw.set(getX(), y+getHeight());

        path = new Path();
        path.setFillType(Path.FillType.EVEN_ODD);
        path.moveTo(point1_draw.x,point1_draw.y);
        path.lineTo(point2_draw.x,point2_draw.y);
        path.lineTo(point3_draw.x,point3_draw.y);
        path.close();
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
