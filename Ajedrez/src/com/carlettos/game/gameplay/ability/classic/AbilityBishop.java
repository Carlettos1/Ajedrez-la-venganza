package com.carlettos.game.gameplay.ability.classic;

import java.util.ArrayList;
import java.util.List;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.gameplay.ability.Ability;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.Direction;

public class AbilityBishop extends Ability {

    public AbilityBishop() {
        super("bishop", 2, 0);
    }

    @Override
    public boolean canUse(AbstractBoard board, Piece piece, Point start, Info info) {
        if (!super.commonCanUse(board, piece) || !info.isType(Direction.class)) { return false; }
        var dir = (Direction) info.getValue();
        return (this.reducedCanUse(board, start, dir));
    }

    @Override
    public void use(AbstractBoard board, Piece piece, Point start, Info info) {
        var dir = (Direction) info.getValue();
        board.set(start.add(dir.toPoint()), piece);
        board.remove(start, false);
        this.commonUse(board, piece);
    }

    @Override
    public Direction[] getValues(AbstractBoard board, Point start) {
        List<Direction> values = new ArrayList<>(4);
        for (Direction direction : Direction.values()) {
            if (this.reducedCanUse(board, start, direction)) {
                values.add(direction);
            }
        }
        return values.toArray(Direction[]::new);
    }

    protected boolean reducedCanUse(AbstractBoard board, Point start, Direction dir) {
        var to = start.add(dir.toPoint());
        return board.contains(to) && !board.get(to).hasPiece();
    }

}
