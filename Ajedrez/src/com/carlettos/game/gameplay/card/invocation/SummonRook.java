package com.carlettos.game.gameplay.card.invocation;

import com.carlettos.game.gameplay.card.SummonPiece;
import com.carlettos.game.gameplay.piece.classic.Rook;

public class SummonRook extends SummonPiece<Rook> {

    public SummonRook() {
        super("rook", 0, Rook::new);
    }
}
