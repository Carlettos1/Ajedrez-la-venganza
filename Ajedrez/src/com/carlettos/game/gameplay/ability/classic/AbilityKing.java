package com.carlettos.game.gameplay.ability.classic;

import java.util.ArrayList;
import java.util.List;

import com.carlettos.game.board.AbstractSquareBoard;
import com.carlettos.game.gameplay.ability.Ability;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.gameplay.piece.classic.King;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.ActionResult;

public class AbilityKing extends Ability {
    public AbilityKing() {
        super("king", 0, 2);
    }

    @Override
    public ActionResult canUse(AbstractSquareBoard board, Piece piece, Point start, Info info) {
        if (!(piece instanceof King)) { return ActionResult.FAIL; }

        if (!info.isType(Point.class)) { return ActionResult.FAIL; }

        var king = (King) piece;
        var point = (Point) info.getValue();

        if (king.isUsedTP()) { return ActionResult.FAIL; }

        return ActionResult.fromBoolean(point.getDistanceTo(start) <= 5);
    }

    @Override
    public void use(AbstractSquareBoard board, Piece piece, Point start, Info info) {
        var king = (King) piece;
        var point = (Point) info.getValue();

        king.setUsedTP(true);
        board.getEscaque(point).setPiece(piece);
        board.getEscaque(start).removePiece();
    }

    @Override
    public Point[] getValues(AbstractSquareBoard board, Point start) {
        List<Point> values = new ArrayList<>();
        for (int x = 0; x < board.shape.x; x++) {
            for (int y = 0; y < board.shape.y; y++) {
                if (new Point(x, y).getDistanceTo(start) <= 5 && !board.getEscaque(new Point(x, y)).hasPiece()) {
                    values.add(new Point(x, y));
                }
            }
        }
        return values.toArray(Point[]::new);
    }
}
