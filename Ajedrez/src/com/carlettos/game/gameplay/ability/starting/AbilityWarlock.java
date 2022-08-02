package com.carlettos.game.gameplay.ability.starting;

import com.carlettos.game.board.AbstractSquareBoard;
import com.carlettos.game.gameplay.ability.Ability;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.ActionResult;

public class AbilityWarlock extends Ability {

    // todo: que funcione
    public AbilityWarlock() {
        super("warlock", 1, 1);
    }

    @Override
    public ActionResult canUse(AbstractSquareBoard board, Piece piece, Point start, Info info) {
        return ActionResult.FAIL;
    }

    @Override
    public void use(AbstractSquareBoard board, Piece piece, Point start, Info info) {
        // todo: body ablity
    }

    @Override
    public Object[] getValues(AbstractSquareBoard board, Point start) {
        return new Object[] { "a" };
    }
}
