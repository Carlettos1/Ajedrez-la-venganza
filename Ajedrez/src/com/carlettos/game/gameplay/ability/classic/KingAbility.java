package com.carlettos.game.gameplay.ability.classic;

import java.util.List;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.board.clock.Time;
import com.carlettos.game.gameplay.ability.Ability;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.gameplay.piece.classic.King;
import com.carlettos.game.util.Point;

public class KingAbility extends Ability<Point> {
    public static final double TP_RANGE = 5.0D;

    public KingAbility() {
        super("king", Time.ZERO, 2);
    }

    @Override
    public void use(AbstractBoard board, Point start, Info info) {
        var king = (King) board.getPiece(start);
        var point = (Point) info.getValue();

        king.setUsedTP(true);
        board.set(point, king);
        board.remove(start, false);
        this.commonUse(board, start);
    }

    @Override
    public boolean checkTypes(Info info) {
        return info.isType(Point.class);
    }

    @Override
    public boolean reducedCanUse(AbstractBoard board, Point start, Point info) {
        if (board.getPiece(start) instanceof King k) {
            return !k.hasUsedTP() && !board.get(info).hasPiece() && start.getDistanceTo(info) <= TP_RANGE;
        }
        return false;
    }

    @Override
    public List<Point> getInfos(AbstractBoard board) {
        return board.stream().map(e -> e.getPos()).toList();
    }
}
