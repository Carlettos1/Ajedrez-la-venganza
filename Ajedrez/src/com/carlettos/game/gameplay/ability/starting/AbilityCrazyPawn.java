package com.carlettos.game.gameplay.ability.starting;

import com.carlettos.game.board.AbstractSquareBoard;
import com.carlettos.game.board.clock.event.Event;
import com.carlettos.game.board.clock.event.EventInfo;
import com.carlettos.game.gameplay.ability.AbilityNoInfo;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.gameplay.player.Player;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.ActionResult;

public class AbilityCrazyPawn extends AbilityNoInfo {
    public AbilityCrazyPawn() {
        super("crazy_pawn", 0, 0);
    }

    @Override
    public ActionResult canUse(AbstractSquareBoard board, Piece piece, Point start) {
        return ActionResult.fromBoolean(this.commonCanUse(board, piece));
    }

    @Override
    public void use(AbstractSquareBoard board, Piece piece, Point start) {
        final Player player = board.getClock().turnOf();
        board.removePieceNoDeath(start);
        board.getClock().addEvent(Event.create(EventInfo.of(board, 1, this.data.getName()), () -> {
            player.takeCards(board.getClock(), 2);
        }));

    }
}
