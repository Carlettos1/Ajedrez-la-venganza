package com.carlettos.game.gameplay.effect;

import com.carlettos.game.board.AbstractSquareBoard;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.util.Point;

public class IceEffect extends Effect {

    public IceEffect(int maxTurns) {
        super("ice", maxTurns);
    }

    @Override
    public void onExpire(AbstractSquareBoard board, Point start, Piece piece) {
        // do nothing
    }

    @Override
    public void onTick(AbstractSquareBoard board, Point start, Piece piece) {
        piece.setIsMoved(true);
    }
}
