package com.carlettos.game.gameplay.ability.starting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.carlettos.game.board.AbstractSquareBoard;
import com.carlettos.game.board.Escaque;
import com.carlettos.game.gameplay.ability.Ability;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.ActionResult;
import com.carlettos.game.util.enums.Direction;
import com.carlettos.game.util.helper.LogHelper;

public class AbilityRam extends Ability {
    public static final int COST_PER_CHARGE = 5;
    public AbilityRam() {
        super("ram", 4, 0);
    }

    @Override
    public ActionResult canUse(AbstractSquareBoard board, Piece piece, Point start, Info info) {
        if (!this.commonCanUse(board, piece) || !info.isType(Direction.class)) {
            return ActionResult.FAIL;
        }
        return ActionResult.fromBoolean(!board.getShape().isOutOfBorders(((Direction) info.getValue()).toPoint()));
    }

    @Override
    public void use(AbstractSquareBoard board, Piece piece, Point start, Info info) {
        Direction dir = (Direction) info.getValue();
        List<Escaque> ray = board.rayCast(start, dir);
        List<Escaque> valids = new ArrayList<>(ray.size());
        for (Escaque r : ray) {
            if (r.hasPiece()) {
                break;
            }
            valids.add(r);
        }
        if (valids.isEmpty()) {
            LogHelper.warning("Valids is empty, and it shouldn't");
            return;
        }
        int totalCharges = valids.size() / COST_PER_CHARGE + 1;
        Point lastPreCharge = valids.get(valids.size() - 1).getPos();
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
        return Arrays.asList(Direction.values()).stream().filter(d -> !board.getShape().isOutOfBorders(d.toPoint())).toArray(Direction[]::new);
    }
}
