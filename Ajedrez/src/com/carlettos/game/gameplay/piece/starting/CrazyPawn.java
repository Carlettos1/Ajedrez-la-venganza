package com.carlettos.game.gameplay.piece.starting;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.board.Board;
import com.carlettos.game.board.clock.event.Event;
import com.carlettos.game.board.clock.event.EventInfo;
import com.carlettos.game.gameplay.ability.Ability;
import com.carlettos.game.gameplay.ability.InfoUse.AbilityNone;
import com.carlettos.game.gameplay.ability.info.InfoNone;
import com.carlettos.game.gameplay.pattern.starting.PatternCrazyPawn;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.gameplay.piece.SimplePiece;
import com.carlettos.game.gameplay.player.Player;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.ActionResult;
import com.carlettos.game.util.enums.Color;
import com.carlettos.game.util.enums.PieceType;

/**
 *
 * @author Carlettos
 */
public class CrazyPawn extends SimplePiece<PatternCrazyPawn> {
    public final static Ability<CrazyPawn, String, InfoNone> ABILITY_CRAZY_PAWN = new AbilityCrazyPawn<>();
    
    public CrazyPawn(Color color) {
        super("crazy_pawn", "cp", ABILITY_CRAZY_PAWN, color, PatternCrazyPawn.STANDARD_PATTERN, PieceType.BIOLOGIC, PieceType.TRANSPORTABLE);
    }
    public static class AbilityCrazyPawn<P extends Piece> extends Ability<P, String, InfoNone> implements AbilityNone {
        public AbilityCrazyPawn() {
            super("Terminar Sufrimiento",
                    "Elimina esta pieza del tablero y te da 2 cartas.", 
                    0, 
                    0);
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
                b.getClock().addEvent(Event.create(EventInfo.of(b, 1, this.data.name()), () -> {
                    player.takeCard(b.getClock());
                    player.takeCard(b.getClock());
                }));
            } else {
                throw new IllegalArgumentException("Tablero no es instanceof Tablero");
            }
        }
    }
}
