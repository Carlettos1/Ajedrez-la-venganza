package com.carlettos.game.board.shape;

import com.carlettos.game.util.Point;

/**
 * A representation of a 2D shape
 */
public abstract class Shape {

    /**
     * It returns the total area of the shape. Because it is used to repressent a
     * chess board, the area is the total number of escaques that it can have;
     */
    public abstract int getArea();

    /**
     * It has to return a new object
     */
    public abstract Square toSquareShape();

    /**
     * It checks that the given point is out or in the border.
     *
     * @param point point to check.
     * @return true if is inside the shape, false other case.
     */
    public abstract boolean isOutOfBorders(Point point);

    @Override
    public boolean equals(Object obj) {
        if (this == obj) { return true; }
        if ((obj == null) || !(obj instanceof Shape)) { return false; }
        Shape other = (Shape) obj;
        return other.toSquareShape().getDimensions().equals(this.toSquareShape().getDimensions());
    }
}
