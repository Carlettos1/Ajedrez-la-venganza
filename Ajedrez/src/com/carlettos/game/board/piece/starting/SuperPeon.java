package com.carlettos.game.board.piece.starting;

import com.carlettos.game.core.ActionResult;
import com.carlettos.game.core.Evento;
import com.carlettos.game.core.Point;
import com.carlettos.game.board.manager.Board;
import com.carlettos.game.board.manager.AbstractBoard;
import com.carlettos.game.board.piece.AbstractPeon;
import com.carlettos.game.board.piece.Pieza;
import com.carlettos.game.board.piece.pattern.starting.PatronSuperPeonComer;
import com.carlettos.game.board.piece.pattern.starting.PatronSuperPeonMover;
import com.carlettos.game.board.property.Color;
import com.carlettos.game.board.property.ability.Habilidad;
import com.carlettos.game.board.property.Tipo;
import com.carlettos.game.board.property.ability.InfoNinguna;
import com.carlettos.game.board.property.ability.InfoGetter.HabilidadSinInfo;

/**
 *
 * @author Carlettos
 */
public class SuperPeon extends AbstractPeon<PatronSuperPeonMover, PatronSuperPeonComer> {
    public static final Habilidad<SuperPeon, String, InfoNinguna> HABILIDAD_SUPER_PEON = new HabilidadSuperPeon<>();
    
    public SuperPeon(Color color) {
        super(()->color, ()->color, "Super Peon", "SU", HABILIDAD_SUPER_PEON, color);
    }
    
    public static class HabilidadSuperPeon<P extends Pieza> extends Habilidad<P, String, InfoNinguna> implements HabilidadSinInfo{
        public HabilidadSuperPeon() {
            super("Defender", 
                    "Añade el tipo inmune a esta pieza.", 
                    10, 0, 
                    "Ninguno.");
        }

        @Override
        public ActionResult canUsar(AbstractBoard tablero, P pieza, Point inicio, InfoNinguna info) {
            return ActionResult.fromBoolean(!pieza.isTipo(Tipo.INMUNE) && this.commonCanUsar(tablero, pieza) && tablero instanceof Board);
        }

        @Override
        public void usar(AbstractBoard tablero, P pieza, Point inicio, InfoNinguna info) {
            if(tablero instanceof Board t){
                pieza.addTipo(Tipo.INMUNE); //TODO: que sea impenetrable
                t.getReloj().addEventos(Evento.Builder.start(t).with(5, "Expiración Defender")
                        .build((turnos1, nombre1, punto1, tablero1) -> {
                            pieza.removeTipo(Tipo.INMUNE);
                }));
            } else {
                throw new IllegalArgumentException("Tablero no es instanceof Tablero");
            }
            this.commonUsar(tablero, pieza);
        }
    }
}
