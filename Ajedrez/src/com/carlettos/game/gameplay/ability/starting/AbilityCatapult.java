package com.carlettos.game.gameplay.ability.starting;

import java.util.ArrayList;
import java.util.List;

import com.carlettos.game.board.AbstractSquareBoard;
import com.carlettos.game.gameplay.ability.Ability;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.gameplay.piece.type.PieceType;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.Tuple;
import com.carlettos.game.util.enums.ActionResult;
import com.carlettos.game.util.enums.Direction;
import com.carlettos.game.util.enums.Direction.SubDirection;

@SuppressWarnings("unchecked")
public class AbilityCatapult extends Ability {
    public static final int RANGE = 6;

    public AbilityCatapult() {
        super("catapult", 5, 0);
    }

    @Override
    public ActionResult canUse(AbstractSquareBoard board, Piece piece, Point start, Info info) {
        if (!this.commonCanUse(board, piece) || !info.isTupleType(Direction.class, SubDirection.class))
            return ActionResult.FAIL;
        return reducedCan(board, start, (Tuple<Direction, SubDirection>) info.getValue());
    }

    @Override
    public void use(AbstractSquareBoard board, Piece piece, Point start, Info info) {
        Tuple<Direction, SubDirection> tuple = (Tuple<Direction, SubDirection>) info.getValue();
        Point posPiece = start.add(tuple.y.toPoint());
        Point newPos = this.getEndPoint(board, start, tuple);
        board.setPiece(newPos, board.getPiece(posPiece));
        board.removePieceNoDeath(posPiece);
        this.commonUse(board, piece);
    }

    @Override
    public Tuple<Direction, Integer>[] getValues(AbstractSquareBoard board, Point start) {
        Direction[] dirs = Direction.values();
        SubDirection[] subDirs = SubDirection.values();
        List<Tuple<Direction, SubDirection>> valores = new ArrayList<>(dirs.length * subDirs.length);
        for (Direction dir : dirs) {
            for (SubDirection subDir : subDirs) {
                if (reducedCan(board, start, new Tuple<>(dir, subDir)).isPositive()) {
                    valores.add(new Tuple<>(dir, subDir));
                }
            }
        }
        return valores.toArray(Tuple[]::new);
    }

    private ActionResult reducedCan(AbstractSquareBoard board, Point start, Tuple<Direction, SubDirection> tuple) {
        Point posPiece = start.add(tuple.y.toPoint());
        if (board.getShape().isOutOfBorders(getEndPoint(board, start, tuple))) {
            return ActionResult.FAIL;
        }
        if (board.shape.isOutOfBorders(posPiece) || !board.getEscaque(posPiece).hasPiece() || !board.getPiece(posPiece).getTypeManager().isType(PieceType.TRANSPORTABLE)) { return ActionResult.FAIL; }
        return ActionResult.PASS;
    }
    
    /**
     * Returns a oob point in case that there's no end point.
     */
    private Point getEndPoint(AbstractSquareBoard board, Point start, Tuple<Direction, SubDirection> tuple) {
        var ray = board.rayCast(start, tuple.x, RANGE);
        ray = ray.stream().filter(e -> !e.hasPiece()).toList();
        if (ray.isEmpty()) {
            return new Point(-1, -1);
        }
        return ray.get(ray.size() - 1).getPos();
    }
}
