package com.carlettos.game.gameplay.ability.starting;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.board.clock.Time;
import com.carlettos.game.gameplay.ability.AbilityNoInfo;
import com.carlettos.game.gameplay.pattern.Pattern;
import com.carlettos.game.gameplay.pattern.Patterns;
import com.carlettos.game.gameplay.piece.demonic.Portal;
import com.carlettos.game.util.Point;

public class AbilityWarlock extends AbilityNoInfo {
    public static final Pattern PATTERN = Patterns.KING_PATTERN;

    public AbilityWarlock() {
        super("warlock", Time.lap(5), 3);
    }

    @Override
    public boolean reducedCanUse(AbstractBoard board, Point start) {
        return board.getAll(PATTERN, start).stream().anyMatch(e -> !e.hasPiece() && e.isMagic() && e.isBuildable());
    }

    @Override
    public void use(AbstractBoard board, Point start) {
        board.getAll(PATTERN, start).stream().filter(e -> !e.hasPiece() && e.isMagic() && e.isBuildable())
                .forEach(e -> e.setPieceIfEmpty(new Portal(board.getPiece(start).getColor())));
        this.commonUse(board, start);
    }
}
