package com.carlettos.game.gameplay.ability.starting;

import com.carlettos.game.board.AbstractSquareBoard;
import com.carlettos.game.board.clock.event.Event;
import com.carlettos.game.board.clock.event.EventInfo;
import com.carlettos.game.gameplay.ability.AbilityNoInfo;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.gameplay.piece.type.IPieceType;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.ActionResult;

public class AbilitySuperPawn extends AbilityNoInfo {
    public AbilitySuperPawn() {
        super("super_pawn", 10, 0);
    }

    @Override
    public ActionResult canUse(AbstractSquareBoard board, Piece piece, Point start, Info info) {
        return ActionResult
                .fromBoolean(!piece.getTypeManager().isType(IPieceType.IMMUNE) && this.commonCanUse(board, piece));
    }

    @Override
    public void use(AbstractSquareBoard board, Piece piece, Point start, Info info) {
        piece.getTypeManager().addType(IPieceType.IMMUNE);
        // TODO: que sea impenetrable
        board.getClock().addEvent(Event.create(EventInfo.of(board, 5, this.data.getName()),
                () -> piece.getTypeManager().removeType(IPieceType.IMMUNE)));
        this.commonUse(board, piece);
    }
}
