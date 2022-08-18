package com.carlettos.game.gameplay.piece.starting;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.gameplay.ability.Abilities;
import com.carlettos.game.gameplay.pattern.Pattern;
import com.carlettos.game.gameplay.pattern.Patterns;
import com.carlettos.game.gameplay.pattern.action.IMove;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.gameplay.piece.type.IPieceType;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.Color;

/**
 *
 * @author Carlettos
 */
public class Warlock extends Piece implements IMove {
    public Warlock(Color color) {
        super("warlock", Abilities.WARLOCK_ABILITY, color, IPieceType.TRANSPORTABLE, IPieceType.DEMONIC,
                IPieceType.IMMUNE);
    }

    @Override
    public Pattern getMovePattern(AbstractBoard board, Point start) {
        return Patterns.MAGICIAN_MOVE_PATTERN;
    }
}
