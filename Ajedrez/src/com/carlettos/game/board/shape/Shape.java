package com.carlettos.game.board.shape;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.carlettos.game.util.Point;

/**
 * A representation of a 2D shape
 */
public abstract class Shape {
    protected final Polygon form;
    protected final Point[] pointsInside;
    protected final int area;

    /**
     * Constructs a shape based on the given vertices, starting at Point(0, 0).
     */
    public Shape(Point[] vertices) {
        if (vertices[0].equals(new Point())) {
            this.form = new Polygon(vertices);
        } else {
            Point[] vs = new Point[vertices.length + 1];
            vs[0] = new Point();
            for (int i = 0; i < vertices.length; i++) {
                vs[i + 1] = vertices[i];
            }
            this.form = new Polygon(vs);
        }

        final Point max = this.form.getBounds();
        final List<Point> points = new ArrayList<>(max.x * max.y);
        for (int y = 0; y < max.y; y++) {
            for (int x = 0; x < max.x; x++) {
                Point current = new Point(x, y);
                if (this.form.contains(current)) {
                    points.add(current);
                }
            }
        }

        this.pointsInside = points.toArray(Point[]::new);
        this.area = this.pointsInside.length;
    }

    /**
     * Returns all the points that are inside this shape
     */
    public Point[] getAllPointsInside() {
        return this.pointsInside;
    }

    /**
     * It returns the total area of the shape (measured in escaques).
     */
    public int area() {
        return this.area;
    }

    /**
     * It has to return a new object
     */
    public Rectangle getBoundingRectangle() {
        return new Rectangle(form.getBounds());
    }

    /**
     * Check if the given point is inside this shape.
     */
    public boolean contains(Point point) {
        return this.form.contains(point);
    }

    @Override
    public int hashCode() {
        return Objects.hash(form);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if ((obj == null) || (getClass() != obj.getClass()))
            return false;
        Shape other = (Shape) obj;
        return Objects.equals(form, other.form);
    }

    @Override
    public String toString() {
        return "Shape of area " + this.area() + ", with " + this.form.vertices.length + " vertices\n"
                + Arrays.toString(this.pointsInside);
    }
}
