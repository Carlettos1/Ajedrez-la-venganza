package com.carlettos.game.gameplay.ability.starting;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.board.clock.event.Event;
import com.carlettos.game.board.clock.event.EventInfo;
import com.carlettos.game.gameplay.ability.AbilityNoInfo;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.gameplay.player.Player;
import com.carlettos.game.util.Point;

public class AbilityCrazyPawn extends AbilityNoInfo {
    public AbilityCrazyPawn() {
        super("crazy_pawn", 0, 0);
    }

    @Override
    public boolean canUse(AbstractBoard board, Piece piece, Point start) {
        return (this.commonCanUse(board, piece));
    }

    @Override
    public void use(AbstractBoard board, Piece piece, Point start) {
        final Player player = board.getClock().turnOf();
        board.remove(start, false);
        board.getClock().addEvent(Event.create(EventInfo.of(board, 1, this.data.getName()), () -> {
            player.takeCards(board.getClock(), 2);
        }));

    }
}
