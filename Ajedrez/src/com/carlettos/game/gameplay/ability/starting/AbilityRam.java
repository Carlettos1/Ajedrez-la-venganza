package com.carlettos.game.gameplay.ability.starting;

import java.util.Arrays;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.gameplay.ability.Ability;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.Direction;

public class AbilityRam extends Ability {
    public static final int COST_PER_CHARGE = 5;

    public AbilityRam() {
        super("ram", 4, 0);
    }

    @Override
    public boolean canUse(AbstractBoard board, Piece piece, Point start, Info info) {
        if (!this.commonCanUse(board, piece) || !info.isType(Direction.class)) { return false; }
        return (board.contains(((Direction) info.getValue()).toPoint()));
    }

    @Override
    public void use(AbstractBoard board, Piece piece, Point start, Info info) {
        Direction dir = (Direction) info.getValue();
        var route = board.rayCast(start, -1, false, dir, e -> e.hasPiece());
        var charge = route.size() / COST_PER_CHARGE + 1;
        var end = route.get(route.size() - 1).getPos();
        var killeable = board.rayCast(end, charge, false, dir, e -> e.getPiece().getTypeManager().isImpenetrable());
        var endCharge = end;
        if (!killeable.isEmpty()) {
            endCharge = killeable.get(killeable.size() - 1).getPos();
        }
        killeable.forEach(e -> board.remove(e, true));
        board.set(endCharge, piece);
        board.remove(start, false);
        this.commonUse(board, piece);
    }

    @Override
    public Direction[] getValues(AbstractBoard board, Point start) {
        return Arrays.asList(Direction.values()).stream().filter(d -> board.contains(d.toPoint()))
                .toArray(Direction[]::new);
    }
}
