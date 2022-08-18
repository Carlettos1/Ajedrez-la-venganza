package com.carlettos.game.gameplay.piece.starting;

import com.carlettos.game.gameplay.ability.Abilities;
import com.carlettos.game.gameplay.pattern.Patterns;
import com.carlettos.game.gameplay.piece.SimplePiece;
import com.carlettos.game.gameplay.piece.type.IPieceType;
import com.carlettos.game.util.enums.Color;

/**
 *
 * @author Carlettos
 */
public class Paladin extends SimplePiece {
    public Paladin(Color color) {
        super("paladin", Abilities.PALADIN_ABILITY, color, Patterns.QUEEN_PATTERN, IPieceType.HEROIC,
                IPieceType.IMMUNE);
    }
}
