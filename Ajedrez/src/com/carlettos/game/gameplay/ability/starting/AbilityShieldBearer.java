package com.carlettos.game.gameplay.ability.starting;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.board.clock.Time;
import com.carlettos.game.gameplay.ability.AbilityNoInfo;
import com.carlettos.game.gameplay.pattern.Pattern;
import com.carlettos.game.gameplay.pattern.Patterns;
import com.carlettos.game.gameplay.piece.type.IPieceType;
import com.carlettos.game.util.Point;

public class AbilityShieldBearer extends AbilityNoInfo {
    public static final Pattern PATTERN = Patterns.KING_PATTERN;

    public AbilityShieldBearer() {
        super("shield_bearer", Time.lap(15), 0);
    }

    @Override
    public boolean reducedCanUse(AbstractBoard board, Point start) {
        return board.getAll(PATTERN, start).stream()
                .anyMatch(e -> e.hasPiece() && e.getPieceColor().equals(board.getPiece(start).getColor()));
    }

    @Override
    public void use(AbstractBoard board, Point start) {
        board.getAll(PATTERN, start).stream().filter(e -> e.isControlledBy(board.getPiece(start).getColor()))
                .forEach(e -> e.getPiece().getTypeManager().addType(IPieceType.IMPENETRABLE));
        this.commonUse(board, start);
    }
}
