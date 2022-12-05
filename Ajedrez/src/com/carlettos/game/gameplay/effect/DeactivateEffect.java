package com.carlettos.game.gameplay.effect;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.util.Point;

public class DeactivateEffect extends Effect {

    public DeactivateEffect(int maxTurns) {
        super("deactivate", maxTurns);
    }

    @Override
    public void onExpire(AbstractBoard board, Point piecePos) {
        // do nothing
    }

    @Override
    public void onTick(AbstractBoard board, Point piecePos) {
        board.getPiece(piecePos).setIsMoved(true);
    }
}
