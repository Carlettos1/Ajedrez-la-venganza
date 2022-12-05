package com.carlettos.game.gameplay.piece.type;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.gameplay.piece.property.Properties;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.Action;

public class Tough extends PieceType {

    @Override
    public void onBe(PieceTypeData data) {
        Piece piece = data.board().getPiece(data.selfPos());
        if (!piece.getPropertyManager().has(Properties.MAX_TAKEN_TIMES)) { return; }
        if (!piece.getPropertyManager().has(Properties.TAKEN_TIMES)) {
            piece.getPropertyManager().add(Properties.TAKEN_TIMES, 0);
        }
        if (data.action().equals(Action.TAKE)) {
            final int takenTimes = piece.getPropertyManager().get(Properties.TAKEN_TIMES) + 1;
            piece.getPropertyManager().add(Properties.TAKEN_TIMES, takenTimes);
            final int maxTakenTimes = piece.getPropertyManager().get(Properties.MAX_TAKEN_TIMES);
            if (takenTimes == maxTakenTimes) { return; }
            AbstractBoard board = data.board();
            Point self = data.selfPos();
            Point other = data.otherPos();
            Point newSelf = board.getThrowPoint(other, self);
            board.set(newSelf, piece);
        }
    }
}
