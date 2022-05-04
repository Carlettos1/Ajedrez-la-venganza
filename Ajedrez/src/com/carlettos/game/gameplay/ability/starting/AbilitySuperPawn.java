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
        super("Defender", 
                "Añade el tipo inmune a esta pieza.", 
                10, 0);
    }

    @Override
    public ActionResult canUse(AbstractBoard board, Piece piece, Point start, Info info) {
        return ActionResult.fromBoolean(!piece.isType(PieceType.IMMUNE) && this.commonCanUse(board, piece) && board instanceof Board);
    }

    @Override
    public void use(AbstractBoard board, Piece piece, Point start, Info info) {
        if(board instanceof Board board1){
            piece.addType(PieceType.IMMUNE);
            //TODO: que sea impenetrable
            board1.getClock().addEvent(Event.create(EventInfo.of(board1, 5,"Expiración Defender"), () -> {
                piece.removeType(PieceType.IMMUNE);
            }));
        } else {
            throw new IllegalArgumentException("Tablero no es instanceof Tablero");
        }
        this.commonUse(board, piece);
    }
}
