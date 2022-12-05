package com.carlettos.game.gameplay.ability.demonic;

import java.util.List;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.board.clock.Time;
import com.carlettos.game.gameplay.ability.Ability;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.gameplay.pattern.Patterns;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.gameplay.piece.property.Properties;
import com.carlettos.game.gameplay.piece.type.IPieceType;
import com.carlettos.game.util.Point;

public class NecromancerAbility extends Ability<Point> {

    public NecromancerAbility() {
        super("necromancer", Time.lap(4), 1);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void use(AbstractBoard board, Point start, Info info) {
        Piece piece = board.getPiece(start);
        Point other = info.getPointOrSubPoint();
        piece.getPropertyManager().get(Properties.GENERIC_LIST).add(board.getPiece(other));
        board.getPiece(other).setColor(board.get(start).getPieceColor());
        board.getPiece(other).getTypeManager().addType(IPieceType.DEAD);
    }

    @Override
    public boolean checkTypes(Info info) {
        return info.isType(Point.class);
    }

    @Override
    public boolean reducedCanUse(AbstractBoard board, Point start, Point info) {
        return Patterns.CANNON_ATTACK_PATTERN.match(board, start, info) && board.get(info).hasPiece()
                && !board.get(info).isControlledBy(board.get(start).getPieceColor());
    }

    @Override
    public List<Point> getInfos(AbstractBoard board) {
        return board.stream().map(e -> e.getPos()).toList();
    }
}
