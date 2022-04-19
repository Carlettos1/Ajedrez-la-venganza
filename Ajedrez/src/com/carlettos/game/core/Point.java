package com.carlettos.game.core;

import com.carlettos.game.board.property.ability.Info;
import com.carlettos.game.board.property.ability.InfoPoint;

/**
 * Immutable version of java.awt.Point.
 * 
 * @author Carlos
 * @see java.awt.Point;
 */
public final class Point implements Cloneable {
    public final int x;
    public final int y;

    public Point() {
        this(0, 0);
    }
    
    public Point(Point point){
        this(point.x, point.y);
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public Point add(Point point){
        return this.add(point.x, point.y);
    }

    public Point add(int dx, int dy) {
        return new Point(this.x + dx, this.y + dy);
    }
    
    public double getDistanceTo(Point other){
        int dx = other.x - this.x;
        int dy = other.y - this.y;
        return Math.sqrt(dx*dx + dy*dy);
    }
    
    public double getSquaredDistanceTo(Point other){
        int dx = other.x - this.x;
        int dy = other.y - this.y;
        return dx*dx + dy*dy;
    }
    
    public Info<Point> toInfo(){
        return new InfoPoint(this);
    }

    @Override
    public Object clone() {
        return new Point(this.x, this.y);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + this.x;
        hash = 97 * hash + this.y;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Point other = (Point) obj;
        if (this.x != other.x) {
            return false;
        }
        return this.y == other.y;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Point[x=").append(x);
        sb.append(", y=").append(y);
        sb.append(']');
        return sb.toString();
    }
}
