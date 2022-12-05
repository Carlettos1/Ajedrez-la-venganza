package com.carlettos.game.gameplay.piece.demonic;

import com.carlettos.game.gameplay.ability.Abilities;
import com.carlettos.game.gameplay.pattern.Patterns;
import com.carlettos.game.gameplay.piece.SimplePiece;
import com.carlettos.game.gameplay.piece.type.IPieceType;
import com.carlettos.game.util.enums.Color;

public class Oni extends SimplePiece {

    public Oni(Color color) {
        super("oni", Abilities.ONI_ABILITY, color, Patterns.ONI_PATTERN, IPieceType.DEMONIC, IPieceType.BIOLOGIC,
                IPieceType.TRANSPORTABLE);
    }
}
