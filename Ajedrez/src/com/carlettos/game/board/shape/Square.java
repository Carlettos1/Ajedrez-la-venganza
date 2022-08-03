package com.carlettos.game.board.shape;

import com.carlettos.game.util.Point;

public class Square extends Shape {
    public final int x;
    public final int y;

    public Square(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int getArea() {
        return x * y;
    }

    @Override
    public Square toSquareShape() {
        return new Square(this.x, this.y);
    }

    public Point getDimensions() {
        return new Point(x, y);
    }

    @Override
    public boolean isOutOfBorders(Point point) {
        if ((point.x < 0) || (point.y < 0) || (point.x >= x)) { return true; }
        return point.y >= y;
    }
}
