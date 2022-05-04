package com.carlettos.game.gameplay.ability.classic;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.gameplay.ability.AbilityNoInfo;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.gameplay.piece.classic.Pawn;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.ActionResult;

public class AbilityKnight extends AbilityNoInfo {
    public AbilityKnight() {
        super("Bajar Jinetes",
        "Invoca 2 peones, uno a cada lado del caballo (EW). Ambas casillas deben estar vacías",
        10,
        1);
    }

    @Override
    public ActionResult canUse(AbstractBoard board, Piece piece, Point start, Info info) {
        if (!this.commonCanUse(board, piece)) {
            return ActionResult.FAIL;
        }

        Point p1 = new Point(start.x + 1, start.y);
        Point p2 = new Point(start.x - 1, start.y);

        if (board.getEscaque(p1).hasPiece() || board.getEscaque(p2).hasPiece()) {
            return ActionResult.FAIL;
        }
        return ActionResult.PASS;
    }

    @Override
    public void use(AbstractBoard board, Piece piece, Point start, Info info) {
        Point p1 = new Point(start.x + 1, start.y);
        Point p2 = new Point(start.x - 1, start.y);
        board.getEscaque(p1).setPiece(new Pawn(piece.getColor()));
        board.getEscaque(p2).setPiece(new Pawn(piece.getColor()));
        this.commonUse(board, piece);
    }
}
