package com.carlettos.game.board.piece.starting;

import com.carlettos.game.core.ActionResult;
import com.carlettos.game.core.Event;
import com.carlettos.game.core.Point;
import com.carlettos.game.board.manager.Board;
import com.carlettos.game.board.manager.AbstractBoard;
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
        public ActionResult canUsar(AbstractBoard tablero, P pieza, Point inicio, InfoNone info) {
            return ActionResult.fromBoolean(!pieza.isTipo(PieceType.INMUNE) && this.commonCanUsar(tablero, pieza) && tablero instanceof Board);
        }

        @Override
        public void usar(AbstractBoard tablero, P pieza, Point inicio, InfoNone info) {
            if(tablero instanceof Board t){
                pieza.addTipo(PieceType.INMUNE); //TODO: que sea impenetrable
                t.getClock().addEventos(Event.Builder.start(t).with(5, "Expiración Defender")
                        .build((turnos1, nombre1, punto1, tablero1) -> {
                            pieza.removeTipo(PieceType.INMUNE);
                }));
            } else {
                throw new IllegalArgumentException("Tablero no es instanceof Tablero");
            }
            this.commonUsar(tablero, pieza);
        }
    }
}
