package com.carlettos.game.gameplay.piece.starting;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.board.Board;
import com.carlettos.game.board.clock.event.Event;
import com.carlettos.game.board.clock.event.EventInfo;
import com.carlettos.game.gameplay.ability.Ability;
import com.carlettos.game.gameplay.ability.InfoUse.AbilityNone;
import com.carlettos.game.gameplay.ability.info.InfoNone;
import com.carlettos.game.gameplay.pattern.starting.PatternSuperPawnMove;
import com.carlettos.game.gameplay.pattern.starting.PatternSuperPawnTake;
import com.carlettos.game.gameplay.piece.AbstractPawn;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.ActionResult;
import com.carlettos.game.util.enums.Color;
import com.carlettos.game.util.enums.PieceType;

/**
 *
 * @author Carlettos
 */
public class SuperPawn extends AbstractPawn<PatternSuperPawnMove, PatternSuperPawnTake> {
    public static final Ability<SuperPawn, String, InfoNone> ABILITY_SUPER_PAWN = new AbilitySuperPawn<>();
    
    public SuperPawn(Color color) {
        super(() -> color, () -> color, "super_pawn", "sp", ABILITY_SUPER_PAWN, color);
    }
    
    public static class AbilitySuperPawn<P extends Piece> extends Ability<P, String, InfoNone> implements AbilityNone {
        public AbilitySuperPawn() {
            super("Defender", 
                    "Añade el tipo inmune a esta pieza.", 
                    10, 0);
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
