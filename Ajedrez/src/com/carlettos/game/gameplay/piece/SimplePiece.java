package com.carlettos.game.gameplay.piece;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.gameplay.ability.Ability;
import com.carlettos.game.gameplay.pattern.Pattern;
import com.carlettos.game.gameplay.pattern.action.IMove;
import com.carlettos.game.gameplay.pattern.action.ITake;
import com.carlettos.game.gameplay.piece.type.IPieceType;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.Color;

/**
 *
 * @author Carlos
 */
public abstract class SimplePiece extends Piece implements ITake, IMove {
    protected final Pattern pattern;

    protected SimplePiece(String key, Ability<?> ability, Color color, Pattern pattern, IPieceType... types) {
        super(key, ability, color, types);
        this.pattern = pattern;
    }

    @Override
    public Pattern getMovePattern(AbstractBoard board, Point start) {
        return this.pattern;
    }

    @Override
    public Pattern getTakePattern(AbstractBoard board, Point start) {
        return this.pattern;
    }
}
