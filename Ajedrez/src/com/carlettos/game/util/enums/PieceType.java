package com.carlettos.game.util.enums;

import com.carlettos.game.board.SquareBoard;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.function.QuadFunction;

/**
 * Piece type, can be added or removed from a piece in mid game. They serve as a
 * way to add characteristics to a piece.
 *
 * @author Carlos
 */
public enum PieceType {
    BIOLOGIC(QuadFunction.PASS, QuadFunction.PASS, QuadFunction.TRUE, QuadFunction.TRUE),
    STRUCTURE(QuadFunction.PASS, QuadFunction.PASS, QuadFunction.TRUE, QuadFunction.TRUE),
    IMMUNE(QuadFunction.PASS, QuadFunction.PASS, QuadFunction.TRUE, QuadFunction.TRUE),
    HEROIC(QuadFunction.PASS, QuadFunction.PASS, QuadFunction.TRUE, QuadFunction.TRUE),
    TRANSPORTABLE(QuadFunction.PASS, QuadFunction.PASS, QuadFunction.TRUE, QuadFunction.TRUE),
    DEMONIC(QuadFunction.PASS, QuadFunction.PASS, QuadFunction.TRUE, QuadFunction.TRUE);

    private final QuadFunction<ActionResult> can;
    private final QuadFunction<ActionResult> on;
    private final QuadFunction<Boolean> canBe;
    private final QuadFunction<Boolean> onBe;

    private PieceType(QuadFunction<ActionResult> can, QuadFunction<ActionResult> on, QuadFunction<Boolean> canBe,
            QuadFunction<Boolean> onBe) {
        this.can = can;
        this.on = on;
        this.canBe = canBe;
        this.onBe = onBe;
    }

    public ActionResult canAction(Action action, SquareBoard board, Piece piece, Point start, Point end) {
        return can.apply(action, board, piece, start, end);
    }

    public ActionResult onAction(Action action, SquareBoard board, Piece piece, Point start, Point end) {
        return on.apply(action, board, piece, start, end);
    }

    public boolean canBeAction(Action action, SquareBoard board, Piece piece, Point start, Point end) {
        return canBe.apply(action, board, piece, start, end);
    }

    public boolean onBeAction(Action action, SquareBoard board, Piece piece, Point start, Point end) {
        return onBe.apply(action, board, piece, start, end);
    }
}
