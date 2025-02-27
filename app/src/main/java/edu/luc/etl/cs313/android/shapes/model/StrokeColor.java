package edu.luc.etl.cs313.android.shapes.model;

/**
 * A decorator for specifying the stroke (foreground) color for drawing the
 * shape.
 */
public class StrokeColor implements Shape {

    // TODO entirely your job - maybe done
    protected final int color;
    protected final Shape shape

    public StrokeColor(final int color, final Shape shape) {
        this.shape = shape;
        this.color = color;
    }

    public int getColor() {
        return this.color;
    }

    public Shape getShape() {
        return this.shape;
    }

    @Override
    public <Result> Result accept(Visitor<Result> v) {
        return v.onStrokeColor(this);
    }
}
