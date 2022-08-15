package com.carlettos.game.gameplay.ability.starting;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.gameplay.ability.AbilityNoInfo;
import com.carlettos.game.gameplay.pattern.Pattern;
import com.carlettos.game.gameplay.pattern.Patterns;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.gameplay.piece.type.IPieceType;
import com.carlettos.game.util.Point;

public class AbilityShieldBearer extends AbilityNoInfo {
    public static final Pattern PATTERN = Patterns.KING_PATTERN;

    public AbilityShieldBearer() {
        super("shield_bearer", 30, 0);
    }

    @Override
    public boolean canUse(AbstractBoard board, Piece piece, Point start) {
        return (this.commonCanUse(board, piece) && board.getAll(PATTERN, start).stream()
                .anyMatch(e -> e.hasPiece() && e.getPieceColor().equals(piece.getColor())));
    }

    @Override
    public void use(AbstractBoard board, Piece piece, Point start) {
        board.getAll(PATTERN, start).stream().filter(e -> e.isControlledBy(piece.getColor()))
                .forEach(e -> e.getPiece().getTypeManager().addType(IPieceType.IMPENETRABLE));
    }
}
