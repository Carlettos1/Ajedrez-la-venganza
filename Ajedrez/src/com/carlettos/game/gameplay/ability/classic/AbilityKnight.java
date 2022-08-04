package com.carlettos.game.gameplay.ability.classic;

import com.carlettos.game.board.AbstractSquareBoard;
import com.carlettos.game.gameplay.ability.AbilityNoInfo;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.gameplay.piece.classic.Pawn;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.ActionResult;

public class AbilityKnight extends AbilityNoInfo {
    public AbilityKnight() {
        super("knight", 10, 1);
    }

    @Override
    public ActionResult canUse(AbstractSquareBoard board, Piece piece, Point start) {
        if (!this.commonCanUse(board, piece)) { return ActionResult.FAIL; }
        Point p1 = start.add(1, 0);
        Point p2 = start.add(-1, 0);
        if (board.getEscaque(p1).hasPiece() || board.getEscaque(p2).hasPiece()) { return ActionResult.FAIL; }
        return ActionResult.PASS;
    }

    @Override
    public void use(AbstractSquareBoard board, Piece piece, Point start) {
        Point p1 = start.add(1, 0);
        Point p2 = start.add(-1, 0);
        board.setPiece(p1, new Pawn(piece.getColor()));
        board.setPiece(p2, new Pawn(piece.getColor()));
        this.commonUse(board, piece);
    }
}
