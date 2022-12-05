package com.carlettos.game.gameplay.ability.starting;

import java.util.List;
import java.util.function.Function;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.board.clock.Time;
import com.carlettos.game.gameplay.ability.Ability;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.gameplay.piece.starting.Wall;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.Color;
import com.carlettos.game.util.enums.Direction;

public class BuilderAbility extends Ability<Direction> {
    protected final Function<Color, Wall> constructor = Wall::new;

    public BuilderAbility() {
        super("builder", Time.lap(10), 0);
    }

    @Override
    public void use(AbstractBoard board, Point start, Info info) {
        Point p1 = new Point(-1, -1);
        Point p2 = new Point(-1, -1);
        Point p3 = new Point(-1, -1);

        switch ((Direction) info.getValue()) {
            case N -> {
                p1 = start.add(1, 1);
                p2 = start.add(0, 1);
                p3 = start.add(-1, 1);
            }
            case S -> {
                p1 = start.add(1, -1);
                p2 = start.add(0, -1);
                p3 = start.add(-1, -1);
            }
            case E -> {
                p1 = start.add(1, 1);
                p2 = start.add(1, 0);
                p3 = start.add(1, -1);
            }
            case W -> {
                p1 = start.add(-1, 1);
                p2 = start.add(-1, 0);
                p3 = start.add(-1, -1);
            }
        }
        if (board.contains(p1)) {
            board.get(p1).setPieceIfEmpty(constructor.apply(board.getPiece(start).getColor()));
        }
        if (board.contains(p2)) {
            board.get(p2).setPieceIfEmpty(constructor.apply(board.getPiece(start).getColor()));
        }
        if (board.contains(p3)) {
            board.get(p3).setPieceIfEmpty(constructor.apply(board.getPiece(start).getColor()));
        }
        this.commonUse(board, start);
    }

    @Override
    public boolean checkTypes(Info info) {
        return info.isType(Direction.class);
    }

    @Override
    public boolean reducedCanUse(AbstractBoard board, Point start, Direction info) {
        return true;
    }

    @Override
    public List<Direction> getInfos(AbstractBoard board) {
        return List.of(Direction.values());
    }
}
