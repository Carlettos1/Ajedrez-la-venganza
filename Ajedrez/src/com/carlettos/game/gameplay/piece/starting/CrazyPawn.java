package com.carlettos.game.gameplay.piece.starting;

import com.carlettos.game.gameplay.ability.Abilities;
import com.carlettos.game.gameplay.pattern.starting.PatternCrazyPawn;
import com.carlettos.game.gameplay.piece.SimplePiece;
import com.carlettos.game.util.enums.Color;
import com.carlettos.game.util.enums.PieceType;

/**
 *
 * @author Carlettos
 */
public class CrazyPawn extends SimplePiece<PatternCrazyPawn> {
    public CrazyPawn(Color color) {
        super("crazy_pawn", Abilities.ABILITY_CRAZY_PAWN, color, PatternCrazyPawn.STANDARD_PATTERN, PieceType.BIOLOGIC,
                PieceType.TRANSPORTABLE);
    }
}
