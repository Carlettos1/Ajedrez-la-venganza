package com.carlettos.game.gameplay.effect;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.util.Point;

public class FireEffect extends Effect {

    public FireEffect() {
        super("fire", 5);
    }

    @Override
    public void onExpire(AbstractBoard board, Point start, Piece piece) {
        board.getEscaque(start).removePiece();
    }
    
    @Override
    public void onTick(AbstractBoard board, Point start, Piece piece) {
        //do nothing
    }
}
