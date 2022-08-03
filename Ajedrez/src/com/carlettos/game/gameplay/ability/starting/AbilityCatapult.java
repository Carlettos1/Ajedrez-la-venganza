package com.carlettos.game.gameplay.ability.starting;

import java.util.ArrayList;
import java.util.List;

import com.carlettos.game.board.AbstractSquareBoard;
import com.carlettos.game.gameplay.ability.Ability;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.Tuple;
import com.carlettos.game.util.enums.ActionResult;
import com.carlettos.game.util.enums.Direction;
import com.carlettos.game.util.helper.MathHelper;

@SuppressWarnings("unchecked")
public class AbilityCatapult extends Ability {
    public AbilityCatapult() {
        super("catapult", 5, 0);
    }

    @Override
    public ActionResult canUse(AbstractSquareBoard board, Piece piece, Point start, Info info) {
        if (!this.commonCanUse(board, piece) || !info.isTupleType(Direction.class, Integer.class)) { return ActionResult.FAIL; }

        return reducedCan(board, start, (Tuple<Direction, Integer>) info.getValue());
    }

    @Override
    public void use(AbstractSquareBoard board, Piece piece, Point start, Info info) {
        Tuple<?, ?> tuple = (Tuple<?, ?>) info.getValue();
        var dir = (Direction) tuple.x;
        var i = (Integer) tuple.y;

        Point posPiece = switch (i) {
            case 1 -> new Point(start.x - 1, start.y - 1);
            case 2 -> new Point(start.x, start.y - 1);
            case 3 -> new Point(start.x + 1, start.y - 1);
            case 4 -> new Point(start.x - 1, start.y);
            case 6 -> new Point(start.x + 1, start.y);
            case 7 -> new Point(start.x - 1, start.y + 1);
            case 8 -> new Point(start.x, start.y + 1);
            case 9 -> new Point(start.x + 1, start.y + 1);
            default -> throw new IllegalArgumentException("Lanzamiento de catapulta inválido");
        };
        if (dir.isAxis(Direction.Axis.EW)) {
            int x = dir.getSign() * 6 + start.x;
            x = MathHelper.clamp(0, board.shape.x - 1, x);
            board.setPiece(new Point(x, start.y), board.getEscaque(posPiece).getPiece());
            board.removePiece(posPiece);
        } else {
            int y = dir.getSign() * 6 + start.y;
            y = MathHelper.clamp(0, board.shape.y - 1, y);
            board.setPiece(new Point(start.x, y), board.getEscaque(posPiece).getPiece());
            board.removePiece(posPiece);
        }
        this.commonUse(board, piece);
    }

    @Override
    public Tuple<Direction, Integer>[] getValues(AbstractSquareBoard board, Point start) {
        Direction[] dirs = Direction.values();
        Integer[] nums = { 1, 2, 3, 4, 6, 7, 8, 9 };
        List<Tuple<Direction, Integer>> valores = new ArrayList<>(dirs.length * nums.length);
        for (Direction dir : dirs) {
            for (Integer num : nums) {
                if (reducedCan(board, start, new Tuple<>(dir, num)).isPositive()) {
                    valores.add(new Tuple<>(dir, num));
                }
            }
        }
        return valores.toArray(Tuple[]::new);
    }

    private ActionResult reducedCan(AbstractSquareBoard board, Point start, Tuple<Direction, Integer> tuple) {
        Point posPiece = switch (tuple.y) {
            case 1 -> new Point(start.x - 1, start.y - 1);
            case 2 -> new Point(start.x, start.y - 1);
            case 3 -> new Point(start.x + 1, start.y - 1);
            case 4 -> new Point(start.x - 1, start.y);
            case 6 -> new Point(start.x + 1, start.y);
            case 7 -> new Point(start.x - 1, start.y + 1);
            case 8 -> new Point(start.x, start.y + 1);
            case 9 -> new Point(start.x + 1, start.y + 1);
            default -> new Point(-1, -1);
        };

        if (board.shape.isOutOfBorders(posPiece)) { return ActionResult.FAIL; }

        int num = tuple.y;
        if (num >= 1 && num <= 9 && num != 5) { return ActionResult.PASS; }

        return ActionResult.FAIL;
    }
}
