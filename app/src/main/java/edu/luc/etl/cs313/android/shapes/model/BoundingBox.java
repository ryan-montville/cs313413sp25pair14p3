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
        int minimumX = Integer.MAX_VALUE, minimumY = Integer.MAX_VALUE, maximumX = 0, maximumY = 0;

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
        Location changedLocation= l.getShape().accept(this);
        return new Location(l.getX() + changedLocation.getX(), l.getY() + changedLocation.getY(), changedLocation.getShape());
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
        int minX = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxY = Integer.MIN_VALUE;
        for (Point point : s.getPoints()) {
            minX = Math.min(minX, point.getX());
            maxX = Math.max(maxX, point.getX());
            minY = Math.min(minY, point.getY());
            maxY = Math.max(maxY, point.getY());
        }
        return new Location(minX, minY, new Rectangle(maxX - minX, maxY - minY));
    }
}
