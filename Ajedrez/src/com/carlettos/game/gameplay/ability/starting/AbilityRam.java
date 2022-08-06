package com.carlettos.game.gameplay.ability.starting;

import java.util.Arrays;
import java.util.List;

import com.carlettos.game.board.AbstractSquareBoard;
import com.carlettos.game.board.Escaque;
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
    public boolean canUse(AbstractSquareBoard board, Piece piece, Point start, Info info) {
        if (!this.commonCanUse(board, piece) || !info.isType(Direction.class)) { return false; }
        return (!board.getShape().isOutOfBorders(((Direction) info.getValue()).toPoint()));
    }

    @Override
    public void use(AbstractSquareBoard board, Piece piece, Point start, Info info) {
        Direction dir = (Direction) info.getValue();
        Point lastPreCharge = this.getEndPointNoJump(board, start, dir, -1);
        int totalCharges = (int) Math.ceil(start.getDistanceTo(lastPreCharge)) / COST_PER_CHARGE + 1;
        List<Escaque> toKill = board.rayCast(lastPreCharge, dir, totalCharges);
        Point lastPostCharge = lastPreCharge;
        if (!toKill.isEmpty()) {
            lastPostCharge = toKill.get(toKill.size() - 1).getPos();
            for (Escaque escaque : toKill) {
                board.killPiece(escaque.getPos());
            }
        }

        board.setPiece(lastPostCharge, piece);
        board.removePieceNoDeath(start);
        this.commonUse(board, piece);
    }

    @Override
    public Direction[] getValues(AbstractSquareBoard board, Point start) {
        return Arrays.asList(Direction.values()).stream().filter(d -> !board.getShape().isOutOfBorders(d.toPoint()))
                .toArray(Direction[]::new);
    }
}
