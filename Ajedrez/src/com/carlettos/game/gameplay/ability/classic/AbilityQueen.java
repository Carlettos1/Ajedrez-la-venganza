package com.carlettos.game.gameplay.ability.classic;

import com.carlettos.game.board.AbstractSquareBoard;
import com.carlettos.game.gameplay.ability.Ability;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.gameplay.pattern.Pattern;
import com.carlettos.game.gameplay.pattern.Patterns;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.ActionResult;

public class AbilityQueen extends Ability {
    public static final Pattern PATTERN = Patterns.KNIGHT_PATTERN;

    public AbilityQueen() {
        super("queen", 5, 0);
    }

    @Override
    public ActionResult canUse(AbstractSquareBoard board, Piece piece, Point start, Info info) {
        if (!this.commonCanUse(board, piece) || !info.isType(Point.class)
                || !PATTERN.match(board, start, (Point) info.getValue())) {
            return ActionResult.FAIL;
        }
        return ActionResult.PASS;
    }

    @Override
    public void use(AbstractSquareBoard board, Piece piece, Point start, Info info) {
        Point point = (Point) info.getValue();
        if (board.getEscaque(point).hasPiece()) {
            board.killPiece(point);
        }
        board.setPiece(point, piece);
        board.removePieceNoDeath(start);
        this.commonUse(board, piece);
    }

    @Override
    public Point[] getValues(AbstractSquareBoard board, Point start) {
        return board.getMatchingEscaques(PATTERN, start).stream().map(e -> e.getPos()).toArray(Point[]::new);
    }
}
