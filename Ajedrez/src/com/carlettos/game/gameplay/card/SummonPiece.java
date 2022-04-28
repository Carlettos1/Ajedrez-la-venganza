package com.carlettos.game.gameplay.card;

import java.util.function.Function;

import com.carlettos.game.board.Board;
import com.carlettos.game.board.clock.event.Event;
import com.carlettos.game.board.clock.event.EventInfo;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.gameplay.player.Player;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.ActionResult;
import com.carlettos.game.util.enums.Color;

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
