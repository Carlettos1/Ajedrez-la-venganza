package com.carlettos.game.gameplay.ability.starting;

import com.carlettos.game.board.AbstractSquareBoard;
import com.carlettos.game.gameplay.ability.AbilityNoInfo;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.ActionResult;
import com.carlettos.game.util.helper.LogHelper;

public class AbilityShieldBearer extends AbilityNoInfo {
    public AbilityShieldBearer() {
        // TODO: repensar la habilidad
        super("shield_bearer", 6, 0);
    }

    @Override
    public ActionResult canUse(AbstractSquareBoard board, Piece piece, Point start) {
        return ActionResult.fromBoolean(this.commonCanUse(board, piece));
    }

    @Override
    public void use(AbstractSquareBoard board, Piece piece, Point start) {
        LogHelper.LOG.info(() -> "USAR HABILIDAD DEFENSOR");
    }
}
