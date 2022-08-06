package com.carlettos.game.gameplay.piece.type;

import com.carlettos.game.board.AbstractSquareBoard;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.annotation.Nullable;
import com.carlettos.game.util.enums.Action;
import com.carlettos.game.util.helper.LogManager;

//TODO: reduce incoming information, pieces not needed
public record PieceTypeData(Action action, AbstractSquareBoard board, Piece self, @Nullable Piece other, Point selfPos,
        @Nullable Point otherPos) {
    public PieceTypeData(Action action, AbstractSquareBoard board, Piece self, Point selfPos) {
        this(action, board, self, null, selfPos, null);
    }

    public boolean hasOther() {
        return !(other == null || otherPos == null);
    }

    public PieceTypeData getOtherData() {
        if (this.hasOther()) {
            return new PieceTypeData(action, board, other, self, otherPos, selfPos);
        } else {
            LogManager.severe("Trying to get other view with no other piece in %s", this.getClass());
            return this;
        }
    }
}
