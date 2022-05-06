package com.carlettos.game.gameplay.ability.starting;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.gameplay.ability.Ability;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.ActionResult;

public class AbilityPaladin extends Ability {

    //todo: que funcione
    public AbilityPaladin() {
        super("paladin", 1, 1);
    } 

    @Override
    public ActionResult canUse(AbstractBoard board, Piece piece, Point start, Info info) {
        return ActionResult.FAIL;
    }

    @Override
    public void use(AbstractBoard board, Piece piece, Point start, Info info) {
        //todo: body ablity
    }

    @Override
    public Object[] getValues(AbstractBoard board, Point start) {
        return new Object[] {"a"};
    }
}
