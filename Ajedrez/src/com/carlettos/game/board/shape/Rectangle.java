package com.carlettos.game.board.shape;

import com.carlettos.game.util.Point;

public class Rectangle extends Shape {
    public final int x;
    public final int y;

    public Rectangle(int x, int y) {
        super(new Point[] { new Point(x, 0), new Point(x, y), new Point(0, y) });
        this.x = x;
        this.y = y;
    }

    public Rectangle(Point p) {
        this(p.x, p.y);
    }
}
