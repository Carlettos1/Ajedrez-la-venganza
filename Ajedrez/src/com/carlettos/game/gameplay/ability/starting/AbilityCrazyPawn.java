package com.carlettos.game.gameplay.ability.starting;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.board.Board;
import com.carlettos.game.board.clock.event.Event;
import com.carlettos.game.board.clock.event.EventInfo;
import com.carlettos.game.gameplay.ability.AbilityNoInfo;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.gameplay.player.Player;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.ActionResult;

public class AbilityCrazyPawn extends AbilityNoInfo {
    public AbilityCrazyPawn() {
        super("crazy_pawn", 0, 0);
    }

    @Override
    public ActionResult canUse(AbstractBoard board, Piece piece, Point start, Info info) {
        return ActionResult.fromBoolean(this.commonCanUse(board, piece) && board instanceof Board);
    }

    @Override
    public void use(AbstractBoard board, Piece piece, Point start, Info info) {
        var b = (Board) board;
        final Player player = b.getClock().turnOf();
        b.removePiece(start);
        b.getClock().addEvent(Event.create(EventInfo.of(b, 1, this.data.getName()), () -> {
            player.takeCard(b.getClock());
            player.takeCard(b.getClock());
        }));
        
    }
}
