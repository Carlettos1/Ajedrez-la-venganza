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
    public static final double TP_RANGE = 5.0D;
    public AbilityKing() {
        super("king", 0, 2);
    }

    @Override
    public ActionResult canUse(AbstractSquareBoard board, Piece piece, Point start, Info info) {
        if (!(piece instanceof King) || !info.isType(Point.class)) { return ActionResult.FAIL; }

        var king = (King) piece;
        var point = (Point) info.getValue();

        if (king.hasUsedTP()) { return ActionResult.FAIL; }

        return ActionResult.fromBoolean(point.getDistanceTo(start) <= TP_RANGE);
    }

    @Override
    public void use(AbstractSquareBoard board, Piece piece, Point start, Info info) {
        var king = (King) piece;
        var point = (Point) info.getValue();

        king.setUsedTP(true);
        board.getEscaque(point).setPiece(piece);
        board.removePieceNoDeath(start);
    }

    @Override
    public Point[] getValues(AbstractSquareBoard board, Point start) {
        List<Point> values = new ArrayList<>();
        for (int x = 0; x < board.shape.x; x++) {
            for (int y = 0; y < board.shape.y; y++) {
                if (new Point(x, y).getDistanceTo(start) <= TP_RANGE && !board.getEscaque(new Point(x, y)).hasPiece()) {
                    values.add(new Point(x, y));
                }
            }
        }
        return values.toArray(Point[]::new);
    }
}
