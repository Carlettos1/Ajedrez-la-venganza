package com.carlettos.game.gameplay.piece.starting;

import com.carlettos.game.gameplay.ability.Abilities;
import com.carlettos.game.gameplay.pattern.Patterns;
import com.carlettos.game.gameplay.pattern.classic.PatternPawnMove;
import com.carlettos.game.gameplay.pattern.classic.PatternPawnTake;
import com.carlettos.game.gameplay.piece.AbstractPawn;
import com.carlettos.game.util.enums.Color;

/**
 *
 * @author Carlettos
 */
public class ShieldBearer extends AbstractPawn<PatternPawnMove, PatternPawnTake> {
    public ShieldBearer(Color color) {
        super(Patterns.PAWN_MOVE_PATTERN, Patterns.PAWN_TAKE_PATTERN, "shield_bearer", Abilities.ABILITY_SHIELD_BEARER, color);
    }
}
