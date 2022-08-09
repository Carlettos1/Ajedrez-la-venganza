package com.carlettos.game.gameplay.ability.demonic;

import com.carlettos.game.board.AbstractSquareBoard;
import com.carlettos.game.board.clock.event.Event;
import com.carlettos.game.board.clock.event.EventInfo;
import com.carlettos.game.gameplay.ability.AbilityNoInfo;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.util.Point;

public class MandragoraAbility extends AbilityNoInfo {
    public MandragoraAbility(String key, int cooldown, int manaCost) {
        super(key, cooldown, manaCost);
    }

    @Override
    public boolean canUse(AbstractSquareBoard board, Piece piece, Point start) {
        return this.commonCanUse(board, piece);
    }

    @Override
    public void use(AbstractSquareBoard board, Piece piece, Point start) {
        var currentPlayer = board.getClock().turnOf();
        board.getClock().addEvents(Event.create(EventInfo.of(board, 1, this.data.getName()), () -> currentPlayer.changeMana(1)));
        board.getClock().addEvents(Event.create(EventInfo.of(board, 2, this.data.getName()), () -> currentPlayer.changeMana(1)));
        this.commonUse(board, piece);
    }
}
