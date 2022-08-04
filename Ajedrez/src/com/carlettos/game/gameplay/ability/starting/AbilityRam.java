package com.carlettos.game.gameplay.ability.starting;

import com.carlettos.game.board.AbstractSquareBoard;
import com.carlettos.game.gameplay.ability.Ability;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.ActionResult;
import com.carlettos.game.util.enums.Direction;

public class AbilityRam extends Ability {
    public AbilityRam() {
        super("ram", 4, 0);
    }

    @Override
    public ActionResult canUse(AbstractSquareBoard board, Piece piece, Point start, Info info) {
        return ActionResult.fromBoolean(this.commonCanUse(board, piece) && info.isType(Direction.class));
    }

    @Override
    public void use(AbstractSquareBoard board, Piece piece, Point start, Info info) {
        var dir = (Direction) info.getValue();
        int charge = 0;
        var isCharged = false;

        var to = start.add(dir.toPoint());
        while (!board.shape.isOutOfBorders(to) && !isCharged) {
            isCharged = board.getEscaque(to).hasPiece();
            to = to.add(dir.toPoint()); // next point
            charge++;
        }
        // TODO: check if it kill some piece

        to = to.add(dir.toPoint().scale(-1)); // it's actually marking the next point, now it doesn't
        board.removePieceNoDeath(start);
        if (isCharged) {
            var distance = charge / 5 + 1;
            var maxTo = to.add(dir.toPoint().scale(distance));
            while (board.shape.isOutOfBorders(maxTo)) {
                maxTo = maxTo.add(dir.toPoint().scale(-1)); // adjusted to not go OofB
            }
            while (!to.equals(maxTo)) { // removing from to to maxTo, without maxTo
                board.removePieceNoDeath(to);
                to = to.add(dir.toPoint());
            }
        }
        board.setPiece(to, piece);
        this.commonUse(board, piece);
    }

    @Override
    public Direction[] getValues(AbstractSquareBoard board, Point start) {
        return Direction.values();
    }
}
