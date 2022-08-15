package com.carlettos.game.gameplay.ability.starting;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.gameplay.ability.AbilityNoInfo;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.util.Point;

public class AbilityShip extends AbilityNoInfo {

    public AbilityShip() {
        super("ship", 12, 0);
    }

    @Override
    public boolean canUse(AbstractBoard board, Piece piece, Point start) {
        return (this.commonCanUse(board, piece));
    }

    @Override
    public void use(AbstractBoard board, Piece piece, Point start) {
        Point[] puntos = new Point[] { start.add(1, 1), start.add(1, 0), start.add(1, -1), start.add(-1, 1),
                start.add(-1, 0), start.add(-1, -1) };
        for (Point point : puntos) {
            if (board.contains(point)) {
                board.remove(point, true);
            }
        }
        this.commonUse(board, piece);
    }
}
