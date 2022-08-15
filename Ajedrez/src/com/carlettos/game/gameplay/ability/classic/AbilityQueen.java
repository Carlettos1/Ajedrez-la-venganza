package com.carlettos.game.gameplay.ability.classic;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.gameplay.ability.Ability;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.gameplay.pattern.Pattern;
import com.carlettos.game.gameplay.pattern.Patterns;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.util.Point;

public class AbilityQueen extends Ability {
    public static final Pattern PATTERN = Patterns.KNIGHT_PATTERN;

    public AbilityQueen() {
        super("queen", 5, 0);
    }

    @Override
    public boolean canUse(AbstractBoard board, Piece piece, Point start, Info info) {
        if (!this.commonCanUse(board, piece) || !info.isType(Point.class)
                || !PATTERN.match(board, start, (Point) info.getValue())) {
            return false;
        }
        return true;
    }

    @Override
    public void use(AbstractBoard board, Piece piece, Point start, Info info) {
        Point point = (Point) info.getValue();
        if (board.get(point).hasPiece()) {
            board.remove(point, true);
        }
        board.set(point, piece);
        board.remove(start, false);
        this.commonUse(board, piece);
    }

    @Override
    public Point[] getValues(AbstractBoard board, Point start) {
        return board.getAll(PATTERN, start).stream().map(e -> e.getPos()).toArray(Point[]::new);
    }
}
