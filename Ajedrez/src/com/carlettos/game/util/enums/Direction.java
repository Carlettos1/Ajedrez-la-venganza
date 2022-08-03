package com.carlettos.game.util.enums;

import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.util.Point;

/**
 *
 * @author Carlettos
 */
public enum Direction {
    N(1), E(1), S(-1), W(-1);

    private final int sign;

    private Direction(int sign) {
        this.sign = sign;
    }

    public int getSign() {
        return sign;
    }

    public Axis getAxis() {
        return switch (this) {
            case N, S -> Axis.NS;
            case E, W -> Axis.EW;
        };
    }

    public boolean isAxis(Axis axis) {
        return this.getAxis().equals(axis);
    }

    public Info toInfo() {
        return Info.getInfo(this);
    }

    public Point toPoint() {
        return switch (this.getAxis()) {
            case NS -> new Point(0, this.getSign());
            case EW -> new Point(this.getSign(), 0);
        };
    }

    public enum Axis {
        NS, EW;
    }
}
