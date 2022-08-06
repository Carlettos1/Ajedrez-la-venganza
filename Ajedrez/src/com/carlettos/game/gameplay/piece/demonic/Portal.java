package com.carlettos.game.gameplay.piece.demonic;

import com.carlettos.game.board.AbstractSquareBoard;
import com.carlettos.game.gameplay.ability.Abilities;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.gameplay.piece.type.IPieceType;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.Action;
import com.carlettos.game.util.enums.Color;

/**
 * TODO: que se utilize
 *
 * @author Carlettos
 */
public class Portal extends Piece {
    public Portal(Color color) {
        super("portal", Abilities.NO_ABILITY, color, IPieceType.STRUCTURE);
    }

    @Override
    public boolean can(Action action, AbstractSquareBoard board, Point start, Info info) {
        return false;
    }
}
