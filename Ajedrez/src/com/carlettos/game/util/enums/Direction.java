package com.carlettos.game.util.enums;

import com.carlettos.game.gameplay.ability.IInfo;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.util.Point;

/**
 *
 * @author Carlettos
 */
public enum Direction implements IInfo {
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

    @Override
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

    public enum SubDirection implements IInfo {
        N(Direction.N), NE(Direction.N, Direction.E), E(Direction.E), SE(Direction.S, Direction.E), S(Direction.S),
        SW(Direction.S, Direction.W), W(Direction.W), NW(Direction.N, Direction.W);

        private final Point point;

        private SubDirection(Direction... directions) {
            Point zero = new Point();
            for (Direction direction : directions) {
                zero = zero.add(direction.toPoint());
            }
            this.point = zero;
        }

        public Point toPoint() {
            return this.point;
        }

        @Override
        public Info toInfo() {
            return Info.getInfo(this);
        }
    }
}
