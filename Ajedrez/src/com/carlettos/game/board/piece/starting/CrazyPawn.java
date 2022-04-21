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
    public final static Ability<CrazyPawn, String, InfoNone> ABILITY_CRAZY_PAWN = new AbilityCrazyPawn<>();
    
    public CrazyPawn(Color color) {
        super("Peon Loco", "PE", ABILITY_CRAZY_PAWN, color, PatternCrazyPawn.STANDARD_PATTERN, PieceType.BIOLOGIC, PieceType.TRANSPORTABLE);
    }
    public static class AbilityCrazyPawn<P extends Piece> extends Ability<P, String, InfoNone> implements AbilityNone {
        public AbilityCrazyPawn() {
            super("Terminar Sufrimiento",
                    "Elimina esta pieza del tablero y te da 2 cartas.", 
                    0, 
                    0, 
                    "Ninguno");
        }

        @Override
        public ActionResult canUse(AbstractBoard board, P piece, Point start, InfoNone info) {
            return ActionResult.fromBoolean(this.commonCanUse(board, piece) && board instanceof Board);
        }

        @Override
        public void use(AbstractBoard board, P piece, Point start, InfoNone info) {
            if(board instanceof Board b){
                final Player player = b.getClock().turnOf();
                b.removePiece(start);
                b.getClock().addEvent(Event.create(EventInfo.of(b, 1, this.getName()), () -> {
                    player.takeCard(); //robar 2 cartas
                    player.takeCard();
                }));
            } else {
                throw new IllegalArgumentException("Tablero no es instanceof Tablero");
            }
        }
    }
}
