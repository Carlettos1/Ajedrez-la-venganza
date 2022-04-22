package com.carlettos.game.board.piece.starting;

import com.carlettos.game.core.ActionResult;
import com.carlettos.game.board.manager.clock.event.Event;
import com.carlettos.game.core.Point;
import com.carlettos.game.board.manager.Board;
import com.carlettos.game.board.manager.AbstractBoard;
import com.carlettos.game.board.manager.clock.event.EventInfo;
import com.carlettos.game.board.piece.AbstractPawn;
import com.carlettos.game.board.piece.Piece;
import com.carlettos.game.board.piece.pattern.starting.PatternSuperPawnTake;
import com.carlettos.game.board.piece.pattern.starting.PatternSuperPawnMove;
import com.carlettos.game.board.property.Color;
import com.carlettos.game.board.property.ability.Ability;
import com.carlettos.game.board.property.PieceType;
import com.carlettos.game.board.property.ability.info.InfoNone;
import com.carlettos.game.board.property.ability.InfoUse.AbilityNone;

/**
 *
 * @author Carlettos
 */
public class SuperPawn extends AbstractPawn<PatternSuperPawnMove, PatternSuperPawnTake> {
    public static final Ability<SuperPawn, String, InfoNone> ABILITY_SUPER_PAWN = new AbilitySuperPawn<>();
    
    public SuperPawn(Color color) {
        super(() -> color, () -> color, "super_pawn", "SU", ABILITY_SUPER_PAWN, color);
    }
    
    public static class AbilitySuperPawn<P extends Piece> extends Ability<P, String, InfoNone> implements AbilityNone {
        public AbilitySuperPawn() {
            super("Defender", 
                    "Añade el tipo inmune a esta pieza.", 
                    10, 0, 
                    "Ninguno.");
        }

        @Override
        public ActionResult canUse(AbstractBoard board, P piece, Point start, InfoNone info) {
            return ActionResult.fromBoolean(!piece.isType(PieceType.IMMUNE) && this.commonCanUse(board, piece) && board instanceof Board);
        }

        @Override
        public void use(AbstractBoard board, P piece, Point start, InfoNone info) {
            if(board instanceof Board board1){
                piece.addType(PieceType.IMMUNE); //TODO: que sea impenetrable
                board1.getClock().addEvent(Event.create(EventInfo.of(board1, 5,"Expiración Defender"), () -> {
                    piece.removeType(PieceType.IMMUNE);
                }));
            } else {
                throw new IllegalArgumentException("Tablero no es instanceof Tablero");
            }
            this.commonUse(board, piece);
        }
    }
}
