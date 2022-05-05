package com.carlettos.game.gameplay.ability.classic;

import java.util.ArrayList;
import java.util.List;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.gameplay.ability.Ability;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.gameplay.pattern.classic.PatternKnight;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.ActionResult;

public class AbilityQueen extends Ability {
    public AbilityQueen() {
        super("queen", 5, 0);
    }

    @Override
    public ActionResult canUse(AbstractBoard board, Piece piece, Point start, Info info) {
        if (!this.commonCanUse(board, piece)) {
            return ActionResult.FAIL;
        }
        
        if (!info.isType(Point.class)) {
            return ActionResult.FAIL;
        }

        if(!new PatternKnight() {}.match(board, start, (Point) info.getValue())){
            return ActionResult.FAIL;
        }
        return ActionResult.PASS;
    }

    @Override
    public void use(AbstractBoard board, Piece piece, Point start, Info info) {
        board.getEscaque((Point) info.getValue()).setPiece(piece);
        board.removePiece(start);
        this.commonUse(board, piece);
    }

    @Override
    public Point[] getValues(AbstractBoard board, Point start) {
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
            if(!board.isOutOfBorder(start.add(v))){
                values.add(v);
            }
        }
        return values.toArray(Point[]::new);
    }
}
