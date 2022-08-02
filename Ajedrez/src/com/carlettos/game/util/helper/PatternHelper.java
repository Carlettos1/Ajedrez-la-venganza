package com.carlettos.game.util.helper;

import com.carlettos.game.util.Point;

public class PatternHelper {
    protected PatternHelper() {}

    public static boolean anyMatch(Point start, Point end, Point... acceptedValues) {
        for (Point accepted : acceptedValues) {
            if (start.add(accepted).equals(end)) { return true; }
        }
        return false;
    }
}
