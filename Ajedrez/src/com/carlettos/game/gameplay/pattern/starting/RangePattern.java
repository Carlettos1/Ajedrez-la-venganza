package com.carlettos.game.gameplay.pattern.starting;

import com.carlettos.game.board.IBaseBoard;
import com.carlettos.game.gameplay.pattern.Pattern;
import com.carlettos.game.util.Point;

public class RangePattern implements Pattern {
    public final int range;

    public RangePattern(int range) {
        this.range = range;
    }

    @Override
    public boolean match(IBaseBoard board, Point start, Point end) {
        return start.getDistanceTo(end) <= range;
    }
}
