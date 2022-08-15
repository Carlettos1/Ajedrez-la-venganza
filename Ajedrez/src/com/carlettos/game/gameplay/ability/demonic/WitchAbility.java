package com.carlettos.game.gameplay.ability.demonic;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.gameplay.ability.AbilityNoInfo;
import com.carlettos.game.gameplay.pattern.Pattern;
import com.carlettos.game.gameplay.pattern.Patterns;
import com.carlettos.game.util.Point;

public class WitchAbility extends AbilityNoInfo {
    public static final Pattern PATTERN = Patterns.KING_PATTERN;

    public WitchAbility() {
        super("witch", 8, 2);
    }

    @Override
    public boolean reducedCanUse(AbstractBoard board, Point start) {
        return true;
    }

    @Override
    public void use(AbstractBoard board, Point start) {
        board.getAll(PATTERN, start).forEach(e -> board.remove(e, true));
        this.commonUse(board, start);
    }
}
