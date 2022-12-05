package com.carlettos.game.gameplay.ability.starting;

import java.util.List;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.board.clock.Time;
import com.carlettos.game.gameplay.ability.Ability;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.Direction;

public class AbilityRam extends Ability<Direction> {
    public static final int COST_PER_CHARGE = 5;

    public AbilityRam() {
        super("ram", Time.lap(4), 0);
    }

    @Override
    public void use(AbstractBoard board, Point start, Info info) {
        Direction dir = (Direction) info.getValue();
        var route = board.rayCast(start, -1, false, dir, e -> e.hasPiece());
        var charge = route.size() / COST_PER_CHARGE + 1;
        var end = route.isEmpty() ? start : route.get(route.size() - 1).getPos();
        var killeable = board.rayCast(end, charge, false, dir, e -> e.getPiece().getTypeManager().isImpenetrable());
        var endCharge = end;
        if (!killeable.isEmpty()) {
            endCharge = killeable.get(killeable.size() - 1).getPos();
        }
        killeable.forEach(e -> board.remove(e, true));
        board.set(endCharge, board.getPiece(start));
        board.remove(start, false);
        this.commonUse(board, start);
    }

    @Override
    public boolean checkTypes(Info info) {
        return info.isType(Direction.class);
    }

    @Override
    public boolean reducedCanUse(AbstractBoard board, Point start, Direction info) {
        return board.contains(info.toPoint());
    }

    @Override
    public List<Direction> getInfos(AbstractBoard board) {
        return List.of(Direction.values());
    }
}
