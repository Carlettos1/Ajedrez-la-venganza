package com.carlettos.game.gameplay.ability.starting;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.gameplay.ability.Ability;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.ActionResult;

public class AbilityMagician extends Ability {

    //todo: que funcione
    public AbilityMagician() {
        super("xd", "nonn", 1, 1);
    } 

    @Override
    public ActionResult canUse(AbstractBoard board, Piece piece, Point start, Info info) {
        return ActionResult.FAIL;
    }

    @Override
    public void use(AbstractBoard board, Piece piece, Point start, Info info) {
    }

    @Override
    public Object[] getValues(AbstractBoard board, Point start) {
        return new Object[] {"a"};
    }
}
