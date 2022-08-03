package com.carlettos.game.gameplay.ability.classic;

import java.util.ArrayList;
import java.util.List;

import com.carlettos.game.board.AbstractSquareBoard;
import com.carlettos.game.gameplay.ability.Ability;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.ActionResult;
import com.carlettos.game.util.enums.Direction;

public class AbilityBishop extends Ability {

    public AbilityBishop() {
        super("bishop", 2, 0);
    }

    @Override
    public ActionResult canUse(AbstractSquareBoard board, Piece piece, Point start, Info info) {
        if (!super.commonCanUse(board, piece) || !info.isType(Direction.class)) { return ActionResult.FAIL; }
        var dir = (Direction) info.getValue();
        return ActionResult.fromBoolean(this.reducedCanUse(board, start, dir));
    }

    @Override
    public void use(AbstractSquareBoard board, Piece piece, Point start, Info info) {
        var dir = (Direction) info.getValue();
        board.setPiece(start.add(dir.toPoint()), piece);
        board.getEscaque(start).removePiece();
        this.commonUse(board, piece);
    }

    @Override
    public Object[] getValues(AbstractSquareBoard board, Point start) {
        List<Direction> values = new ArrayList<>(4);
        for (Direction direction : Direction.values()) {
            if (this.reducedCanUse(board, start, direction)) {
                values.add(direction);
            }
        }
        return values.toArray(Direction[]::new);
    }
    
    protected boolean reducedCanUse(AbstractSquareBoard board, Point start, Direction dir) {
        var to = start.add(dir.toPoint());
        return !board.shape.isOutOfBorders(to) && !board.getEscaque(to).hasPiece();
    }

}
