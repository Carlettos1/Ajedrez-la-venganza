package com.carlettos.game.board.piece.starting;

import com.carlettos.game.core.ActionResult;
import com.carlettos.game.board.manager.clock.event.Event;
import com.carlettos.game.core.Point;
import com.carlettos.game.board.player.Player;
import com.carlettos.game.board.manager.Board;
import com.carlettos.game.board.manager.AbstractBoard;
import com.carlettos.game.board.manager.clock.event.EventInfo;
import com.carlettos.game.board.piece.Piece;
import com.carlettos.game.board.piece.SimplePiece;
import com.carlettos.game.board.property.Color;
import com.carlettos.game.board.property.ability.Ability;
import com.carlettos.game.board.property.PieceType;
import com.carlettos.game.board.property.ability.InfoNone;
import com.carlettos.game.board.property.ability.InfoGetter.AbilityNone;
import com.carlettos.game.board.piece.pattern.starting.PatternCrazyPawn;

/**
 *
 * @author Carlettos
 */
public class CrazyPawn extends SimplePiece<PatternCrazyPawn> {
    public final static Ability<CrazyPawn, String, InfoNone> HABILIDAD_PEON_LOCO = new HabilidadPeonLoco<>();
    
    public CrazyPawn(Color color) {
        super("Peon Loco", "PE", HABILIDAD_PEON_LOCO, color, PatternCrazyPawn.STANDARD_PATTERN, PieceType.BIOLOGIC, PieceType.TRANSPORTABLE);
    }
    public static class HabilidadPeonLoco<P extends Piece> extends Ability<P, String, InfoNone> implements AbilityNone {
        public HabilidadPeonLoco() {
            super("Terminar Sufrimiento",
                    "Elimina esta pieza del tablero y te da 2 cartas.", 
                    0, 
                    0, 
                    "Ninguno");
        }

        @Override
        public ActionResult canUse(AbstractBoard tablero, P pieza, Point inicio, InfoNone info) {
            return ActionResult.fromBoolean(this.commonCanUse(tablero, pieza) && tablero instanceof Board);
        }

        @Override
        public void use(AbstractBoard tablero, P pieza, Point inicio, InfoNone info) {
            if(tablero instanceof Board board){
                final Player jugador = board.getClock().turnOf();
                board.removePiece(inicio);
                board.getClock().addEvent(Event.create(EventInfo.of(board, 1, this.getNombre()), () -> {
                    jugador.robarCarta(); //robar 2 cartas
                    jugador.robarCarta();
                }));
            } else {
                throw new IllegalArgumentException("Tablero no es instanceof Tablero");
            }
        }
    }
}
