package com.carlettos.game.gameplay.ability.classic;

import java.util.ArrayList;
import java.util.List;

import com.carlettos.game.board.AbstractSquareBoard;
import com.carlettos.game.gameplay.ability.Ability;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.gameplay.pattern.Patterns;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.ActionResult;

public class AbilityQueen extends Ability {
    public AbilityQueen() {
        super("queen", 5, 0);
    }

    @Override
    public ActionResult canUse(AbstractSquareBoard board, Piece piece, Point start, Info info) {
        if (!this.commonCanUse(board, piece) || !info.isType(Point.class) || !Patterns.KNIGHT_PATTERN.match(board, start, (Point) info.getValue())) { return ActionResult.FAIL; }
        return ActionResult.PASS;
    }

    @Override
    public void use(AbstractSquareBoard board, Piece piece, Point start, Info info) {
        board.getEscaque((Point) info.getValue()).setPiece(piece);
        board.removePiece(start);
        this.commonUse(board, piece);
    }

    @Override
    public Point[] getValues(AbstractSquareBoard board, Point start) {
        List<Point> allValues = new ArrayList<>(8);
        List<Point> values = new ArrayList<>(8);
        allValues.add(new Point(-2, -1));
        allValues.add(new Point(-2, 1));
        allValues.add(new Point(2, -1));
        allValues.add(new Point(2, 1));
        allValues.add(new Point(-1, -2));
        allValues.add(new Point(-1, 2));
        allValues.add(new Point(1, -2));
        allValues.add(new Point(1, 2));
        for (Point v : allValues) {
            if (!board.shape.isOutOfBorders(start.add(v))) {
                values.add(v);
            }
        }
        return values.toArray(Point[]::new);
    }
}
