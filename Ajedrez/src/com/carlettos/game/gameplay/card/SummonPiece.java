package com.carlettos.game.gameplay.card;

import java.util.function.Function;

import com.carlettos.game.board.SquareBoard;
import com.carlettos.game.board.clock.event.Event;
import com.carlettos.game.board.clock.event.EventInfo;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.gameplay.player.Player;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.Color;

/**
 *
 * @author Carlettos
 */
public class SummonPiece<P extends Piece> extends Card {
    protected final Function<Color, P> builder;

    public SummonPiece(String key, int manaCost, Function<Color, P> builder) {
        super("summon_" + key, manaCost);
        this.builder = builder;
    }

    @Override
    public boolean canUse(Point point, SquareBoard board, Player caster) {
        if (board.get(point).hasPiece()) {
            return false;
            // TODO: use summoneable range method of board.
        }
        return this.commonCanUse(point, board, caster);
    }

    @Override
    public void use(Point point, SquareBoard board, Player caster) {
        board.getClock().addEvent(Event.create(EventInfo.of(board, 1, this.data.getName(), point),
                () -> board.set(point, builder.apply(caster.getColor()))));
        this.commonUse(point, board, caster);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
