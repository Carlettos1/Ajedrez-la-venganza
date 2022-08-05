package com.carlettos.game.gameplay.piece;

import com.carlettos.game.board.AbstractSquareBoard;
import com.carlettos.game.gameplay.ability.Abilities;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.Action;
import com.carlettos.game.util.enums.Color;

/**
 *
 * @author Carlos
 */
public class Empty extends Piece {
    public Empty() {
        super("empty", Abilities.NO_ABILITY, Color.GRAY);
    }

    @Override
    public boolean can(Action action, AbstractSquareBoard board, Point start, Info info) {
        return false;
    }
}
