package com.carlettos.game.gameplay.ability.demonic;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.gameplay.ability.AbilityNoInfo;
import com.carlettos.game.gameplay.pattern.Pattern;
import com.carlettos.game.gameplay.pattern.Patterns;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.gameplay.piece.demonic.Portal;
import com.carlettos.game.util.Point;

public class MermaidAbility extends AbilityNoInfo {
    public static final Pattern PATTERN = Patterns.KING_PATTERN;

    public MermaidAbility() {
        super("mermaid", 5, 2);
    }

    @Override
    public boolean canUse(AbstractBoard board, Piece piece, Point start) {
        return commonCanUse(board, piece)
                && board.getAll(PATTERN, start).stream().anyMatch(e -> e.getPiece() instanceof Portal);
    }

    @Override
    public void use(AbstractBoard board, Piece piece, Point start) {
        board.getAll(PATTERN, start).stream().filter(e -> e.getPiece() instanceof Portal)
                .forEach(e -> e.getPiece().changeCD(-2));
        this.commonUse(board, piece);
    }
}
