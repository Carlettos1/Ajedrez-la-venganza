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
        if (!super.commonCanUse(board, piece)) { return ActionResult.FAIL; }

        if (!info.isType(Direction.class)) { return ActionResult.FAIL; }

        boolean can;
        var dir = (Direction) info.getValue();
        // todo: simplificar
        if (dir.isAxis(Direction.Axis.NS)) {
            if (board.shape.isOutOfBorders(start.add(0, dir.getSign()))) {
                can = false;
            } else {
                can = !board.getEscaque(start.add(0, dir.getSign())).hasPiece();
            }
        } else {
            if (board.shape.isOutOfBorders(start.add(dir.getSign(), 0))) {
                can = false;
            } else {
                can = !board.getEscaque(start.add(dir.getSign(), 0)).hasPiece();
            }
        }
        return ActionResult.fromBoolean(can);
    }

    @Override
    public void use(AbstractSquareBoard board, Piece piece, Point start, Info info) {
        var dir = (Direction) info.getValue();
        if (dir.isAxis(Direction.Axis.NS)) {
            board.getEscaque(start.add(0, dir.getSign())).setPiece(piece);
        } else {
            board.getEscaque(start.add(dir.getSign(), 0)).setPiece(piece);
        }
        board.getEscaque(start).removePiece();
        this.commonUse(board, piece);
    }

    @Override
    public Object[] getValues(AbstractSquareBoard board, Point start) {
        List<Direction> values = new ArrayList<>(4);
        for (Direction direction : Direction.values()) {
            boolean can;

            if (direction.isAxis(Direction.Axis.NS)) {
                if (board.shape.isOutOfBorders(start.add(0, direction.getSign()))) {
                    can = false;
                } else {
                    can = !board.getEscaque(start.add(0, direction.getSign())).hasPiece();
                }
            } else {
                if (board.shape.isOutOfBorders(start.add(direction.getSign(), 0))) {
                    can = false;
                } else {
                    can = !board.getEscaque(start.add(direction.getSign(), 0)).hasPiece();
                }
            }
            if (can) {
                values.add(direction);
            }
        }
        return values.toArray(Direction[]::new);
    }

}
