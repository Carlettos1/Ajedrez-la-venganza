package com.carlettos.game.gameplay.ability.starting;

import com.carlettos.game.board.AbstractSquareBoard;
import com.carlettos.game.gameplay.ability.AbilityNoInfo;
import com.carlettos.game.gameplay.pattern.Pattern;
import com.carlettos.game.gameplay.pattern.Patterns;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.gameplay.piece.type.IPieceType;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.ActionResult;

public class AbilityShieldBearer extends AbilityNoInfo {
    public static final Pattern PATTERN = Patterns.KING_PATTERN;
    public AbilityShieldBearer() {
        super("shield_bearer", 30, 0);
    }

    @Override
    public ActionResult canUse(AbstractSquareBoard board, Piece piece, Point start) {
        return ActionResult.fromBoolean(this.commonCanUse(board, piece) && board.getMatchingEscaques(PATTERN, start).stream().anyMatch(e -> e.hasPiece() && e.getPieceColor().equals(piece.getColor())));
    }

    @Override
    public void use(AbstractSquareBoard board, Piece piece, Point start) {
        board.getMatchingEscaques(PATTERN, start).stream().filter(e -> e.isControlledBy(piece.getColor())).forEach(e -> e.getPiece().getTypeManager().addType(IPieceType.IMPENETRABLE));
    }
}
