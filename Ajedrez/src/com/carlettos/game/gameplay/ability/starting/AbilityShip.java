package com.carlettos.game.gameplay.ability.starting;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.gameplay.ability.AbilityNoInfo;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.ActionResult;

public class AbilityShip extends AbilityNoInfo {

    public AbilityShip() {
        super("ship", 12, 0);
    }

    @Override
    public ActionResult canUse(AbstractBoard board, Piece piece, Point start, Info info) {
        return ActionResult.fromBoolean(this.commonCanUse(board, piece));
    }

    @Override
    public void use(AbstractBoard board, Piece piece, Point start, Info info) {
        Point[] puntos = new Point[]{start.add(1, 1), start.add(1, 0), start.add(1, -1),
                                   start.add(-1, 1), start.add(-1, 0), start.add(-1, -1)};
        for (Point point : puntos) {
            if(!board.isOutOfBorder(point)){
                board.removePiece(point);
            }
        }
        this.commonUse(board, piece);
    }
}
