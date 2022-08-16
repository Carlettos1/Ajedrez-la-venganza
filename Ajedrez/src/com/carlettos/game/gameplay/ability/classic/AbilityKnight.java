package com.carlettos.game.gameplay.ability.classic;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.gameplay.ability.AbilityNoInfo;
import com.carlettos.game.gameplay.piece.classic.Pawn;
import com.carlettos.game.util.Point;

public class AbilityKnight extends AbilityNoInfo {
    public AbilityKnight() {
        super("knight", 10, 1);
    }

    @Override
    public boolean reducedCanUse(AbstractBoard board, Point start) {
        Point p1 = start.add(1, 0);
        Point p2 = start.add(-1, 0);
        if (!board.contains(p1) || !board.contains(p2)) { return false; }
        if (board.get(p1).hasPiece() || board.get(p2).hasPiece()) { return false; }
        return true;
    }

    @Override
    public void use(AbstractBoard board, Point start) {
        Point p1 = start.add(1, 0);
        Point p2 = start.add(-1, 0);
        board.set(p1, new Pawn(board.getPiece(start).getColor()));
        board.set(p2, new Pawn(board.getPiece(start).getColor()));
        this.commonUse(board, start);
    }
}
