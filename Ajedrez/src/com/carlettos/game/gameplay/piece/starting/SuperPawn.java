package com.carlettos.game.gameplay.piece.starting;

import com.carlettos.game.gameplay.ability.Abilities;
import com.carlettos.game.gameplay.pattern.Patterns;
import com.carlettos.game.gameplay.pattern.starting.PatternSuperPawnMove;
import com.carlettos.game.gameplay.pattern.starting.PatternSuperPawnTake;
import com.carlettos.game.gameplay.piece.AbstractPawn;
import com.carlettos.game.util.enums.Color;

/**
 *
 * @author Carlettos
 */
public class SuperPawn extends AbstractPawn<PatternSuperPawnMove, PatternSuperPawnTake> {
    public SuperPawn(Color color) {
        super(Patterns.SUPER_PAWN_MOVE_PATTERN, Patterns.SUPER_PAWN_TAKE_PATTERN, "super_pawn",
                Abilities.SUPER_PAWN_ABILITY, color);
    }
}
