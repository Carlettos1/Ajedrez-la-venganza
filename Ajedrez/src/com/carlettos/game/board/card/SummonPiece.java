package com.carlettos.game.board.card;

import com.carlettos.game.board.manager.Board;
import com.carlettos.game.board.manager.clock.event.Event;
import com.carlettos.game.board.manager.clock.event.EventInfo;
import com.carlettos.game.board.piece.Piece;
import com.carlettos.game.board.player.Player;
import com.carlettos.game.board.property.Color;
import com.carlettos.game.core.ActionResult;
import com.carlettos.game.core.Point;
import java.util.function.Function;

/**
 *
 * @author Carlettos
 */
public class SummonPiece<P extends Piece> extends Card {
    protected final Function<Color, P> builder;

    public SummonPiece(String name, String description, int manaCost, Function<Color, P> builder) {
        super(name, description, manaCost);
        this.builder = builder;
    }

    @Override
    public ActionResult canUse(Point point, Board board, Player caster) {
        if(board.getEscaque(point).hasPiece()){
            return ActionResult.FAIL; //todo: use summoneable range method from board.
        }
        return this.commonCanUse(point, board, caster);
    }

    @Override
    public void use(Point point, Board board, Player caster) {
        board.getClock().addEvent(Event.create(EventInfo.of(board, 1, this.name, point), () -> {
            board.getEscaque(point).setPiece(builder.apply(caster.getColor()));
        }));
        this.commonUse(point, board, caster);
    }
}
