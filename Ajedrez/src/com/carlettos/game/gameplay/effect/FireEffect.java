package com.carlettos.game.gameplay.effect;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.util.Point;

public class FireEffect extends Effect {

    public FireEffect(int maxTurns) {
        super("fire", maxTurns);
    }

    @Override
    public void onExpire(AbstractBoard board, Point start, Piece piece) {
        board.remove(start, true);
    }

    @Override
    public void onTick(AbstractBoard board, Point start, Piece piece) {}
}
