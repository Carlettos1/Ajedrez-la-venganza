package com.carlettos.game.board.piece.starting;

import com.carlettos.game.core.ActionResult;
import com.carlettos.game.core.Event;
import com.carlettos.game.core.Point;
import com.carlettos.game.board.player.Player;
import com.carlettos.game.board.manager.Board;
import com.carlettos.game.board.manager.AbstractBoard;
import com.carlettos.game.board.piece.Piece;
import com.carlettos.game.board.piece.SimplePiece;
import com.carlettos.game.board.piece.pattern.starting.PatternMadPawn;
import com.carlettos.game.board.property.Color;
import com.carlettos.game.board.property.ability.Ability;
import com.carlettos.game.board.property.PieceType;
import com.carlettos.game.board.property.ability.InfoNone;
import com.carlettos.game.board.property.ability.InfoGetter.HabilidadSinInfo;

/**
 *
 * @author Carlettos
 */
public class MadPawn extends SimplePiece<PatternMadPawn> {
    public final static Ability<MadPawn, String, InfoNone> HABILIDAD_PEON_LOCO = new HabilidadPeonLoco<>();
    
    public MadPawn(Color color) {
        super("Peon Loco", "PE", HABILIDAD_PEON_LOCO, color, PatternMadPawn.PATRON_STANDAR, PieceType.BIOLOGICA, PieceType.TRANSPORTABLE);
    }
    public static class HabilidadPeonLoco<P extends Piece> extends Ability<P, String, InfoNone> implements HabilidadSinInfo {
        public HabilidadPeonLoco() {
            super("Terminar Sufrimiento",
                    "Elimina esta pieza del tablero y te da 2 cartas.", 
                    0, 
                    0, 
                    "Ninguno");
        }

        @Override
        public ActionResult canUsar(AbstractBoard tablero, P pieza, Point inicio, InfoNone info) {
            return ActionResult.fromBoolean(this.commonCanUsar(tablero, pieza) && tablero instanceof Board);
        }

        @Override
        public void usar(AbstractBoard tablero, P pieza, Point inicio, InfoNone info) {
            if(tablero instanceof Board t){
                final Player jugador = t.getClock().turnoDe();
                t.quitarPieza(inicio);
                t.getClock().addEventos(Event.Builder.start(t).with(1, this.getNombre()).build((turnos1, nombre1, punto1, tablero1) -> {
                    jugador.robarCarta();
                    jugador.robarCarta();
                }));
            } else {
                throw new IllegalArgumentException("Tablero no es instanceof Tablero");
            }
        }
    }
}
