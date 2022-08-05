package com.carlettos.game.gameplay.piece.type;

import com.carlettos.game.util.enums.Action;

public class Inmune extends PieceType {
    @Override
    public boolean canBe(PieceTypeData data) {
        if (data.action().equals(Action.ABILITY)) { return false; }
        return true;
    }
}
