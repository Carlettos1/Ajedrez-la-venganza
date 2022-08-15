package com.carlettos.game.gameplay.effect;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.util.Point;

public class DeactivateEffect extends Effect {

    public DeactivateEffect(int maxTurns) {
        super("deactivate", maxTurns);
    }

    @Override
    public void onExpire(AbstractBoard board, Point start, Piece piece) {
        // do nothing
    }

    @Override
    public void onTick(AbstractBoard board, Point start, Piece piece) {
        piece.setIsMoved(true);
    }
}
