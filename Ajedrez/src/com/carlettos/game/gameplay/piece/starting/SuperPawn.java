package com.carlettos.game.gameplay.piece.starting;

import com.carlettos.game.gameplay.ability.Abilities;
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
        super(() -> color, () -> color, "super_pawn", "sp", Abilities.ABILITY_SUPER_PAWN, color);
    }
}
