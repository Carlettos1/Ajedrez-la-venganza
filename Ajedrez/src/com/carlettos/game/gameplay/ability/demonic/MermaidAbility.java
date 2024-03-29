package com.carlettos.game.gameplay.ability.demonic;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.board.clock.Time;
import com.carlettos.game.gameplay.ability.NoInfoAbility;
import com.carlettos.game.gameplay.pattern.Pattern;
import com.carlettos.game.gameplay.pattern.Patterns;
import com.carlettos.game.gameplay.piece.demonic.Portal;
import com.carlettos.game.util.Point;

public class MermaidAbility extends NoInfoAbility {
    public static final Pattern PATTERN = Patterns.KING_PATTERN;

    public MermaidAbility() {
        super("mermaid", Time.lap(5), 2);
    }

    @Override
    public boolean reducedCanUse(AbstractBoard board, Point start) {
        return board.getAll(PATTERN, start).stream().anyMatch(e -> e.getPiece() instanceof Portal);
    }

    @Override
    public void use(AbstractBoard board, Point start) {
        board.getAll(PATTERN, start).stream().filter(e -> e.getPiece() instanceof Portal)
                .forEach(e -> e.getPiece().removeCD(Time.lap(2)));
        this.commonUse(board, start);
    }
}
