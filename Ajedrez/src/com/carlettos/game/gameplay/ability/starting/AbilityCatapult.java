package com.carlettos.game.gameplay.ability.starting;

import java.util.ArrayList;
import java.util.List;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.gameplay.ability.Ability;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.Tuple;
import com.carlettos.game.util.enums.Direction;
import com.carlettos.game.util.enums.Direction.SubDirection;

@SuppressWarnings("unchecked")
public class AbilityCatapult extends Ability {
    public static final int RANGE = 6;

    public AbilityCatapult() {
        super("catapult", 5, 0);
    }

    @Override
    public boolean canUse(AbstractBoard board, Piece piece, Point start, Info info) {
        if (!this.commonCanUse(board, piece) || !info.isTupleType(Direction.class, SubDirection.class))
            return false;
        return reducedCan(board, start, (Tuple<Direction, SubDirection>) info.getValue());
    }

    @Override
    public void use(AbstractBoard board, Piece piece, Point start, Info info) {
        Tuple<Direction, SubDirection> tuple = (Tuple<Direction, SubDirection>) info.getValue();
        Point posPiece = start.add(tuple.y.toPoint());
        var ray = board.rayCast(start, RANGE, true, tuple.x, e -> true);
        Point newPos = ray.get(ray.size() - 1).getPos();
        board.set(newPos, board.getPiece(posPiece));
        board.remove(posPiece, false);
        this.commonUse(board, piece);
    }

    @Override
    public Tuple<Direction, Integer>[] getValues(AbstractBoard board, Point start) {
        Direction[] dirs = Direction.values();
        SubDirection[] subDirs = SubDirection.values();
        List<Tuple<Direction, SubDirection>> valores = new ArrayList<>(dirs.length * subDirs.length);
        for (Direction dir : dirs) {
            for (SubDirection subDir : subDirs) {
                if (reducedCan(board, start, new Tuple<>(dir, subDir))) {
                    valores.add(new Tuple<>(dir, subDir));
                }
            }
        }
        return valores.toArray(Tuple[]::new);
    }

    private boolean reducedCan(AbstractBoard board, Point start, Tuple<Direction, SubDirection> tuple) {
        Point posPiece = start.add(tuple.y.toPoint());
        if (board.rayCast(start, RANGE, true, tuple.x, e -> true).isEmpty() || !board.contains(posPiece)
                || !board.get(posPiece).hasPiece() || !board.getPiece(posPiece).getTypeManager().isTransportable()) {
            return false;
        }
        return true;
    }
}
