package com.carlettos.game.gameplay.pattern.demonic;

import com.carlettos.game.board.IBaseBoard;
import com.carlettos.game.gameplay.pattern.Pattern;
import com.carlettos.game.gameplay.pattern.Patterns;
import com.carlettos.game.util.Point;

public class WitchPattern implements Pattern {
    @Override
    public boolean match(IBaseBoard board, Point start, Point end) {
        return Patterns.ROOK_PATTERN.match(board, start, end) || Patterns.KING_PATTERN.match(board, start, end);
    }
}
