package com.carlettos.game.gameplay.ability.demonic;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.board.clock.Time;
import com.carlettos.game.board.clock.event.Event;
import com.carlettos.game.board.clock.event.EventInfo;
import com.carlettos.game.gameplay.ability.NoInfoAbility;
import com.carlettos.game.util.Point;

public class MandragoraAbility extends NoInfoAbility {
    public MandragoraAbility() {
        super("mandragora", Time.lap(12), 0);
    }

    @Override
    public boolean reducedCanUse(AbstractBoard board, Point start) {
        return true;
    }

    @Override
    public void use(AbstractBoard board, Point start) {
        var currentPlayer = board.getClock().turnOf();
        board.getClock().addEvents(
                Event.create(EventInfo.of(board, 1, this.data.getName()), () -> currentPlayer.changeMana(1)));
        board.getClock().addEvents(
                Event.create(EventInfo.of(board, 2, this.data.getName()), () -> currentPlayer.changeMana(1)));
        this.commonUse(board, start);
    }
}
