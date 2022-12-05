package com.carlettos.game.gameplay.piece.type;

public abstract non-sealed class PieceType implements IPieceType {

    protected PieceType() {}

    @Override
    public boolean can(PieceTypeData data) {
        return true;
    }

    @Override
    public void on(PieceTypeData data) {}

    @Override
    public boolean canBe(PieceTypeData data) {
        return true;
    }

    @Override
    public void onBe(PieceTypeData data) {}
}
