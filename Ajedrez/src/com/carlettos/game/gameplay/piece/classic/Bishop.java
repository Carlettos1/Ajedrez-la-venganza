package com.carlettos.game.gameplay.piece.classic;

import com.carlettos.game.gameplay.ability.Abilities;
import com.carlettos.game.gameplay.pattern.Patterns;
import com.carlettos.game.gameplay.pattern.classic.PatternBishop;
import com.carlettos.game.gameplay.piece.SimplePiece;
import com.carlettos.game.gameplay.piece.type.IPieceType;
import com.carlettos.game.util.enums.Color;

/**
 *
 * @author Carlos
 */
public class Bishop extends SimplePiece<PatternBishop> {
    public Bishop(Color color) {
        super("bishop", Abilities.BISHOP_ABILITY, color, Patterns.BISHOP_PATTERN, IPieceType.BIOLOGIC,
                IPieceType.TRANSPORTABLE);
    }
}
