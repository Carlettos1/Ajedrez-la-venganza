package com.carlettos.game.tablero.pieza.nueva;

import com.carlettos.game.core.ActionResult;
import com.carlettos.game.core.Evento;
import com.carlettos.game.core.Point;
import com.carlettos.game.tablero.jugador.Jugador;
import com.carlettos.game.tablero.manager.Tablero;
import com.carlettos.game.tablero.pieza.Pieza;
import com.carlettos.game.tablero.pieza.clasica.PiezaSimple;
import com.carlettos.game.tablero.pieza.patron.nuevo.PatronPeonLoco;
import com.carlettos.game.tablero.propiedad.Color;

import com.carlettos.game.tablero.propiedad.habilidad.Habilidad;
import com.carlettos.game.tablero.propiedad.Tipo;

/**
 *
 * @author Carlettos
 */
public class PeonLoco extends PiezaSimple<PatronPeonLoco> {
    public final static Habilidad<PeonLoco> HABILIDAD_PEON_LOCO = new HabilidadPeonLoco<>();
    
    public PeonLoco(Color color) {
        super("Peon Loco", "PE", HABILIDAD_PEON_LOCO, color, PatronPeonLoco.PATRON_STANDAR, Tipo.BIOLOGICA, Tipo.TRANSPORTABLE);
    }
    public static class HabilidadPeonLoco<P extends Pieza> extends Habilidad<P>{
        public HabilidadPeonLoco() {
            super("Terminar Sufrimiento",
                    "Elimina esta pieza del tablero y te da 2 cartas.", 
                    0, 
                    0, 
                    "Ninguno");
        }

        @Override
        public ActionResult canUsar(Tablero tablero, P pieza, Point inicio, Point final_, String informacionExtra) {
            return ActionResult.PASS;
        }

        @Override
        public void usar(Tablero tablero, P pieza, Point inicio, Point final_, String informacionExtra) {
            final Jugador jugador = tablero.getReloj().turnoDe();
            tablero.quitarPieza(inicio);
            tablero.getReloj().addEventos(Evento.Builder.start(tablero).with(1, this.getNombre()).build((turnos1, nombre1, punto1, tablero1) -> {
                jugador.robarCarta();
                jugador.robarCarta();
            }));
        }
    }
}
