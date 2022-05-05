package com.carlettos.game.gameplay.ability;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.ActionResult;

public class AbilityNone extends AbilityNoInfo {

    public AbilityNone() {
        super("none", 0, 0);
    }

    @Override
    public boolean commonCanUse(AbstractBoard board, Piece piece) {
        return false;
    }

    @Override
    public ActionResult canUse(AbstractBoard board, Piece piece, Point start, Info info) {
        return ActionResult.FAIL;
    }

    @Override
    public void use(AbstractBoard board, Piece piece, Point start, Info info) {
      // it doesn't do anything
    }
}
