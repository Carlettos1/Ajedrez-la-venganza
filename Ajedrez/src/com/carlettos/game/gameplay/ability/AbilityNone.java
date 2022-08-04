package com.carlettos.game.gameplay.ability;

import com.carlettos.game.board.AbstractSquareBoard;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.ActionResult;

public class AbilityNone extends AbilityNoInfo {

    public AbilityNone() {
        super("none", 0, 0);
    }

    @Override
    public boolean commonCanUse(AbstractSquareBoard board, Piece piece) {
        return false;
    }

    @Override
    public ActionResult canUse(AbstractSquareBoard board, Piece piece, Point start) {
        return ActionResult.FAIL;
    }

    @Override
    public void use(AbstractSquareBoard board, Piece piece, Point start) {
    }
}
