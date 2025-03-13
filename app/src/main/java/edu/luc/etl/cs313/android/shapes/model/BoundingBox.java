package edu.luc.etl.cs313.android.shapes.model;

import java.util.List;

/**
 * A shape visitor for calculating the bounding box, that is, the smallest
 * rectangle containing the shape. The resulting bounding box is returned as a
 * rectangle at a specific location.
 */
public class BoundingBox implements Visitor<Location> {

    @Override
    public Location onCircle(final Circle c) {
        final int radius = c.getRadius();
        return new Location(-radius, -radius, new Rectangle(2 * radius, 2 * radius));
    }

    @Override
    public Location onFill(final Fill f) {
        return f.getShape().accept(this);
    }

    @Override
    public Location onGroup(final Group g) {
        List<? extends Shape> Shapes = g.getShapes();
        if (Shapes.isEmpty()) {
            return new Location(0, 0, new Rectangle(0, 0));
        }
        int minimumX = Integer.MAX_VALUE;
        int maximumX = Integer.MAX_VALUE;
        int minimumY = Integer.MIN_VALUE;
        int maximumY = Integer.MIN_VALUE;

        for (Shape s : Shapes){
        Location location = s.accept(this);
        Rectangle rectangle = (Rectangle) location.getShape();
        int x = location.getX();
        int y = location.getY();
        int widthOfShape = rectangle.getWidth();
        int heightOfShape = rectangle.getHeight();
        minimumX = Math.min(minimumX, x);
        minimumY = Math.min(minimumY, y);
        maximumX = Math.max(maximumX, x + widthOfShape);
        maximumY = Math.max(maximumY, y + widthOfShape);
        }
return new Location(minimumX,minimumY, new Rectangle(maximumX - minimumX, maximumY - minimumY));
}
    @Override
    public Location onLocation(final Location l) {
return null;
    }

    @Override
    public Location onRectangle(final Rectangle r) {
       return new Location(0,0, r);
    }

    @Override
    public Location onStrokeColor(final StrokeColor c) {
        return c.getShape().accept(this);
    }

    @Override
    public Location onOutline(final Outline o) {
        return o.getShape().accept(this);
    }

    @Override
    public Location onPolygon(final Polygon s) {
        return null;
    }
}
