package com.carlettos.game.gameplay.ability.starting;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.board.Board;
import com.carlettos.game.board.clock.event.Event;
import com.carlettos.game.board.clock.event.EventInfo;
import com.carlettos.game.gameplay.ability.AbilityNoInfo;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.ActionResult;
import com.carlettos.game.util.enums.PieceType;

public class AbilitySuperPawn extends AbilityNoInfo {
    public AbilitySuperPawn() {
        super("super_pawn", 10, 0);
    }

    @Override
    public ActionResult canUse(AbstractBoard board, Piece piece, Point start, Info info) {
        return ActionResult.fromBoolean(!piece.isType(PieceType.IMMUNE) && this.commonCanUse(board, piece) && board instanceof Board);
    }

    @Override
    public void use(AbstractBoard board, Piece piece, Point start, Info info) {
        var board1 = (Board) board;
        piece.addType(PieceType.IMMUNE);
        //TODO: que sea impenetrable
        board1.getClock().addEvent(Event.create(EventInfo.of(board1, 5, this.data.getName()), () -> piece.removeType(PieceType.IMMUNE)));
        this.commonUse(board, piece);
    }
}
