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
import com.carlettos.game.util.enums.Direction.SubDirection;
import com.carlettos.game.util.helper.MathHelper;

@SuppressWarnings("unchecked")
public class AbilityCatapult extends Ability {
    public static final int RANGE = 6;

    public AbilityCatapult() {
        super("catapult", 5, 0);
    }

    @Override
    public ActionResult canUse(AbstractSquareBoard board, Piece piece, Point start, Info info) {
        if (!this.commonCanUse(board, piece) || !info.isTupleType(Direction.class, SubDirection.class)
                || board.getEscaque(((Tuple<Direction, SubDirection>) info.getValue()).x.toPoint().add(start)).hasPiece())
            return ActionResult.FAIL;
        return reducedCan(board, start, (Tuple<Direction, SubDirection>) info.getValue());
    }

    @Override
    public void use(AbstractSquareBoard board, Piece piece, Point start, Info info) {
        Tuple<Direction, SubDirection> tuple = (Tuple<Direction, SubDirection>) info.getValue();
        var dir = tuple.x;
        Point posPiece = start.add(tuple.y.toPoint());
        Point newPos = MathHelper.clamp(new Point(), board.shape.getDimensions().add(-1, -1),
                start.add(dir.toPoint().scale(RANGE)));
        board.setPiece(newPos, board.getPiece(posPiece));
        // TODO: check if it kill some piece
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
        if (board.shape.isOutOfBorders(posPiece)) { return ActionResult.FAIL; }
        return ActionResult.PASS;
    }
}
