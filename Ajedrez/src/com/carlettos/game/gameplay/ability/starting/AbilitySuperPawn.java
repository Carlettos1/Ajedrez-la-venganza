package com.carlettos.game.gameplay.ability.starting;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.gameplay.ability.AbilityNoInfo;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.gameplay.piece.type.IPieceType;
import com.carlettos.game.util.Point;

public class AbilitySuperPawn extends AbilityNoInfo {
    public AbilitySuperPawn() {
        super("super_pawn", 10, 0);
    }

    @Override
    public boolean canUse(AbstractBoard board, Piece piece, Point start) {
        return (!piece.getTypeManager().isImmune() && this.commonCanUse(board, piece));
    }

    @Override
    public void use(AbstractBoard board, Piece piece, Point start) {
        piece.getTypeManager().addTypes(IPieceType.IMMUNE, IPieceType.IMPENETRABLE);
    }
}
