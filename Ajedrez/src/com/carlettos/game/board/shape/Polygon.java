package com.carlettos.game.board.shape;

import java.util.Arrays;

import com.carlettos.game.util.Point;
import com.carlettos.game.util.helper.MathHelper;

/**
 * Its an immutable version of {@link java.awt.Polygon}.
 */
public final class Polygon {

    /**
     * Immutable array. Every vertex its here.
     */
    protected final Point[] vertices;

    /**
     * Constructs a polygon from the given vertices.
     */
    public Polygon(final Point[] vertices) {
        if (vertices.length < 3) { throw new IllegalArgumentException("A polygon needs at least 3 vertices"); }
        this.vertices = vertices;
    }

    public Point getBounds() {
        Point boundMax = this.vertices[0];
        for (Point vertex : this.vertices) {
            boundMax = MathHelper.max(vertex, boundMax);
        }
        return boundMax;
    }

    /**
     * Determines whether the specified {@link Point} is inside this {@code Polygon}
     * using the even-odd rule.
     *
     * @param p the specified {@code Point} to be tested
     * @return {@code true} if the {@code Polygon} contains the {@code Point};
     *         {@code false} otherwise.
     */
    public boolean contains(final Point p) {
        Point last = this.vertices[vertices.length - 1];
        boolean isOdd = false;
        double correctedX = p.x + 0.5D;
        double correctedY = p.y + 0.5D;

        for (Point current : this.vertices) {
            if ((current.y > correctedY) != (last.y > correctedY)) {
                double slope = (correctedX - current.x) * (last.y - current.y)
                        - (correctedY - current.y) * (last.x - current.x);
                if (slope == 0) { return true; }
                if ((slope < 0) != (last.y < current.y)) {
                    isOdd = !isOdd;
                }
            }
            last = current;
        }
        return isOdd;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(vertices);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if ((obj == null) || (getClass() != obj.getClass()))
            return false;
        Polygon other = (Polygon) obj;
        return Arrays.equals(vertices, other.vertices);
    }

    @Override
    public String toString() {
        return "Polygon [vertices=" + Arrays.toString(vertices) + "]";
    }
}
