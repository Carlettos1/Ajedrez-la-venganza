package com.carlettos.game.gameplay.piece.starting;

import com.carlettos.game.gameplay.ability.Abilities;
import com.carlettos.game.gameplay.pattern.starting.CrazyPawnPattern;
import com.carlettos.game.gameplay.piece.SimplePiece;
import com.carlettos.game.gameplay.piece.type.IPieceType;
import com.carlettos.game.util.enums.Color;

/**
 *
 * @author Carlettos
 */
public class CrazyPawn extends SimplePiece {
    public CrazyPawn(Color color) {
        super("crazy_pawn", Abilities.CRAZY_PAWN_ABILITY, color, CrazyPawnPattern.STANDARD_PATTERN, IPieceType.BIOLOGIC,
                IPieceType.TRANSPORTABLE);
    }
}
