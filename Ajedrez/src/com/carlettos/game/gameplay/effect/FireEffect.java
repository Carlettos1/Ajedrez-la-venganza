package com.carlettos.game.gameplay.effect;

import com.carlettos.game.board.AbstractSquareBoard;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.util.Point;

public class FireEffect extends Effect {

    public FireEffect() {
        super("fire", 5);
    }

    @Override
    public void onExpire(AbstractSquareBoard board, Point start, Piece piece) {
        board.killPiece(start);
    }

    @Override
    public void onTick(AbstractSquareBoard board, Point start, Piece piece) {
        // do nothing
    }
}
