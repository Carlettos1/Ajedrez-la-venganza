package com.carlettos.game.gameplay.card.utility;

import com.carlettos.game.board.Board;
import com.carlettos.game.board.clock.event.Event;
import com.carlettos.game.board.clock.event.EventInfo;
import com.carlettos.game.gameplay.card.Card;
import com.carlettos.game.gameplay.player.Player;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.ActionResult;

/**
 *
 * @author Carlettos
 */
public class AddMovement extends Card {

    public AddMovement() {
        super("Add movement", "Adds 1 movement per turn to this player at the"
                + " end of the turn", 1);
    }

    @Override
    public ActionResult canUse(Point point, Board board, Player caster) {
        return this.commonCanUse(point, board, caster);
    }

    @Override
    public void use(Point point, Board board, Player caster) {
        board.getClock().addEvent(Event.create(EventInfo.of(board, manaCost, name), () -> {
            caster.changeMovements(1);
        }));
        this.commonUse(point, board, caster);
    }
}
