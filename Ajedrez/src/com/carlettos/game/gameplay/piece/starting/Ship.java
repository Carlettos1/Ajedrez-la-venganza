package com.carlettos.game.gameplay.piece.starting;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.gameplay.ability.Abilities;
import com.carlettos.game.gameplay.pattern.Pattern;
import com.carlettos.game.gameplay.pattern.Patterns;
import com.carlettos.game.gameplay.pattern.action.IMove;
import com.carlettos.game.gameplay.pattern.action.ITake;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.gameplay.piece.type.IPieceType;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.Color;

/**
 *
 * @author Carlettos
 */
public class Ship extends Piece implements IMove, ITake {
    public Ship(Color color) {
        super("ship", Abilities.SHIP_ABILITY, color, IPieceType.STRUCTURE);
    }

    @Override
    public Pattern getMovePattern(AbstractBoard board, Point start) {
        return Patterns.MAGICIAN_MOVE_PATTERN;
    }

    @Override
    public Pattern getTakePattern(AbstractBoard board, Point start) {
        return Patterns.KING_PATTERN;
    }
}
