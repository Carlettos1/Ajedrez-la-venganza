package com.carlettos.game.gameplay.ability.starting;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.board.clock.Time;
import com.carlettos.game.board.clock.event.Event;
import com.carlettos.game.board.clock.event.EventInfo;
import com.carlettos.game.gameplay.ability.NoInfoAbility;
import com.carlettos.game.gameplay.player.Player;
import com.carlettos.game.util.Point;

public class CrazyPawnAbility extends NoInfoAbility {
    public CrazyPawnAbility() {
        super("crazy_pawn", Time.ZERO, 0);
    }

    @Override
    public void use(AbstractBoard board, Point start) {
        final Player player = board.getClock().getCurrentlyPlaying();
        board.remove(start, false);
        board.getClock().addEvent(Event.create(EventInfo.of(board, this.data.getName()), () -> {
            player.takeCards(board.getClock(), 2);
        }));

    }

    @Override
    public boolean reducedCanUse(AbstractBoard board, Point start) {
        return true;
    }
}
