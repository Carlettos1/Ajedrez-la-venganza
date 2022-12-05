package com.carlettos.game.gameplay.ability.demonic;

import java.util.List;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.board.clock.Time;
import com.carlettos.game.gameplay.ability.Ability;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.Direction;

public class OgreAbility extends Ability<Direction> {

    public OgreAbility() {
        super("ogre", Time.lap(4), 0);
    }

    @Override
    public void use(AbstractBoard board, Point start, Info info) {
        Direction dir = (Direction) info.getValue();
        Point other = start.add(dir.toPoint());
        Point place = board.getThrowPoint(start, other);
        if (place.equals(other)) {
            return;
        } else {
            board.set(place, board.get(other));
            board.remove(other, false);
        }
    }

    @Override
    public boolean checkTypes(Info info) {
        return info.isType(Direction.class);
    }

    @Override
    public boolean reducedCanUse(AbstractBoard board, Point start, Direction info) {
        return board.get(start.add(info.toPoint())).hasPiece();
    }

    @Override
    public List<Direction> getInfos(AbstractBoard board) {
        return List.of(Direction.values());
    }
}
