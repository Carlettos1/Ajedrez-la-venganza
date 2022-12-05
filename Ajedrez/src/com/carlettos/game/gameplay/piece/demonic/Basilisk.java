package com.carlettos.game.gameplay.piece.demonic;

import com.carlettos.game.gameplay.ability.Abilities;
import com.carlettos.game.gameplay.pattern.Patterns;
import com.carlettos.game.gameplay.piece.SimplePiece;
import com.carlettos.game.gameplay.piece.type.IPieceType;
import com.carlettos.game.util.enums.Color;

public class Basilisk extends SimplePiece {

    public Basilisk(Color color) {
        super("basilisk", Abilities.BASILISK_ABILITY, color, Patterns.BISHOP_PATTERN, IPieceType.DEMONIC,
                IPieceType.IMMUNE);
    }
}
