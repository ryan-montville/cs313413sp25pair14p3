package edu.luc.etl.cs313.android.shapes.android;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import edu.luc.etl.cs313.android.shapes.model.*;

/**
 * A Visitor for drawing a shape to an Android canvas.
 */
public class Draw implements Visitor<Void> {

    // TODO entirely your job (except onCircle)

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
        canvas.setColor(c);
        return null;
    }

    @Override
    public Void onFill(final Fill f) {
        canvas.setColor(f);
        return null;
    }

    @Override
    public Void onGroup(final Group g) {
        //Still neede to be finished
        return null;
    }

    @Override
    public Void onLocation(final Location l) {
        //waiting on BoundingBox to be completed to fiqure this one out
        return null;
    }

    @Override
    public Void onRectangle(final Rectangle r) {
        canvas.drawRect(0, 0, r.getWidth(), r.getHeight(), paint)
        return null;
    }

    @Override
    public Void onOutline(Outline o) {
        //still need to complete, waiting on Ouline model to be completed
        return null;
    }

    @Override
    public Void onPolygon(final Polygon s) {
        //not sure if more is needed here
        final float[] pts = null;

        canvas.drawLines(pts, paint);
        return null;
    }
}
