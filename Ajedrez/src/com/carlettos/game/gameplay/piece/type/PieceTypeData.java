package com.carlettos.game.gameplay.piece.type;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.annotation.Nullable;
import com.carlettos.game.util.enums.Action;
import com.carlettos.game.util.helper.LogHelper;

public record PieceTypeData(Action action, AbstractBoard board, Point selfPos, @Nullable Point otherPos) {
    public PieceTypeData(Action action, AbstractBoard board, Point selfPos) {
        this(action, board, selfPos, null);
    }

    public boolean hasOther() {
        return otherPos != null;
    }

    public PieceTypeData getOtherData() {
        if (this.hasOther()) {
            return new PieceTypeData(action, board, otherPos, selfPos);
        } else {
            LogHelper.LOG.severe("Trying to get other view with no other piece in %s" + this.getClass());
            return this;
        }
    }
}
