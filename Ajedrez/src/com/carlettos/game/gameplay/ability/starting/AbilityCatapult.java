package com.carlettos.game.gameplay.ability.starting;

import java.util.ArrayList;
import java.util.List;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.gameplay.ability.Ability;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.Tuple;
import com.carlettos.game.util.enums.Direction;
import com.carlettos.game.util.enums.Direction.SubDirection;

@SuppressWarnings("unchecked")
public class AbilityCatapult extends Ability<Tuple<Direction, SubDirection>> {
    public static final int RANGE = 6;

    public AbilityCatapult() {
        super("catapult", 5, 0);
    }

    @Override
    public void use(AbstractBoard board, Point start, Info info) {
        Tuple<Direction, SubDirection> tuple = (Tuple<Direction, SubDirection>) info.getValue();
        Point posPiece = start.add(tuple.y.toPoint());
        var ray = board.rayCast(start, RANGE, true, tuple.x, e -> true);
        Point newPos = ray.get(ray.size() - 1).getPos();
        board.set(newPos, board.getPiece(posPiece));
        board.remove(posPiece, false);
        this.commonUse(board, start);
    }

    @Override
    public boolean checkTypes(Info info) {
        return info.isTupleType(Direction.class, SubDirection.class);
    }

    @Override
    public boolean reducedCanUse(AbstractBoard board, Point start, Tuple<Direction, SubDirection> info) {
        Point posPiece = start.add(info.y.toPoint());
        if (board.rayCast(start, RANGE, true, info.x, e -> true).isEmpty() || !board.contains(posPiece)
                || !board.get(posPiece).hasPiece() || !board.getPiece(posPiece).getTypeManager().isTransportable()) {
            return false;
        }
        return true;
    }

    @Override
    public List<Tuple<Direction, SubDirection>> getInfos(AbstractBoard board) {
        List<Tuple<Direction, SubDirection>> values = new ArrayList<>(32);
        for (Direction dir : Direction.values()) {
            for (SubDirection subDir : SubDirection.values()) {
                values.add(Tuple.of(dir, subDir));
            }
        }
        return values;
    }
}
