package com.carlettos.game.tablero.pieza.nueva;

import com.carlettos.game.core.ActionResult;
import com.carlettos.game.core.Evento;
import com.carlettos.game.core.Point;
import com.carlettos.game.tablero.manager.Tablero;
import com.carlettos.game.tablero.manager.TableroAbstract;
import com.carlettos.game.tablero.pieza.AbstractPeon;
import com.carlettos.game.tablero.pieza.Pieza;
import com.carlettos.game.tablero.pieza.patron.nuevo.PatronSuperPeonComer;
import com.carlettos.game.tablero.pieza.patron.nuevo.PatronSuperPeonMover;
import com.carlettos.game.tablero.propiedad.Color;
import com.carlettos.game.tablero.propiedad.habilidad.Habilidad;
import com.carlettos.game.tablero.propiedad.Tipo;
import com.carlettos.game.tablero.propiedad.habilidad.InfoNinguna;
import com.carlettos.game.tablero.propiedad.habilidad.InfoGetter.HabilidadSinInfo;

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
        public ActionResult canUsar(TableroAbstract tablero, P pieza, Point inicio, InfoNinguna info) {
            return ActionResult.fromBoolean(!pieza.isTipo(Tipo.INMUNE));
        }

        @Override
        public void usar(TableroAbstract tablero, P pieza, Point inicio, InfoNinguna info) {
            if(tablero instanceof Tablero t){
                pieza.addTipo(Tipo.INMUNE); //TODO: que sea impenetrable
                t.getReloj().addEventos(Evento.Builder.start(t).with(5, "Expiración Defender")
                        .build((turnos1, nombre1, punto1, tablero1) -> {
                            pieza.removeTipo(Tipo.INMUNE);
                }));
            } else {
                throw new IllegalArgumentException("Tablero no es instanceof Tablero");
            }
        }
    }
}
