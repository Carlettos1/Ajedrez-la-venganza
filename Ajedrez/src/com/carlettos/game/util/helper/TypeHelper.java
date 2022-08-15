package com.carlettos.game.util.helper;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.gameplay.piece.type.PieceTypeData;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.Action;

public class TypeHelper {
    public static final boolean checkIfTypesCan(Action action, AbstractBoard board, Point pos, Info info) {
        if (info.isPointOrSubPoint() && board.get(info.getPointOrSubPoint()).hasPiece()) {
            return checkIfTypesCanWithPoint(action, board, pos, info.getPointOrSubPoint());
        } else {
            return board.getPiece(pos).getTypeManager().can(new PieceTypeData(action, board, pos));
        }
    }

    public static final boolean checkIfTypesCanWithPoint(Action action, AbstractBoard board, Point pos,
            Point otherPos) {
        var posData = new PieceTypeData(action, board, pos, otherPos);
        return board.getPiece(pos).getTypeManager().can(posData)
                && (board.getPiece(otherPos).getTypeManager().canBe(posData.getOtherData()));
    }

    public static final void ActivateTypesOnAction(Action action, AbstractBoard board, Point pos, Info info) {
        if (info.isPointOrSubPoint() && board.get(info.getPointOrSubPoint()).hasPiece()) {
            ActivateTypesOnActionWithPoint(action, board, pos, info.getPointOrSubPoint());
        } else {
            board.getPiece(pos).getTypeManager().on(new PieceTypeData(action, board, pos));
        }
    }

    public static final void ActivateTypesOnActionWithPoint(Action action, AbstractBoard board, Point pos,
            Point otherPos) {
        var posData = new PieceTypeData(action, board, pos, otherPos);
        board.getPiece(pos).getTypeManager().on(posData);
        board.getPiece(otherPos).getTypeManager().onBe(posData.getOtherData());
    }
}
