package com.carlettos.game.tablero.pieza.nueva;

import com.carlettos.game.core.ActionResult;
import com.carlettos.game.core.Evento;
import com.carlettos.game.core.Point;
import com.carlettos.game.tablero.jugador.Jugador;
import com.carlettos.game.tablero.manager.Tablero;
import com.carlettos.game.tablero.manager.TableroAbstract;
import com.carlettos.game.tablero.pieza.Pieza;
import com.carlettos.game.tablero.pieza.clasica.PiezaSimple;
import com.carlettos.game.tablero.pieza.patron.nuevo.PatronPeonLoco;
import com.carlettos.game.tablero.propiedad.Color;
import com.carlettos.game.tablero.propiedad.habilidad.Habilidad;
import com.carlettos.game.tablero.propiedad.Tipo;
import com.carlettos.game.tablero.propiedad.habilidad.InfoNinguna;
import com.carlettos.game.tablero.propiedad.habilidad.InfoGetter.HabilidadSinInfo;

/**
 *
 * @author Carlettos
 */
public class PeonLoco extends PiezaSimple<PatronPeonLoco> {
    public final static Habilidad<PeonLoco, String, InfoNinguna> HABILIDAD_PEON_LOCO = new HabilidadPeonLoco<>();
    
    public PeonLoco(Color color) {
        super("Peon Loco", "PE", HABILIDAD_PEON_LOCO, color, PatronPeonLoco.PATRON_STANDAR, Tipo.BIOLOGICA, Tipo.TRANSPORTABLE);
    }
    public static class HabilidadPeonLoco<P extends Pieza> extends Habilidad<P, String, InfoNinguna> implements HabilidadSinInfo {
        public HabilidadPeonLoco() {
            super("Terminar Sufrimiento",
                    "Elimina esta pieza del tablero y te da 2 cartas.", 
                    0, 
                    0, 
                    "Ninguno");
        }

        @Override
        public ActionResult canUsar(TableroAbstract tablero, P pieza, Point inicio, InfoNinguna info) {
            return ActionResult.fromBoolean(this.commonCanUsar(tablero, pieza));
        }

        @Override
        public void usar(TableroAbstract tablero, P pieza, Point inicio, InfoNinguna info) {
            if(tablero instanceof Tablero t){
                final Jugador jugador = t.getReloj().turnoDe();
                t.quitarPieza(inicio);
                t.getReloj().addEventos(Evento.Builder.start(t).with(1, this.getNombre()).build((turnos1, nombre1, punto1, tablero1) -> {
                    jugador.robarCarta();
                    jugador.robarCarta();
                }));
            } else {
                throw new IllegalArgumentException("Tablero no es instanceof Tablero");
            }
        }
    }
}
