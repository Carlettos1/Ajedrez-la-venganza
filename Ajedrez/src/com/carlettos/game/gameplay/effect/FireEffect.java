package com.carlettos.game.gameplay.effect;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.util.Point;

public class FireEffect extends Effect {

    public FireEffect(int maxTurns) {
        super("fire", maxTurns);
    }

    @Override
    public void onExpire(AbstractBoard board, Point piecePos) {
        board.remove(piecePos, true);
    }

    @Override
    public void onTick(AbstractBoard board, Point piecePos) {}
}
