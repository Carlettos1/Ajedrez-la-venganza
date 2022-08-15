package com.carlettos.game.gameplay.piece.starting;

import com.carlettos.game.board.AbstractBoard;
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
public class Wall extends Piece {
    public Wall(Color color) {
        super("wall", Abilities.NO_ABILITY, color, IPieceType.STRUCTURE, IPieceType.IMPENETRABLE);
    }

    @Override
    public boolean can(Action action, AbstractBoard board, Point start, Info info) {
        return false;
    }
}
