package com.carlettos.game.gameplay.piece.type;

import com.carlettos.game.util.enums.Action;
import com.carlettos.game.util.enums.ActionResult;

public class Heroic extends PieceType {
    @Override
    public ActionResult canBe(PieceTypeData data) {
        if (data.action().equals(Action.ATTACK)) { return ActionResult.FAIL; }
        return ActionResult.PASS;
    }
}
