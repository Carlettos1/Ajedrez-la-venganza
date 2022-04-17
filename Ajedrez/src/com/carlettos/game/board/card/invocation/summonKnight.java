package com.carlettos.game.board.card.invocation;

import com.carlettos.game.board.manager.clock.event.Event;
import com.carlettos.game.core.Tuple;
import com.carlettos.game.board.card.Card;
import com.carlettos.game.board.player.Player;
import com.carlettos.game.board.manager.clock.Clock;
import com.carlettos.game.board.manager.Board;
import com.carlettos.game.board.manager.clock.event.EventInfo;
import com.carlettos.game.board.piece.classic.Knight;
import com.carlettos.game.core.ActionResult;
import com.carlettos.game.core.Point;

/**
 * It represents a card that summons a knight.
 * 
 * @author Carlos
 */
public class SummonKnight extends Card {

    public SummonKnight() {
        super("Invocar Caballo", "Invoca un caballo en la ubicación elegida al final del turno.", 2);
    }

    @Override
    public ActionResult canUse(Point point, Board board, Player caster) {
        if(caster.getMana() < this.getCost() || board.getEscaque(point).hasPiece()){
            return ActionResult.FAIL;
        }
        return ActionResult.PASS;
    }

    @Override
    public void use(Point point, Board board, Player caster) {
        board.getClock().addEvent(Event.create(EventInfo.of(board, 1, this.name, point), () -> {
            board.getEscaque(point).setPiece(new Knight(caster.getColor()));
        }));
        caster.getHand().quitarCarta(this);
        caster.cambiarMana(-getCost());
        board.movement();
    }
}
