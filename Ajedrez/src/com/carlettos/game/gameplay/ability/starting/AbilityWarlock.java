package com.carlettos.game.gameplay.ability.starting;

import com.carlettos.game.board.AbstractSquareBoard;
import com.carlettos.game.gameplay.ability.AbilityNoInfo;
import com.carlettos.game.gameplay.pattern.Pattern;
import com.carlettos.game.gameplay.pattern.Patterns;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.gameplay.piece.demonic.Portal;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.ActionResult;

public class AbilityWarlock extends AbilityNoInfo {
    public static final Pattern PATTERN = Patterns.KING_PATTERN;

    public AbilityWarlock() {
        super("warlock", 10, 3);
    }

    @Override
    public ActionResult canUse(AbstractSquareBoard board, Piece piece, Point start) {
        return ActionResult.fromBoolean(board.getMatchingEscaques(PATTERN, start).stream().anyMatch(e -> !e.hasPiece() && e.isMagic() && e.isBuildable()));
    }

    @Override
    public void use(AbstractSquareBoard board, Piece piece, Point start) {
        board.getMatchingEscaques(PATTERN, start).stream().forEach(e -> e.setPieceIfEmpty(new Portal(piece.getColor())));
    }
}
