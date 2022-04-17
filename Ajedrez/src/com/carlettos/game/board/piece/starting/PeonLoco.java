package com.carlettos.game.board.piece.starting;

import com.carlettos.game.core.ActionResult;
import com.carlettos.game.core.Evento;
import com.carlettos.game.core.Point;
import com.carlettos.game.board.player.Jugador;
import com.carlettos.game.board.manager.Board;
import com.carlettos.game.board.manager.AbstractBoard;
import com.carlettos.game.board.piece.Piece;
import com.carlettos.game.board.piece.PiezaSimple;
import com.carlettos.game.board.piece.pattern.starting.PatronPeonLoco;
import com.carlettos.game.board.property.Color;
import com.carlettos.game.board.property.ability.Ability;
import com.carlettos.game.board.property.Tipo;
import com.carlettos.game.board.property.ability.InfoNinguna;
import com.carlettos.game.board.property.ability.InfoGetter.HabilidadSinInfo;

/**
 *
 * @author Carlettos
 */
public class PeonLoco extends PiezaSimple<PatronPeonLoco> {
    public final static Ability<PeonLoco, String, InfoNinguna> HABILIDAD_PEON_LOCO = new HabilidadPeonLoco<>();
    
    public PeonLoco(Color color) {
        super("Peon Loco", "PE", HABILIDAD_PEON_LOCO, color, PatronPeonLoco.PATRON_STANDAR, Tipo.BIOLOGICA, Tipo.TRANSPORTABLE);
    }
    public static class HabilidadPeonLoco<P extends Piece> extends Ability<P, String, InfoNinguna> implements HabilidadSinInfo {
        public HabilidadPeonLoco() {
            super("Terminar Sufrimiento",
                    "Elimina esta pieza del tablero y te da 2 cartas.", 
                    0, 
                    0, 
                    "Ninguno");
        }

        @Override
        public ActionResult canUsar(AbstractBoard tablero, P pieza, Point inicio, InfoNinguna info) {
            return ActionResult.fromBoolean(this.commonCanUsar(tablero, pieza) && tablero instanceof Board);
        }

        @Override
        public void usar(AbstractBoard tablero, P pieza, Point inicio, InfoNinguna info) {
            if(tablero instanceof Board t){
                final Jugador jugador = t.getClock().turnoDe();
                t.quitarPieza(inicio);
                t.getClock().addEventos(Evento.Builder.start(t).with(1, this.getNombre()).build((turnos1, nombre1, punto1, tablero1) -> {
                    jugador.robarCarta();
                    jugador.robarCarta();
                }));
            } else {
                throw new IllegalArgumentException("Tablero no es instanceof Tablero");
            }
        }
    }
}
