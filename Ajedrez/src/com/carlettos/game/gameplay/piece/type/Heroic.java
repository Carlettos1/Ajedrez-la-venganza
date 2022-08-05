package com.carlettos.game.gameplay.piece.type;

import com.carlettos.game.util.enums.Action;

public class Heroic extends PieceType {
    @Override
    public boolean canBe(PieceTypeData data) {
        if (data.action().equals(Action.ATTACK)) { return false; }
        return true;
    }
}
