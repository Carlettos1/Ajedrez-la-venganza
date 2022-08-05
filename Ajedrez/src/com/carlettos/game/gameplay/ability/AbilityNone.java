package com.carlettos.game.gameplay.ability;

import com.carlettos.game.board.AbstractSquareBoard;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.util.Point;

public class AbilityNone extends AbilityNoInfo {

    public AbilityNone() {
        super("none", 0, 0);
    }

    @Override
    public boolean commonCanUse(AbstractSquareBoard board, Piece piece) {
        return false;
    }

    @Override
    public boolean canUse(AbstractSquareBoard board, Piece piece, Point start) {
        return false;
    }

    @Override
    public void use(AbstractSquareBoard board, Piece piece, Point start) {}
}
