package com.carlettos.game.gameplay.piece.classic;

import com.carlettos.game.gameplay.ability.Abilities;
import com.carlettos.game.gameplay.pattern.classic.PatternPawnMove;
import com.carlettos.game.gameplay.pattern.classic.PatternPawnTake;
import com.carlettos.game.gameplay.piece.AbstractPawn;
import com.carlettos.game.util.enums.Color;

/**
 *
 * @author Carlos
 */
public class Pawn extends AbstractPawn<PatternPawnMove, PatternPawnTake> {
    public Pawn(Color color) {
        super(()->color, ()->color, "pawn", "p", Abilities.ABILITY_PAWN, color);
    }
}
