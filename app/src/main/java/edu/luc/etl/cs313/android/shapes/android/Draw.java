package edu.luc.etl.cs313.android.shapes.android;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import edu.luc.etl.cs313.android.shapes.model.*;

import java.util.List;

/**
 * A Visitor for drawing a shape to an Android canvas.
 */
public class Draw implements Visitor<Void> {

    private final Canvas canvas;

    private final Paint paint;

    public Draw(final Canvas canvas, final Paint paint) {
        this.canvas = canvas;
        this.paint = paint;
        paint.setStyle(Style.STROKE);
    }

    @Override
    public Void onCircle(final Circle c) {
        canvas.drawCircle(0, 0, c.getRadius(), paint);
        return null;
    }

    @Override
    public Void onStrokeColor(final StrokeColor c) {
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setColor(c.getColor());
        c.getShape().accept(this);
        return null;
    }

    @Override
    public Void onFill(final Fill f) {
        if (paint.getStyle() != Paint.Style.FILL_AND_STROKE) {
            paint.setStyle(Paint.Style.FILL);
        }
        f.getShape().accept(this);
        return null;
    }

    @Override
    public Void onGroup(final Group g) {
        for (Shape shape: g.getShapes()) {
            shape.accept(this);
        }
        return null;
    }

    @Override
    public Void onLocation(final Location l) {
        canvas.translate(l.getX(), l.getY());
        l.getShape().accept(this);
        canvas.translate(-l.getX(), -l.getY());
        return null;
    }

    @Override
    public Void onRectangle(final Rectangle r) {
        canvas.drawRect(0, 0, r.getWidth(), r.getHeight(), paint);
        return null;
    }

    @Override
    public Void onOutline(Outline o) {
        paint.setStyle(Paint.Style.STROKE);
        o.getShape().accept(this);
        return null;
    }

    @Override
    public Void onPolygon(final Polygon s) {
        final List<? extends Point> points = s.getPoints();
        final int numPoints = points.size();
        if (numPoints < 2) {
            return null; //If polygon has less than two points, it's not really a polygon
        }
        final float[] pts = new float[numPoints * 4];
        int index = 0;

        for (int i =0; i < numPoints; i++) {
            Point currentPoint = points.get(i);
            pts[index++] = currentPoint.getX();
            pts[index++] = currentPoint.getY();
            Point nextPoint = points.get((i + 1) % numPoints);
            pts[index++] = nextPoint.getX();
            pts[index++] = nextPoint.getY();
        }

        canvas.drawLines(pts, paint);
        return null;
    }
}
