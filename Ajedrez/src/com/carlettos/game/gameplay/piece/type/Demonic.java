package com.carlettos.game.gameplay.piece.type;

import com.carlettos.game.util.enums.Action;

public class Demonic extends PieceType {
    @Override
    public void onBe(PieceTypeData data) {
        if (data.action().equals(Action.TAKE)) {
            data.board().getClock().getPlayerOfColor(data.board().getEscaque(data.selfPos()).getPieceColor())
                    .changeMana(1);
        }
    }
}
