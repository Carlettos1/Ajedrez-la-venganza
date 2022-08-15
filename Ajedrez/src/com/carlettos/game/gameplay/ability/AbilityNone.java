package com.carlettos.game.gameplay.ability;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.util.Point;

public final class AbilityNone extends AbilityNoInfo {

    public AbilityNone() {
        super("none", 0, 0);
    }

    @Override
    public boolean reducedCanUse(AbstractBoard board, Point start) {
        return false;
    }

    @Override
    public void use(AbstractBoard board, Point start) {}
}
