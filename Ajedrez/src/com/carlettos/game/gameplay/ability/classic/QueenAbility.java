package com.carlettos.game.gameplay.ability.classic;

import java.util.List;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.board.clock.Time;
import com.carlettos.game.gameplay.ability.Ability;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.gameplay.pattern.Pattern;
import com.carlettos.game.gameplay.pattern.Patterns;
import com.carlettos.game.util.Point;

public class QueenAbility extends Ability<Point> {
    public static final Pattern PATTERN = Patterns.KNIGHT_PATTERN;

    public QueenAbility() {
        super("queen", Time.lap(5), 0);
    }

    @Override
    public void use(AbstractBoard board, Point start, Info info) {
        Point point = (Point) info.getValue();
        if (board.get(point).hasPiece()) {
            board.remove(point, true);
        }
        board.set(point, board.getPiece(start));
        board.remove(start, false);
        this.commonUse(board, start);
    }

    @Override
    public boolean checkTypes(Info info) {
        return info.isType(Point.class);
    }

    @Override
    public boolean reducedCanUse(AbstractBoard board, Point start, Point info) {
        return PATTERN.match(board, start, info);
    }

    @Override
    public List<Point> getInfos(AbstractBoard board) {
        return board.stream().map(e -> e.getPos()).toList();
    }
}
