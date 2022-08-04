package com.carlettos.game.util.helper;

import com.carlettos.game.util.Point;

/**
 *
 * @author Carlettos
 */
public final class MathHelper {
    private MathHelper() {}

    public static final int clamp(int min, int max, int num) {
        if (num > max) {
            return max;
        } else if (num < min) {
            return min;
        } else {
            return num;
        }
    }

    public static final Point clamp(Point min, Point max, Point num) {
        return new Point(clamp(min.x, max.x, num.x), clamp(min.y, max.y, num.y));
    }

    /**
     * Checks if the number is between min and max (both inclusive).
     *
     * @param num number to check
     * @param min minimum value of the number
     * @param max maximum value of the number
     * @throws IndexOutOfBoundsException in case that the number is higher than max
     *                                   or lower than min.
     */
    public static final void requireInBounds(int num, int min, int max) {
        if (num < min || num > max) {
            throw new IndexOutOfBoundsException("%s is not between %s and %s".formatted(num, min, max));
        }
    }
}
