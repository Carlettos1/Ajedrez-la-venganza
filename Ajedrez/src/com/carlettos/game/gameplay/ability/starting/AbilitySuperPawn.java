package com.carlettos.game.gameplay.ability.starting;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.board.clock.Time;
import com.carlettos.game.gameplay.ability.AbilityNoInfo;
import com.carlettos.game.gameplay.piece.type.IPieceType;
import com.carlettos.game.util.Point;

public class AbilitySuperPawn extends AbilityNoInfo {
    public AbilitySuperPawn() {
        super("super_pawn", Time.lap(10), 0);
    }

    @Override
    public boolean reducedCanUse(AbstractBoard board, Point start) {
        return !board.getPiece(start).getTypeManager().isImmune();
    }

    @Override
    public void use(AbstractBoard board, Point start) {
        board.getPiece(start).getTypeManager().addTypes(IPieceType.IMMUNE, IPieceType.IMPENETRABLE);
        this.commonUse(board, start);
    }
}
