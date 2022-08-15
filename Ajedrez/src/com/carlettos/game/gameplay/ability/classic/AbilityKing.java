package com.carlettos.game.gameplay.ability.classic;

import java.util.ArrayList;
import java.util.List;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.gameplay.ability.Ability;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.gameplay.piece.classic.King;
import com.carlettos.game.util.Point;

public class AbilityKing extends Ability {
    public static final double TP_RANGE = 5.0D;

    public AbilityKing() {
        super("king", 0, 2);
    }

    @Override
    public boolean canUse(AbstractBoard board, Piece piece, Point start, Info info) {
        if (!(piece instanceof King) || !info.isType(Point.class)) { return false; }

        var king = (King) piece;
        var point = (Point) info.getValue();

        if (king.hasUsedTP() || board.get(point).hasPiece()) { return false; }

        return (point.getDistanceTo(start) <= TP_RANGE);
    }

    @Override
    public void use(AbstractBoard board, Piece piece, Point start, Info info) {
        var king = (King) piece;
        var point = (Point) info.getValue();

        king.setUsedTP(true);
        board.set(point, piece);
        board.remove(start, false);
    }

    @Override
    public Point[] getValues(AbstractBoard board, Point start) {
        List<Point> values = new ArrayList<>();
        board.stream().forEach(e -> {
            if (e.getPos().getDistanceTo(start) <= TP_RANGE && !e.hasPiece()) {
                values.add(e.getPos());
            }
        });
        return values.toArray(Point[]::new);
    }
}
