package com.carlettos.game.util.helper;

import com.carlettos.game.board.IClockUse;
import com.carlettos.game.board.clock.AbstractClock;
import com.carlettos.game.gameplay.card.Card;

public class CardHelper {
    private CardHelper() {}

    public static final boolean boardHasCard(IClockUse board, Card card) {
        return clockHasCard(board.getClock(), card);
    }

    public static final boolean clockHasCard(AbstractClock clock, Card card) {
        return clock.containsCardOnBoard(card);
    }

    public static final boolean boardHasCards(IClockUse board, Card... cards) {
        return clockHasCards(board.getClock(), cards);
    }

    public static final boolean clockHasCards(AbstractClock clock, Card... cards) {
        for (Card card : cards) {
            if (!clockHasCard(clock, card))
                return false;
        }
        return true;
    }
}
