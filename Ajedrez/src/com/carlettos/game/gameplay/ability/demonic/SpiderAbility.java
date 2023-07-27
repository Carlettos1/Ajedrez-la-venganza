package com.carlettos.game.gameplay.ability.demonic;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.board.clock.Time;
import com.carlettos.game.gameplay.ability.NoInfoAbility;
import com.carlettos.game.gameplay.piece.demonic.SpiderEgg;
import com.carlettos.game.util.Point;

public class SpiderAbility extends NoInfoAbility {

    public SpiderAbility() {
        super("spider", Time.lap(12), 1);
    }

    @Override
    public boolean reducedCanUse(AbstractBoard board, Point start) {
        Point p = start.add(0, 1);
        if (!board.contains(p) || board.get(p).hasPiece()) {
            return false;
        }
        return true;
    }

    @Override
    public void use(AbstractBoard board, Point start) {
        Point p = start.add(0, 1);
        board.set(p, new SpiderEgg(board.getPiece(start).getColor()));
        this.commonUse(board, start);
    }
}
