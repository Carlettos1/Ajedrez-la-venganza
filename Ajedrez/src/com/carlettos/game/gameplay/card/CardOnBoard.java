package com.carlettos.game.gameplay.card;

import com.carlettos.game.board.SquareBoard;
import com.carlettos.game.gameplay.player.Player;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.ActionResult;

public abstract class CardOnBoard extends Card {

    protected CardOnBoard(String key, int manaCost) {
        super(key, manaCost);
    }

    @Override
    public ActionResult canUse(Point point, SquareBoard board, Player caster) {
        return commonCanUse(point, board, caster);
    }

    @Override
    public void use(Point point, SquareBoard board, Player caster) {
        board.getClock().addCardToBoard(caster, this);
        commonUse(point, board, caster);
    }
}
