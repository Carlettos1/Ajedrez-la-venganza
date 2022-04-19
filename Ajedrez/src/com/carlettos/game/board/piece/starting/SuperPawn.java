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
import com.carlettos.game.board.property.ability.InfoNone;
import com.carlettos.game.board.property.ability.InfoGetter.HabilidadSinInfo;

/**
 *
 * @author Carlettos
 */
public class SuperPawn extends AbstractPawn<PatternSuperPawnMove, PatternSuperPawnTake> {
    public static final Ability<SuperPawn, String, InfoNone> HABILIDAD_SUPER_PEON = new HabilidadSuperPeon<>();
    
    public SuperPawn(Color color) {
        super(()->color, ()->color, "Super Peon", "SU", HABILIDAD_SUPER_PEON, color);
    }
    
    public static class HabilidadSuperPeon<P extends Piece> extends Ability<P, String, InfoNone> implements HabilidadSinInfo{
        public HabilidadSuperPeon() {
            super("Defender", 
                    "Añade el tipo inmune a esta pieza.", 
                    10, 0, 
                    "Ninguno.");
        }

        @Override
        public ActionResult canUse(AbstractBoard board, P piece, Point start, InfoNone info) {
            return ActionResult.fromBoolean(!piece.isType(PieceType.INMUNE) && this.commonCanUse(board, piece) && board instanceof Board);
        }

        @Override
        public void use(AbstractBoard board, P piece, Point start, InfoNone info) {
            if(board instanceof Board board1){
                piece.addType(PieceType.INMUNE); //TODO: que sea impenetrable
                board1.getClock().addEvent(Event.create(EventInfo.of(board1, 5,"Expiración Defender"), () -> {
                    piece.removeType(PieceType.INMUNE);
                }));
            } else {
                throw new IllegalArgumentException("Tablero no es instanceof Tablero");
            }
            this.commonUse(board, piece);
        }
    }
}
