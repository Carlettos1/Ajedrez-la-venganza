package com.carlettos.game.gameplay.ability.starting;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.board.clock.Time;
import com.carlettos.game.gameplay.ability.AbilityNoInfo;
import com.carlettos.game.gameplay.card.onBoard.CardsOnBoard;
import com.carlettos.game.gameplay.effect.FireEffect;
import com.carlettos.game.gameplay.effect.IceEffect;
import com.carlettos.game.gameplay.pattern.Pattern;
import com.carlettos.game.gameplay.pattern.Patterns;
import com.carlettos.game.util.Point;

public class AbilityMagician extends AbilityNoInfo {
    public static final Pattern ACTION_PATTERN = Patterns.ARCHER_MOVE_PATTERN;

    public AbilityMagician() {
        super("magician", Time.lap(6), 2);
    }

    @Override
    public boolean reducedCanUse(AbstractBoard board, Point start) {
        return board.getClock().boardContainsAny(CardsOnBoard.ICE, CardsOnBoard.FIRE);
    }

    @Override
    public void use(AbstractBoard board, Point start) {
        var hasIce = board.getClock().boardContains(CardsOnBoard.ICE);
        var hasFire = board.getClock().boardContains(CardsOnBoard.FIRE);
        var pieces = board.getAll(ACTION_PATTERN, start);
        pieces.removeIf(e -> !e.hasPiece());
        pieces.removeIf(e -> e.getPieceColor() == board.get(start).getPieceColor());

        if (hasIce) {
            pieces.forEach(e -> e.getPiece().getEffectManager().addEffect(new IceEffect(5)));
        }
        if (hasFire) {
            pieces.forEach(e -> e.getPiece().getEffectManager().addEffect(new FireEffect(11)));
        }

        this.commonUse(board, start);
    }
}
