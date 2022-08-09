package com.carlettos.game.gameplay.ability.demonic;

import com.carlettos.game.board.AbstractSquareBoard;
import com.carlettos.game.gameplay.ability.AbilityNoInfo;
import com.carlettos.game.gameplay.pattern.Pattern;
import com.carlettos.game.gameplay.pattern.Patterns;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.util.Point;

public class WitchAbility extends AbilityNoInfo {
    public static final Pattern PATTERN = Patterns.KING_PATTERN;

    public WitchAbility() {
        super("witch", 8, 2);
    }

    @Override
    public boolean canUse(AbstractSquareBoard board, Piece piece, Point start) {
        return this.commonCanUse(board, piece);
    }

    @Override
    public void use(AbstractSquareBoard board, Piece piece, Point start) {
        board.getMatchingEscaques(PATTERN, start).forEach(e -> board.killPiece(e.getPos()));
        this.commonUse(board, piece);
    }
}
