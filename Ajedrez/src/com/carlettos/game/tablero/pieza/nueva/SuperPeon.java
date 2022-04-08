package com.carlettos.game.tablero.pieza.nueva;

import com.carlettos.game.core.ActionResult;
import com.carlettos.game.core.Evento;
import com.carlettos.game.core.Point;
import com.carlettos.game.tablero.manager.Tablero;
import com.carlettos.game.tablero.pieza.AbstractPeon;
import com.carlettos.game.tablero.pieza.Pieza;
import com.carlettos.game.tablero.pieza.patron.nuevo.PatronSuperPeonComer;
import com.carlettos.game.tablero.pieza.patron.nuevo.PatronSuperPeonMover;
import com.carlettos.game.tablero.propiedad.Color;
import com.carlettos.game.tablero.propiedad.Habilidad;
import com.carlettos.game.tablero.propiedad.Tipo;

/**
 *
 * @author Carlettos
 */
public class SuperPeon extends AbstractPeon<PatronSuperPeonComer, PatronSuperPeonMover>{
    public static final Habilidad<SuperPeon> HABILIDAD_SUPER_PEON = new HabilidadSuperPeon<>();
    
    public SuperPeon(Color color) {
        super(()->color, ()->color, "Super Peon", "SU", HABILIDAD_SUPER_PEON, color);
    }
    
    public static class HabilidadSuperPeon<P extends Pieza> extends Habilidad<P>{
        public HabilidadSuperPeon() {
            super("Defender", 
                    "Añade el tipo inmune a esta pieza.", 
                    10, 0, 
                    "Ninguno.");
        }

        @Override
        public ActionResult canUsar(Tablero tablero, P pieza, Point inicio, Point final_, String informacionExtra) {
            return ActionResult.fromBoolean(!pieza.isTipo(Tipo.INMUNE));
        }

        @Override
        public void usar(Tablero tablero, P pieza, Point inicio, Point final_, String informacionExtra) {
            pieza.addTipo(Tipo.INMUNE); //TODO: que sea impenetrable
            tablero.getReloj().addEventos(Evento.Builder.start(tablero).with(5, "Expiración Defender")
                    .build((turnos1, nombre1, punto1, tablero1) -> {
                        pieza.removeTipo(Tipo.INMUNE);
            }));
        }
    }
}
