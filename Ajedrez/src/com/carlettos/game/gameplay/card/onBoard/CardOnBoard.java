package com.carlettos.game.gameplay.card.onBoard;

import com.carlettos.game.board.SquareBoard;
import com.carlettos.game.gameplay.card.Card;
import com.carlettos.game.gameplay.player.Player;
import com.carlettos.game.util.Point;

public abstract class CardOnBoard extends Card {

    protected CardOnBoard(String key, int manaCost) {
        super(key, manaCost);
    }

    @Override
    public boolean canUse(Point point, SquareBoard board, Player caster) {
        return commonCanUse(point, board, caster);
    }

    @Override
    public void use(Point point, SquareBoard board, Player caster) {
        board.getClock().addCardToBoard(caster, this);
        commonUse(point, board, caster);
    }
}
