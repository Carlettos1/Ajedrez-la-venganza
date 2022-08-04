package com.carlettos.game.gameplay.piece.type;

import com.carlettos.game.util.enums.ActionResult;

public abstract class PieceType implements IPieceType {

    protected PieceType() {}

    @Override
    public ActionResult can(PieceTypeData data) {
        return ActionResult.PASS;
    }

    @Override
    public void on(PieceTypeData data) {}

    @Override
    public ActionResult canBe(PieceTypeData data) {
        return ActionResult.PASS;
    }

    @Override
    public void onBe(PieceTypeData data) {}
}
