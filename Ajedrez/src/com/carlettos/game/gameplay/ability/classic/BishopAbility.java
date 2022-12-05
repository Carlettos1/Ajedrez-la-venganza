package com.carlettos.game.gameplay.ability.classic;

import java.util.List;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.board.clock.Time;
import com.carlettos.game.gameplay.ability.Ability;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.Direction;

public class BishopAbility extends Ability<Direction> {

    public BishopAbility() {
        super("bishop", Time.lap(2), 0);
    }

    @Override
    public void use(AbstractBoard board, Point start, Info info) {
        var dir = (Direction) info.getValue();
        board.set(start.add(dir.toPoint()), board.getPiece(start));
        board.remove(start, false);
        this.commonUse(board, start);
    }

    @Override
    public boolean checkTypes(Info info) {
        return info.isType(Direction.class);
    }

    @Override
    public boolean reducedCanUse(AbstractBoard board, Point start, Direction info) {
        var to = start.add(info.toPoint());
        return board.contains(to) && !board.get(to).hasPiece();
    }

    @Override
    public List<Direction> getInfos(AbstractBoard board) {
        return List.of(Direction.values());
    }
}
