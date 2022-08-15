package com.carlettos.game.gameplay.ability.starting;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.gameplay.ability.AbilityNoInfo;
import com.carlettos.game.util.Point;

public class AbilityShip extends AbilityNoInfo {

    public AbilityShip() {
        super("ship", 12, 0);
    }

    @Override
    public boolean reducedCanUse(AbstractBoard board, Point start) {
        return true;
    }

    @Override
    public void use(AbstractBoard board, Point start) {
        Point[] puntos = new Point[] { start.add(1, 1), start.add(1, 0), start.add(1, -1), start.add(-1, 1),
                start.add(-1, 0), start.add(-1, -1) };
        for (Point point : puntos) {
            if (board.contains(point)) {
                board.remove(point, true);
            }
        }
        this.commonUse(board, start);
    }
}
